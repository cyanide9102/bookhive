package com.cyanide9102.identityservice.user;

import com.cyanide9102.identityservice.role.Role;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(length = 13, columnDefinition = "char(13)")
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", columnDefinition = "char(13)"), inverseJoinColumns = @JoinColumn(name = "role_id", columnDefinition = "char(13)"))
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    public void prePersist() {

        if (this.id == null) {
            id = TSID.Factory.getTsid().toString();
        }
    }
}
