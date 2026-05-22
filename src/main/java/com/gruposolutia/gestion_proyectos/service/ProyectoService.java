package com.gruposolutia.gestion_proyectos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gruposolutia.gestion_proyectos.enums.EstadoProyecto;
import com.gruposolutia.gestion_proyectos.model.Proyecto;

@Service
public class ProyectoService {
	
	
	public List<Proyecto> obtenerProyectos() {
		return List.of(
				new Proyecto(
						1L,
						"Portal RHH",
						"Gestión de empleados",
						EstadoProyecto.MANTENIMIENTO,
						List.of("Vue", "Spring Boot", "MySQL")),
				new Proyecto(
						2L,
						"Sistema de Inventario",
						"Gestión de inventario del stock de la empresa",
						EstadoProyecto.MANTENIMIENTO,
						List.of("PHP", "Laravel", "MySQL")),
				new Proyecto(
						3L,
						"Aplicación para verificación de Equipos",
						"Aplicación para controlar la stock de inventario de equipos y su verificación",
						EstadoProyecto.PRODUCCION,
						List.of("Android","MySQL")),
				new Proyecto(
						4L,
						"Gestión de los clientes y facturas",
						"Aplicación para controlar las relaciones con los clientes y las facturas",
						EstadoProyecto.DESARROLLO,
						List.of("Phyron", "Odoo","Postgres"))
				);
	}

}
