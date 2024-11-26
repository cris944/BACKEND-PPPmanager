package pe.edu.upeu.pppmanager.controller;

import pe.edu.upeu.pppmanager.dto.SolicitudDto;
import pe.edu.upeu.pppmanager.repository.SolicitudService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/solicitudes")
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

	 private static final Logger logger = LoggerFactory.getLogger(SolicitudController.class);

	    @Autowired
	    private SolicitudService solicitudService;


	    @GetMapping
	    public ResponseEntity<Map<String, Object>> obtenerDatosInicialesYSolicitudes() {
	        logger.info("Solicitando datos iniciales y lista de solicitudes...");
	        try {

	            SolicitudDto datosIniciales = solicitudService.obtenerDatosEstudianteConEmpresasYLineas();

	            List<SolicitudDto> solicitudes = solicitudService.listarSolicitudes();


	            Map<String, Object> respuesta = new HashMap<>();
	            respuesta.put("datosIniciales", datosIniciales);
	            respuesta.put("solicitudes", solicitudes);

	            logger.info("Datos iniciales y solicitudes obtenidos exitosamente.");
	            return ResponseEntity.ok(respuesta);

	        } catch (Exception e) {
	            logger.error("Error al obtener datos iniciales y solicitudes: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	    @PostMapping
	    public ResponseEntity<Map<String, String>> guardarSolicitud(@Valid @RequestBody SolicitudDto solicitudDto) {
	        logger.info("Guardando nueva solicitud...");
	        try {

	            if (solicitudDto.getEmpresa() == null || solicitudDto.getEmpresa().getId() == null) {
	                throw new RuntimeException("Debe seleccionar una empresa válida.");
	            }
	            if (solicitudDto.getLineaCarrera() == null || solicitudDto.getLineaCarrera().getId() == null) {
	                throw new RuntimeException("Debe seleccionar una línea de carrera válida.");
	            }
	            
	            solicitudService.guardarSolicitud(solicitudDto);
	            logger.info("Solicitud guardada exitosamente.");
	            
	            Map<String, String> response = new HashMap<>();
	            response.put("message", "Solicitud guardada exitosamente.");
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        } catch (RuntimeException e) {
	            logger.error("Error al guardar solicitud: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
	        } catch (Exception e) {
	            logger.error("Error inesperado al guardar solicitud: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error interno del servidor."));
	        }
	    }

	    @GetMapping("/list")
	    public ResponseEntity<List<SolicitudDto>> listarSolicitudes() {
	        logger.info("Listando todas las solicitudes...");
	        try {
	            List<SolicitudDto> solicitudes = solicitudService.listarSolicitudes();
	            return ResponseEntity.ok(solicitudes);
	        } catch (Exception e) {
	            logger.error("Error al listar solicitudes: {}", e.getMessage(), e);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }

	}