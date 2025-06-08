package com.example.employee.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "authusers")
public class AuthUser {

    @Id
    private String _id;

    @NotEmpty(message = "username cannot be empty")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
