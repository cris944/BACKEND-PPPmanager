package pe.edu.upeu.pppmanager.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
