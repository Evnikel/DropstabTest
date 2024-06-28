import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DropstabAuthTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.dropstab.com";  // Предполагаем, что API авторизации находится по этому адресу
    }

    @Test
    public void testPositiveLogin() {
        // Создаем JSON тело запроса
        String requestBody = "{\n" +
                "    \"email\": \"your-email@example.com\",\n" +
                "    \"password\": \"your-secure-password\"\n" +
                "}";

        // Выполняем POST запрос для авторизации
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/auth/login")  // Предполагаем, что эндпоинт для авторизации /auth/login
                .then()
                .statusCode(200)  // Ожидаем, что код ответа будет 200
                .body("message", equalTo("Login successful"))  // Проверяем сообщение в ответе (зависит от API)
                .extract()
                .response();

        // Дополнительные проверки
        String token = response.jsonPath().getString("token");
        assert token != null : "Token is null";

        System.out.println("Authorization token: " + token);
    }
}
