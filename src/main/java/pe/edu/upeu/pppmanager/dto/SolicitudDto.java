package pe.edu.upeu.pppmanager.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class SolicitudDto {
    private Long id; 
    private String estado; 
    private LocalDateTime fechaCreacion; // Fecha de creaci
    private EstudianteDTO estudiante; // Datos del estudiante
    private EmpresaDTO empresa; // Datos completos de la empresa seleccionada
    private LineaCarreraDTO lineaCarrera; // Datos completos de la línea de carrera seleccionada
    private List<EmpresaDTO> empresas; // Lista de empresas disponibles (solo para la creación)
    private List<LineaCarreraDTO> lineasCarrera; // Lista de líneas de carrera disponibles (solo para la creación)

    @Data
    public static class EstudianteDTO {
        private String codigo; // Código del estudiante
        private String nombre; // Nombre del estudiante
        private String apellido; // Apellido del estudiante
        private String dni; // DNI del estudiante
        private String telefono; // Teléfono del estudiante
        private String correo; // Correo del estudiante
    }

    @Data
    public static class EmpresaDTO {
        private Long id; // ID de la empresa
        private String razonSocial; // Razón social de la empresa
        private String direccion; // Dirección de la empresa
        private String email; // Email de la empresa
        private String telefono; // Teléfono de la empresa
    }

    @Data
    public static class LineaCarreraDTO {
        private Long id; // ID de la línea de carrera
        private String nombre; // Nombre de la línea de carrera
    }
}