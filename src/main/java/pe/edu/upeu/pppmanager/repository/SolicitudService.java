package pe.edu.upeu.pppmanager.repository;

import java.util.List;

import pe.edu.upeu.pppmanager.dto.SolicitudDto;



public interface SolicitudService {
	 SolicitudDto obtenerDatosEstudianteConEmpresasYLineas();
	    void guardarSolicitud(SolicitudDto solicitudDTO);
	    List<SolicitudDto> listarSolicitudes();
	    List<SolicitudDto> listarSolicitudesPorEstudiante(Long estudianteId); // Nueva funcionalidad
}
    
