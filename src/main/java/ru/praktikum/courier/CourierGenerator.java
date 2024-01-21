package ru.praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;
import ru.praktikum.courier.courier.auth.CourierForAuth;
import ru.praktikum.courier.courier.auth.CourierForAuthWithoutLogin;
import ru.praktikum.courier.courier.auth.CourierForAuthWithoutPassword;

public class CourierGenerator {
    private final String password = "password";
    private final String firstName = "name";

    public Courier getCourier() {
        return Courier.builder()
                .login(RandomStringUtils.randomAlphanumeric(10))
                .password(password)
                .firstName(firstName)
                .build();
    }

    public CourierWithoutPassword getCourierWithoutPassword() {
        return new CourierWithoutPassword(RandomStringUtils.randomAlphanumeric(10));
    }

    public CourierWithoutLogin getCourierWithoutLogin() {
        return new CourierWithoutLogin(password);
    }


    public CourierForAuth getCourierForAuth(Courier courier) {
        return CourierForAuth.builder()
                .login(courier.getLogin())
                .password(courier.getPassword())
                .build();
    }

    public CourierForAuthWithoutLogin getCourierForAuthWithoutLogin(Courier courier) {
        return new CourierForAuthWithoutLogin(courier);
    }

    public CourierForAuthWithoutPassword getCourierForAuthWithoutPassword(Courier courier) {
        return new CourierForAuthWithoutPassword(courier);
    }

}
