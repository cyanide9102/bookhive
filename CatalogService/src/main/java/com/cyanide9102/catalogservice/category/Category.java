package com.cyanide9102.catalogservice.category;

import com.cyanide9102.common.context.UserContext;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(nullable = false)
    private String name;

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
