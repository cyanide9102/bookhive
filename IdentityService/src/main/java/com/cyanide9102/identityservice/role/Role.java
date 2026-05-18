package com.cyanide9102.identityservice.role;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            id = TSID.Factory.getTsid().toString();
        }
    }
}
