package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Order;
import lk.lnas.backend.domain.Transaction;
import lk.lnas.backend.model.OrderDTO;
import lk.lnas.backend.repos.OrderRepository;
import lk.lnas.backend.repos.TransactionRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;

    public OrderService(final OrderRepository orderRepository,
            final TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map((order) -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setType(order.getType());
        orderDTO.setSubtotal(order.getSubtotal());
        orderDTO.setItemDiscount(order.getItemDiscount());
        orderDTO.setTax(order.getTax());
        orderDTO.setShipping(order.getShipping());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setTransacationID(order.getTransacationID() == null ? null : order.getTransacationID().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setStatus(orderDTO.getStatus());
        order.setType(orderDTO.getType());
        order.setSubtotal(orderDTO.getSubtotal());
        order.setItemDiscount(orderDTO.getItemDiscount());
        order.setTax(orderDTO.getTax());
        order.setShipping(orderDTO.getShipping());
        order.setTotal(orderDTO.getTotal());
        final Transaction transacationID = orderDTO.getTransacationID() == null ? null : transactionRepository.findById(orderDTO.getTransacationID())
                .orElseThrow(() -> new NotFoundException("transacationID not found"));
        order.setTransacationID(transacationID);
        return order;
    }

}
