package com.cyanide9102.catalogservice.book;

import com.cyanide9102.catalogservice.category.Category;
import com.cyanide9102.common.context.UserContext;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "stock_quantity", nullable = false, columnDefinition = "smallint default 0 check (stock_quantity >= 0)")
    private Short stockQuantity = 0;

    @Column(nullable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) check (price > 0)")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", columnDefinition = "char(13)")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    @Column(name = "created_by", length = 13, columnDefinition = "char(13)")
    private String createdBy;

    @Column(name = "updated_by", length = 13, columnDefinition = "char(13)")
    private String updatedBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            id = TSID.Factory.getTsid().toString();
        }

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
