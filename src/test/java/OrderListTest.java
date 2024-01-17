import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.praktikum.order.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    public static final String FIELD_ORDERS = "orders";
    OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Список заказов")
    @Description("Проверка получения списка заказов")
    public void getListOrdersTest() {
        Response response = orderClient.getOrders();
        response.then().log().all().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body(FIELD_ORDERS, notNullValue());
    }
}