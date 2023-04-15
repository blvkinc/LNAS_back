package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Order;
import lk.lnas.backend.domain.OrderItem;
import lk.lnas.backend.model.OrderItemDTO;
import lk.lnas.backend.repos.OrderItemRepository;
import lk.lnas.backend.repos.OrderRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    public OrderItemService(final OrderItemRepository orderItemRepository,
            final OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderItemDTO> findAll() {
        final List<OrderItem> orderItems = orderItemRepository.findAll(Sort.by("id"));
        return orderItems.stream()
                .map((orderItem) -> mapToDTO(orderItem, new OrderItemDTO()))
                .toList();
    }

    public OrderItemDTO get(final Long id) {
        return orderItemRepository.findById(id)
                .map(orderItem -> mapToDTO(orderItem, new OrderItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderItemDTO orderItemDTO) {
        final OrderItem orderItem = new OrderItem();
        mapToEntity(orderItemDTO, orderItem);
        return orderItemRepository.save(orderItem).getId();
    }

    public void update(final Long id, final OrderItemDTO orderItemDTO) {
        final OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderItemDTO, orderItem);
        orderItemRepository.save(orderItem);
    }

    public void delete(final Long id) {
        orderItemRepository.deleteById(id);
    }

    private OrderItemDTO mapToDTO(final OrderItem orderItem, final OrderItemDTO orderItemDTO) {
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setPrice(orderItem.getPrice());
        orderItemDTO.setDiscount(orderItem.getDiscount());
        orderItemDTO.setQty(orderItem.getQty());
        orderItemDTO.setDescription(orderItem.getDescription());
        orderItemDTO.setOrderID(orderItem.getOrderID() == null ? null : orderItem.getOrderID().getId());
        return orderItemDTO;
    }

    private OrderItem mapToEntity(final OrderItemDTO orderItemDTO, final OrderItem orderItem) {
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setDiscount(orderItemDTO.getDiscount());
        orderItem.setQty(orderItemDTO.getQty());
        orderItem.setDescription(orderItemDTO.getDescription());
        final Order orderID = orderItemDTO.getOrderID() == null ? null : orderRepository.findById(orderItemDTO.getOrderID())
                .orElseThrow(() -> new NotFoundException("orderID not found"));
        orderItem.setOrderID(orderID);
        return orderItem;
    }

}
