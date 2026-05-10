package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.common.entity.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "books")
@Check(constraints = "stock_quantity >= 0 AND price > 0")
public class Book extends EntityBase {

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "stock_quantity", nullable = false)
    private Short stockQuantity = 0;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;
}
