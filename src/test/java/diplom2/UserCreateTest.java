package diplom2;

import io.qameta.allure.junit4.DisplayName;
import ru.practicum.yandex.user.UserCreateResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.practicum.yandex.user.User;
import ru.practicum.yandex.ObjectGenerator;
import ru.practicum.yandex.network.UserManager;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateTest {

    private UserManager userManager;
    private String accessToken;
    private final String expectedMessage = "Email, password and name are required fields";

    @Before
    public void setup() {
        userManager = new UserManager();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void testCreateUniqueUser() {
        accessToken = userManager.createUser(ObjectGenerator.generateUser())
                .body("success", equalTo(true))
                .statusCode(200)
                .extract()
                .body()
                .as(UserCreateResponse.class)
                .getAccessToken();
    }

    @Test
    @DisplayName("Создание существующего пользователя")
    public void testCreateExistingUser() {
        User user = ObjectGenerator.generateUser();
        String expectedMessage = "User already exists"; // Замените на ваше ожидаемое сообщение
        userManager.createUser(user);
        userManager.createUser(user)
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage)); // Создаем пользователя ValidatableResponse response = userManager.createUser (user); // Пытаемся создать его снова
    }

    @Test
    @DisplayName("Создание пользователя без email")
    public void testCreateUserWithOutEmail() {
        userManager.createUser(ObjectGenerator.generateUserWithOutEmail())
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    public void testCreateUserWithOutPassword() {
        userManager.createUser(ObjectGenerator.generateUserWithOutPassword())
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithWithOutName() {
        userManager.createUser(ObjectGenerator.generateUserWithOutName())
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @DisplayName("Создание пользователя без параметров")
    public void createUserWithWithOutParams() {
        userManager.createUser(ObjectGenerator.generateUserWithOutParams())
                .assertThat()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }

    @After
    public void tearDown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userManager.deleteUser(accessToken);
        }
    }
}
