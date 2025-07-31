package com.mx.ProyectoChakray.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Usuario {
	private UUID id;
	private String correoE;
	private String nombre;
	private String celular;
	private String tax_id;
	private String crear_id;
	private String crear_fecha;
	private List<Direccion> direccion;
	 @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenna;

}