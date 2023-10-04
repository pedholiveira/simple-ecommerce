package com.tv.demo.simpleecommerce.service;

import com.tv.demo.simpleecommerce.dto.OrderDto;
import com.tv.demo.simpleecommerce.model.Order;
import com.tv.demo.simpleecommerce.model.OrderStatus;
import com.tv.demo.simpleecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class OrderServiceTest extends PostgresContainerTest {

    @Autowired
    OrderRepository repository;
    @Autowired
    OrderService service;

    @AfterEach
    public void cleanup() {
        repository.deleteAll();
    }

    @Test
    @Transactional
    public void testListOrders() {
        addOrder();
        var result = service.listOrders();
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void testListOrders_whenTableIsEmpty() {
        var result = service.listOrders();
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @Transactional
    public void testFindOrderById() {
        var order = addOrder();
        var result = service.findOrderById(order.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(order.getId());
    }

    @Test
    public void testFindOrderById_whenNoOrderIsFound() {
        var id = UUID.randomUUID();
        Assertions.assertThatThrownBy(() -> service.findOrderById(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testCreateOrder() {
        var result = service.createOrder();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isNotNull();
        Assertions.assertThat(result.getStatus()).isEqualTo(OrderStatus.IN_CREATION.name());
        Assertions.assertThat(result.getItems()).isEmpty();
        Assertions.assertThat(result.getTotalPrice()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testUpdateOrder() {
        var id = addOrder().getId();
        var dto = OrderDto.builder()
                .status(OrderStatus.WAITING_PAYMENT.name())
                .build();

        var result = service.updateOrder(id, dto);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(id);
        Assertions.assertThat(result.getStatus()).isEqualTo(dto.getStatus());
    }

    @Test
    public void testUpdateOrder_whenOrderIsNotFound() {
        var id = UUID.randomUUID();
        var dto = OrderDto.builder()
                .status(OrderStatus.WAITING_PAYMENT.name())
                .build();

        Assertions.assertThatThrownBy(() -> service.updateOrder(id, dto))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testDeleteOrder() {
        var order = addOrder();

        service.deleteOrder(order.getId());
        Assertions.assertThat(repository.findAll()).isEmpty();
    }

    @Test
    public void testDeleteOrder_whenOrderIsNotFound() {
        var id = UUID.randomUUID();

        Assertions.assertThatThrownBy(() -> service.deleteOrder(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private Order addOrder() {
        var order = new Order();
        order.setStatus(OrderStatus.IN_CREATION);
        return repository.save(order);
    }
}
