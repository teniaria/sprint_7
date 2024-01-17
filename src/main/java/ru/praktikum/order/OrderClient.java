package ru.praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.constant.Client;

public class OrderClient extends Client {
    private final static String ROOT = "/orders";

    @Step("Создание заказа")
    public Response createOrder(Orders orders) {
        return spec()
                .body(orders)
                .when()
                .post(ROOT);
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return spec()
                .get(ROOT);
    }

}