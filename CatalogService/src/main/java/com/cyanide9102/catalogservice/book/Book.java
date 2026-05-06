package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.catalogservice.common.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
@Check(constraints = "stock_quantity >= 0")
public class Book extends EntityBase {

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "stock_quantity", nullable = false)
    private Short stockQuantity = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;
}
