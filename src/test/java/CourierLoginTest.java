import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.courier.*;
import ru.praktikum.courier.courier.auth.CourierForAuth;
import ru.praktikum.courier.courier.auth.CourierForAuthWithoutLogin;
import ru.praktikum.courier.courier.auth.CourierForAuthWithoutPassword;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;


public class CourierLoginTest extends BaseTest {
    private static final String FIELD_MESSAGE = "message";
    public static final String FIELD_ID = "id";
    public static final String MESSAGE_NO_ENOUGH_DATA = "Недостаточно данных для входа";
    public static final String MESSAGE_COURIER_NOT_FOUND = "Учетная запись не найдена";
    private final CourierGenerator generator = new CourierGenerator();
    private final CourierClient courierClient = new CourierClient();
    private CourierForAuth courierForAuth;

    @Before
    public void setUp() {
        courier = generator.getCourier();
        courierClient.create(courier);
        courierForAuth = generator.getCourierForAuth(courier);
    }

    @Test
    @DisplayName("Курьер авторизирован")
    @Description("Проверка авторизации курьера с корректным логином и паролем")
    public void checkCreatingCourierLoginTest() {
        Response response = courierClient.login(courierForAuth);
        response.then().log().all().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body(FIELD_ID, allOf(notNullValue(), greaterThan(0)));
    }

    @Test
    @DisplayName("Курьер авторизирован без поля login")
    @Description("Проверка авторизации курьера без ввода логина")
    public void checkVerificationWithoutLoginAuthorization() {
        CourierForAuthWithoutLogin courierForAuthWithoutLogin = generator.getCourierForAuthWithoutLogin(courier);
        Response response = courierClient.loginWithoutLogin(courierForAuthWithoutLogin);
        response.then().log().all().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body(FIELD_MESSAGE, equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Курьер авторизирован без поля password")
    @Description("Проверка авторизации курьера без ввода пароля")
    public void checkVerificationWithoutPasswordAuthorization() {
        CourierForAuthWithoutPassword courierForAuthWithoutPassword = generator.getCourierForAuthWithoutPassword(courier);
        Response response = courierClient.loginWithoutPassword(courierForAuthWithoutPassword);
        response.then().log().all().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body(FIELD_MESSAGE, equalTo(MESSAGE_NO_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Курьер авторизирован под несуществующим login и password")
    @Description("Проверка авторизации курьера в системе под несуществующим пользователем (логин и пароль)")
    public void checkVerificationNonExistent() {
        Courier courierNotCreate = generator.getCourier();
        CourierForAuth courierForAuthNotCreate = generator.getCourierForAuth(courierNotCreate);
        Response response = courierClient.login(courierForAuthNotCreate);
        response.then().log().all().statusCode(HttpStatus.SC_NOT_FOUND)
                .and().body(FIELD_MESSAGE, equalTo(MESSAGE_COURIER_NOT_FOUND));
    }
}
