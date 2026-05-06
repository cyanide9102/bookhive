package com.cyanide9102.catalogservice.category;

import com.cyanide9102.catalogservice.common.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends EntityBase {

    @Column(nullable = false)
    private String name;
}
