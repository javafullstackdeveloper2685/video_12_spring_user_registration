package org.sergei.lunchApp.dto;

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
public class User {
    private UUID uuid;
    private String userName;
    private String password;
    private String name;
    private String surname;
    private BigDecimal balance = BigDecimal.ZERO;
    private UserRole userRole = UserRole.USER;
    private Date createdAt;
}
