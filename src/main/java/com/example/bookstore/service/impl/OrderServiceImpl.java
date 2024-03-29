package com.example.bookstore.service.impl;

import static com.example.bookstore.model.Order.Status.PENDING;

import com.example.bookstore.dto.order.AddOrderRequestDto;
import com.example.bookstore.dto.order.OrderDto;
import com.example.bookstore.dto.order.UpdateOrderStatusDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.OrderService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderDto create(AddOrderRequestDto requestDto, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user cart by id"
                        + userId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by id" + userId));
        String shippingAddress = requestDto.getShippingAddress();
        Order order = intitializeOrder(user, shippingAddress);
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        fillOrderItems(cartItems, orderItems, orderItem);
        order.setOrderItem(orderItems);
        BigDecimal total = findTotal(orderItems);
        order.setTotal(total);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(shoppingCart.getCartItem());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAll(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orderMapper.toDtos(orders);
    }

    @Transactional
    @Override
    public OrderDto update(UpdateOrderStatusDto updateOrderStatusDto, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find order by id" + id));
        order.setStatus(updateOrderStatusDto.getStatus());
        return orderMapper.toDto(orderRepository.save(order));
    }

    private BigDecimal findTotal(Set<OrderItem> orderItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getPrice());
        }
        return total;
    }

    private void fillOrderItems(
            Set<CartItem> cartItems,
            Set<OrderItem> orderItems,
            OrderItem orderItem
    ) {
        for (CartItem cartItem : cartItems) {
            BigDecimal price = cartItem.getBook().getPrice();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setBook(cartItem.getBook());
            orderItem.setPrice(price);
            orderItems.add(orderItem);
        }
    }

    private Order intitializeOrder(User user, String shippingAddress) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setUser(user);
        order.setStatus(PENDING);
        order.setShippingAddress(shippingAddress);
        return order;
    }
}
