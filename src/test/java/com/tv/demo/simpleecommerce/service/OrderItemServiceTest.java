package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.OrderItemDto;
import com.tv.demo.simpleecommerce.dto.OrderItemUpdateDto;
import com.tv.demo.simpleecommerce.exception.ProductAlreadyExistsException;
import com.tv.demo.simpleecommerce.exception.RelationshipEntityNotFoundException;
import com.tv.demo.simpleecommerce.model.Order;
import com.tv.demo.simpleecommerce.model.OrderItem;
import com.tv.demo.simpleecommerce.model.OrderStatus;
import com.tv.demo.simpleecommerce.model.Product;
import com.tv.demo.simpleecommerce.repository.OrderItemRepository;
import com.tv.demo.simpleecommerce.repository.OrderRepository;
import com.tv.demo.simpleecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class OrderItemServiceTest extends PostgresContainerTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    OrderItemService service;

    @AfterEach
    public void cleanup() {
        orderItemRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testListOrderItems() {
        var productId = addProduct().getId();
        var orderId = addOrder().getId();
        addOrderItem(orderId, productId);

        var result = service.listOrderItems();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testListOrderItems_whenTableIsEmpty() {
        var result = service.listOrderItems();
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testFindOrderItemById() {
        var productId = addProduct().getId();
        var orderId = addOrder().getId();
        var orderItem = addOrderItem(orderId, productId);

        var result = service.findOrderItemById(orderItem.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(orderItem.getId());
    }

    @Test
    public void testFindOrderItemById_whenNoOrderItemIsFound() {
        var id = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> service.findOrderItemById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testCreateOrderItem() {
        var product = addProduct();
        var order = addOrder();

        var dto = OrderItemDto.builder()
                .productId(product.getId())
                .orderId(order.getId())
                .productQuantity(5)
                .build();

        var result = service.createOrderItem(dto);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getProductId()).isEqualTo(dto.getProductId());
        Assertions.assertThat(result.getOrderId()).isEqualTo(dto.getOrderId());
        Assertions.assertThat(result.getProductQuantity()).isEqualTo(dto.getProductQuantity());
    }

    @Test
    public void testCreateOrderItem_whenProductDoesNotExist() {
        var order = addOrder();

        var dto = OrderItemDto.builder()
                .productId(UUID.randomUUID())
                .orderId(order.getId())
                .productQuantity(5)
                .build();

        Assertions.assertThatThrownBy(() -> service.createOrderItem(dto))
                .isInstanceOf(RelationshipEntityNotFoundException.class);
    }

    @Test
    public void testCreateOrderItem_whenOrderDoesNotExist() {
        var product = addProduct();

        var dto = OrderItemDto.builder()
                .productId(product.getId())
                .orderId(UUID.randomUUID())
                .productQuantity(5)
                .build();

        Assertions.assertThatThrownBy(() -> service.createOrderItem(dto))
                .isInstanceOf(RelationshipEntityNotFoundException.class);
    }

    @Test
    public void testCreateOrderItem_whenProductIsAlreadyPresentInOrder() {
        var product = addProduct();
        var order = addOrder();
        addOrderItem(order.getId(), product.getId());

        var dto = OrderItemDto.builder()
                .productId(product.getId())
                .orderId(order.getId())
                .productQuantity(5)
                .build();

        Assertions.assertThatThrownBy(() -> service.createOrderItem(dto))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }

    @Test
    public void testUpdateOrderItem() {
        var product = addProduct();
        var order = addOrder();

        var dto = OrderItemDto.builder()
                .productId(product.getId())
                .orderId(order.getId())
                .productQuantity(5)
                .build();

        var originalOrderItem = service.createOrderItem(dto);
        Assertions.assertThat(originalOrderItem).isNotNull();
        Assertions.assertThat(originalOrderItem.getId()).isNotNull();

        var id = originalOrderItem.getId();
        var updateDto = OrderItemUpdateDto.builder()
                .productQuantity(1)
                .build();

        var updatedOrderItem = service.updateOrderItem(id, updateDto);
        Assertions.assertThat(updatedOrderItem).isNotNull();
        Assertions.assertThat(updatedOrderItem.getId()).isEqualTo(originalOrderItem.getId());
        Assertions.assertThat(updatedOrderItem.getProductQuantity()).isEqualTo(updateDto.getProductQuantity());
        Assertions.assertThat(updatedOrderItem.getOrderId()).isEqualTo(originalOrderItem.getOrderId());
        Assertions.assertThat(updatedOrderItem.getProductId()).isEqualTo(originalOrderItem.getProductId());

    }

    @Test
    public void testUpdateOrderItem_whenOrderItemIsNotFound() {
        var id = UUID.randomUUID();
        var dto = OrderItemUpdateDto.builder()
                .productQuantity(1)
                .build();

        Assertions.assertThatThrownBy(() -> service.updateOrderItem(id, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testDeleteOrderItem() {
        var productId = addProduct().getId();
        var orderId = addOrder().getId();
        var orderItem = addOrderItem(orderId, productId);

        service.deleteOrderItem(orderItem.getId());
        Assertions.assertThat(orderItemRepository.findAll()).isEmpty();
    }

    @Test
    public void testDeleteOrderItem_whenOrderItemIsNotFound() {
        var id = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> service.deleteOrderItem(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private Order addOrder() {
        var order = new Order();
        order.setStatus(OrderStatus.IN_CREATION);
        return orderRepository.save(order);
    }

    private Product addProduct() {
        var product = new Product();
        product.setName("Product 1");
        product.setDescription("Description");
        product.setPrice(BigDecimal.TEN);
        return productRepository.save(product);
    }

    private OrderItem addOrderItem(UUID orderId, UUID productId) {
        var orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.findById(orderId).get());
        orderItem.setProduct(productRepository.findById(productId).get());
        orderItem.setProductQuantity(5);
        return orderItemRepository.save(orderItem);
    }
}
