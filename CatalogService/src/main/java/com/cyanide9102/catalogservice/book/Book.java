package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.common.context.UserContext;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
@Check(constraints = "stock_quantity >= 0 AND price > 0")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();

        String userId = UserContext.getUserId();
        if (userId != null) {
            this.createdBy = userId;
            this.updatedBy = userId;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();

        String userId = UserContext.getUserId();
        if (userId != null) {
            this.updatedBy = userId;
        }
    }
}
