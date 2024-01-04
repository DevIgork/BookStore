package com.example.bookstore.repository;

import com.example.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT o FROM OrderItem o join fetch o.book join fetch o.order where o.order.id=:id")
    List<OrderItem> findAllByOrderId(Long id);

    @Query("SELECT o from OrderItem o join fetch o.book join fetch o.order WHERE o.id = :itemId AND  o.order.id = :orderid")
    Optional<OrderItem> findByIdAndOrderId(Long orderid, Long itemId);
}
