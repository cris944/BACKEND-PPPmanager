package pe.edu.upeu.pppmanager.service;

import pe.edu.upeu.pppmanager.entity.Usuario;

public interface UsuarioService {
    Usuario findByUsername(String username);
}
