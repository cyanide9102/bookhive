package com.cyanide9102.orderservice.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByCreatedBy(String userId);

    Optional<Order> findByTrackingId(String trackingId);
}
