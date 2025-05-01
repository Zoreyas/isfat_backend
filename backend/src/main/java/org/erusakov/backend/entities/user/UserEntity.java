package org.erusakov.backend.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.erusakov.backend.entities.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity extends BaseEntity<Long> {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "password", nullable = false)
    private String password;

    public String getFullName() {
        return name + (surname != null ? " " + surname : "");
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "m2m_users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notifications", joinColumns = @JoinColumn(name = "user_id"))
    private List<NotificationEntity> notifications = new ArrayList<>();

    public UserEntity(Long id) {
        super(id);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[name= " + username + ", email=" + email + ", name=" + name +
                ", surname=" + surname + "]";
    }

}
