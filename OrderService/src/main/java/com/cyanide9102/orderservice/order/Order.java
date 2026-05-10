package com.cyanide9102.orderservice.order;

import com.cyanide9102.common.entity.EntityBase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "orders")
@Check(constraints = "quantity > 0")
public class Order extends EntityBase {

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "book_title", nullable = false)
    private String bookTitle;

    @Column(nullable = false)
    private Short quantity;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
}
