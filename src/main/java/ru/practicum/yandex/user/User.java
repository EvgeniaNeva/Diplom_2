package ru.practicum.yandex.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String email;
    @Setter
    private String name;
    private String password;

    public void withParams(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void withOutEmail(String password, String name) {
        this.password = password;
        this.name = name;
    }

    public void withOutPassword(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public void withOutName(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
