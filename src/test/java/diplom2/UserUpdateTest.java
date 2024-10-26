package diplom2;

import io.qameta.allure.Description;
import ru.practicum.yandex.ObjectGenerator;
import ru.practicum.yandex.user.User;
import ru.practicum.yandex.network.UserManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserUpdateTest {
    private UserManager userManager;
    private User user;
    private String accessToken;

    @Before
    public void setup() {
        userManager = new UserManager();
        user = ObjectGenerator.generateUser();
        userManager.createUser(user);
    }

    @Test
    @Description("Изменение данных авторизованного пользователя")
    public void testChangeUserDataWithAuthorization() {
        accessToken = userManager.loginUser(user)
                .body("success", equalTo(true))
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getString("accessToken");
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        userManager.changeUserData(user, accessToken)
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test
    @Description("Изменение данных неавторизованного пользователя")
    public void testChangeUserDataWithoutAuthorization() {
        user.setName(RandomStringUtils.randomAlphanumeric(10));
        userManager.changeUserData(user, null)
                .assertThat().statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @After
    public void teardown() {
        if (accessToken != null && !accessToken.isEmpty()) {
            userManager.deleteUser(accessToken);
        }
    }
}
