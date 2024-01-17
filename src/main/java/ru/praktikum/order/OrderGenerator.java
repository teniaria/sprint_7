package ru.praktikum.order;

public class OrderGenerator {
    public Orders getOrder(String firstName, String lastName, String address, String metroStation, String phone,
                           Integer rentTime, String deliveryDate, String comment, String[] color) {
        return Orders.builder()
                .firstName(firstName)
                .lastName(lastName)
                .address(address)
                .metroStation(metroStation)
                .phone(phone)
                .rentTime(rentTime)
                .deliveryDate(deliveryDate)
                .comment(comment)
                .color(color)
                .build();
    }
}
