package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import ru.practicum.yandex.ObjectGenerator;
import ru.practicum.yandex.user.User;
import ru.practicum.yandex.user.UserCreateResponse;
import ru.practicum.yandex.network.UserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {
    private UserManager userManager;
    private User user;
    private final String expectedMessage = "email or password are incorrect";
    private String accessToken;

    @Before
    public void setUp() {
        userManager = new UserManager();
        user = ObjectGenerator.generateUser();
        userManager.createUser(user);
    }

    @Test
    @Description("Логин существующего пользователя")
    public void testLoginExistingUser() {
        accessToken = userManager.loginUser(user)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract()
                .body()
                .as(UserCreateResponse.class)
                .getAccessToken();
    }

    @Test
    @Description("Логин существующего пользователя с неверным паролем")
    public void testLoginExistingUserWithWrongPassword() {
        User wrongUser = ObjectGenerator.generateUser();
        wrongUser.setEmail(user.getEmail());
        userManager.loginUser(wrongUser)
                .assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo(expectedMessage));
    }

    @Test
    @Description("Логин существующего пользователя с неверным email")
    public void testLoginExistingUserWithWrongEmail() {
        User wrongUser = ObjectGenerator.generateUser();
        wrongUser.setPassword(user.getPassword());
        userManager.loginUser(wrongUser)
                .assertThat().
                statusCode(401)
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
