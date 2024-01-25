package ru.praktikum.courier.courier.auth;

import lombok.*;
import ru.praktikum.courier.Courier;

@Getter
@Setter
@NoArgsConstructor
public class CourierForAuthWithoutPassword {
    private String login;

    public CourierForAuthWithoutPassword(Courier courier) {
        this.login = courier.getLogin();
    }
}

