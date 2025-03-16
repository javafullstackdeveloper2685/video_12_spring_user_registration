package org.sergei.lunchApp.model;

import jakarta.persistence.*;
import lombok.*;
import org.sergei.lunchApp.model.enums.UserRole;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Entity
@Table(name="user")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @Column(name="username", nullable = false, unique = true)
    private String userName;

    @Column(name ="password", nullable = false)
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="balance")
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private UserRole userRole = UserRole.USER;

    @Column(name="created_at", updatable = false)
    private Date createdAt;

    @PrePersist
    protected  void onCreate(){
        createdAt = new Date();
    }

}
