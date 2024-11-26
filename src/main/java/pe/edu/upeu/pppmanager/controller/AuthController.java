package pe.edu.upeu.pppmanager.controller;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.pppmanager.config.JwtTokenProvider;
import pe.edu.upeu.pppmanager.dto.JwtResponse;
import pe.edu.upeu.pppmanager.dto.LoginRequest;
import pe.edu.upeu.pppmanager.dto.UserInfoDTO;
import pe.edu.upeu.pppmanager.entity.Estudiante;
import pe.edu.upeu.pppmanager.entity.Persona;
import pe.edu.upeu.pppmanager.entity.Usuario;
import pe.edu.upeu.pppmanager.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    @GetMapping("/user-info")
    public ResponseEntity<UserInfoDTO> getUserInfo(Authentication authentication) {
        String username = authentication.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Persona persona = usuario.getPersona();
        Set<Estudiante> estudiante = persona.getEstudiante();
        Long idUsuario = usuario.getId();
        String nombreCompleto = usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido();
        String nombre = usuario.getPersona().getNombre();
        String apellido = usuario.getPersona().getApellido();
        String email = usuario.getPersona().getCorreo();
        String dni = usuario.getPersona().getDni();
        String img_perfil = usuario.getImg_perfil();
        
        List<String> roles = usuario.getUsuarioRoles().stream()
                .map(usuarioRol -> usuarioRol.getRol().getName()) 
                .toList();

        UserInfoDTO userInfo = new UserInfoDTO(idUsuario, nombreCompleto, nombre, apellido, email, username, dni, roles, img_perfil);

        return ResponseEntity.ok(userInfo);
    }
}


