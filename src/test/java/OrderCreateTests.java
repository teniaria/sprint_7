import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.order.OrderClient;
import ru.praktikum.order.OrderGenerator;
import ru.praktikum.order.Orders;

import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.constant.ScooterColorUtils.COLOR_BLACK;
import static ru.praktikum.constant.ScooterColorUtils.COLOR_GREY;

@RunWith(Parameterized.class)
public class OrderCreateTests {

    public static final String FIELD_TRACK = "track";
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final OrderClient orderClient = new OrderClient();
    private final OrderGenerator generator = new OrderGenerator();

    public OrderCreateTests(String firstName, String lastName, String address, String metroStation, String phone,
                            Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters(name = "{index} : color {7}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Иван", "Иванов", "г.Москва ул.Первая д.1 кв.1", "Бульвар Рокоссовского", "71111111111", 1, "2024-01-16", "grey", new String[]{COLOR_GREY}},
                {"Мария-Виктория", "Петрова-Иванова", "г.Москва ул.Вторая д.2", "Речной вокзал", "72222222222", 2, "2024-01-17", "black", new String[]{COLOR_BLACK}},
                {"Марина123", "Муркина123", "г Москва ул Третья д 3", "Новогиреево", "73333333333", 3, "2024-03-05", "black and grey", new String[]{COLOR_BLACK, COLOR_GREY}},
                {"", "", "", "", "", 5, "2024-01-20", "не указан", new String[]{}},
        };
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        Orders orders = generator.getOrder(firstName, lastName, address, metroStation, phone,
                rentTime, deliveryDate, comment, color);
        Response response = orderClient.createOrder(orders);
        response.then().log().all().statusCode(HttpStatus.SC_CREATED)
                .and().assertThat().body(FIELD_TRACK, notNullValue());
    }
}