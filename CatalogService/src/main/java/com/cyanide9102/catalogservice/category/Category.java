package com.cyanide9102.catalogservice.category;

import com.cyanide9102.common.context.UserContext;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

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
