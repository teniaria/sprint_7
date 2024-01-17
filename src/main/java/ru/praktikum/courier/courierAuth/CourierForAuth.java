package ru.praktikum.courier.courierAuth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourierForAuth {
    private String login;
    private String password;
}