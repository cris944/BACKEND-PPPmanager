package pe.edu.upeu.pppmanager.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upeu.pppmanager.entity.Usuario;
import pe.edu.upeu.pppmanager.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsername(username);

        List<GrantedAuthority> authorities = usuario.getUsuarioRoles()
        	    .stream()
        	    .map(ur -> new SimpleGrantedAuthority(ur.getRol().getName()))
        	    .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
            usuario.getUsername(),
            usuario.getPassword(),
            authorities
        );
    }
}

