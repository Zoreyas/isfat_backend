package org.erusakov.backend.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.erusakov.backend.entities.BaseEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity extends BaseEntity<Long> {

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @Column(name = "phone")
    private String phone;

    public String getFullName() {
        return user.getName() + " " + user.getSurname();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id=" + getId() + '\'' +
                "licenseNumber='" + licenseNumber + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}