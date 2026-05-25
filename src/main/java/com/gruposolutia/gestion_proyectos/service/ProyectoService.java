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
						List.of("Phyron", "Odoo","Postgres")),
				new Proyecto(
                        4L,
                        "Gestión Clientes y Facturas",
                        "CRM básico para clientes y facturación",
                        EstadoProyecto.PRODUCCION,
                        List.of("Python", "Odoo", "PostgreSQL")
                ),

                new Proyecto(
                        5L,
                        "Intranet Corporativa",
                        "Portal interno de comunicación",
                        EstadoProyecto.PRODUCCION,
                        List.of("React", "Node.js", "MongoDB")
                ),

                new Proyecto(
                        6L,
                        "Sistema de Tickets",
                        "Gestión de incidencias internas",
                        EstadoProyecto.DESARROLLO,
                        List.of("Vue", "Spring Boot", "PostgreSQL")
                ),

                new Proyecto(
                        7L,
                        "Dashboard de Analítica",
                        "Visualización de KPIs empresariales",
                        EstadoProyecto.DESARROLLO,
                        List.of("Angular", "TypeScript", "Chart.js")
                ),

                new Proyecto(
                        8L,
                        "Plataforma eLearning",
                        "Formación interna de empleados",
                        EstadoProyecto.MANTENIMIENTO,
                        List.of("Laravel", "MySQL", "Vue")
                ),

                new Proyecto(
                        9L,
                        "Sistema de Notificaciones",
                        "Servicio de alertas internas",
                        EstadoProyecto.PRODUCCION,
                        List.of("Spring Boot", "Redis", "Kafka")
                ),

                new Proyecto(
                        10L,
                        "Gestor de Documentos",
                        "Almacenamiento y control documental",
                        EstadoProyecto.DESARROLLO,
                        List.of("React", "AWS S3", "Node.js")
                )
		);
	}

}
