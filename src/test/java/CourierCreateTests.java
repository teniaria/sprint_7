import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.courier.CourierClient;
import ru.praktikum.courier.CourierGenerator;
import ru.praktikum.courier.CourierWithoutLogin;
import ru.praktikum.courier.CourierWithoutPassword;

import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreateTests extends BaseTest {
    private static final String FIELD_MESSAGE = "message";
    private static final String FIELD_OK = "ok";
    private static final String MESSAGE_EXISTING_LOGIN = "Этот логин уже используется. Попробуйте другой.";
    private static final String MESSAGE_WITHOUT_REQUIRED_FIELDS = "Недостаточно данных для создания учетной записи";


    CourierGenerator generator = new CourierGenerator();
    CourierClient courierClient = new CourierClient();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Создание учетной записи курьера с параметрами логин, пароль, имя")
    @Description("Проверка состояние кода и значений для полей /api/v1/courier")
    public void createCourierTest() {
        courier = generator.getCourier();
        Response response = courierClient.create(courier);
        response.then().log().all().assertThat().statusCode(HttpStatus.SC_CREATED)
                .and().body(FIELD_OK, equalTo(true));
    }

    @Test
    @DisplayName("Создание курьеров с одинаковыми логинами")
    @Description("Проверка состояние кода и сообщение при создании двух курьеров с одинаковыми логинами")
    public void creatingTwoIdenticalLoginCouriers() {
        courier = generator.getCourier();
        Response response = courierClient.create(courier);
        Response conflictResponse = courierClient.create(courier);
        conflictResponse.then().log().all().statusCode(HttpStatus.SC_CONFLICT)
                .and().body(FIELD_MESSAGE, equalTo(MESSAGE_EXISTING_LOGIN));
    }

    @Test
    @DisplayName("Создание курьера без поля name")
    @Description("Проверка состояние кода и сообщение при создании курьера без имени курьера")
    public void creatingCourierWithoutFirstName() {
        courier = generator.getCourier();
        Response response = courierClient.create(courier);
        response.then().log().all().statusCode(HttpStatus.SC_CREATED)
                .and().body(FIELD_OK, equalTo(true));

    }


    @Test
    @DisplayName("Создание курьера поля login")
    @Description("Проверка состояние кода и сообщение при создании курьера без логина")
    public void creatingCourierWithoutLogin() {
            CourierWithoutLogin courierWithoutLogin = generator.getCourierWithoutLogin();
            Response response = courierClient.createWithoutLogin(courierWithoutLogin);
            response.then().log().all().statusCode(HttpStatus.SC_BAD_REQUEST)
                    .and().assertThat().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }

    @Test
    @DisplayName("Создание курьера без поля password")
    @Description("Проверка состояние кода и сообщение при создании курьера без пароля")
    public void creatingCourierWithoutPassword() {
        CourierWithoutPassword courierWithoutPassword = generator.getCourierWithoutPassword();
        Response response = courierClient.createWithoutPassword(courierWithoutPassword);
        response.then().log().all().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body(FIELD_MESSAGE, equalTo(MESSAGE_WITHOUT_REQUIRED_FIELDS));
    }
}

