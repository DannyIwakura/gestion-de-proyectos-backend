package com.gruposolutia.gestion_proyectos.model;

import java.util.List;

import com.gruposolutia.gestion_proyectos.enums.EstadoProyecto;

public class Proyecto {
	
    private Long id;

    private String nombre;

    private String descripcion;

    private EstadoProyecto estado;

    private List<String> tecnologias;

	public Proyecto(Long id, String nombre, String descripcion, EstadoProyecto estado, List<String> tecnologias) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tecnologias = tecnologias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoProyecto getEstado() {
		return estado;
	}

	public void setEstado(EstadoProyecto estado) {
		this.estado = estado;
	}

	public List<String> getTecnologias() {
		return tecnologias;
	}

	public void setTecnologias(List<String> tecnologias) {
		this.tecnologias = tecnologias;
	}
}


