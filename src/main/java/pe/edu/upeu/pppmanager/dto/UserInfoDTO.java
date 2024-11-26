package pe.edu.upeu.pppmanager.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor 
@NoArgsConstructor  
@Getter             
@Setter
public class UserInfoDTO {
    private Long idUsuario;
    private String namenombreCompleto;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String dni;
    private List<String> roles;
    private String imgPerfil;
    
}