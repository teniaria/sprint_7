package ru.praktikum.courier.courier.auth;

import lombok.*;
import ru.praktikum.courier.Courier;

@Getter
@Setter
@NoArgsConstructor
public class CourierForAuthWithoutLogin {
    private String password;

    public CourierForAuthWithoutLogin(Courier courier) {
        this.password = courier.getPassword();
    }
}