package ru.practicum.yandex.network;

import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.practicum.yandex.Constants.Constants;
import static io.restassured.RestAssured.given;

public class NetworkManager {
    private final RestAssuredConfig config = RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation())
            .redirect(new RedirectConfig().followRedirects(true));

    public RequestSpecification request() {
        return given()
                .config(config)
                .contentType(ContentType.JSON)
                .baseUri(Constants.BASE_URL);
    }
}
