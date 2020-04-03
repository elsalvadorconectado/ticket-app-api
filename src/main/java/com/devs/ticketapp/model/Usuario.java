package com.devs.ticketapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	@Getter()
	@Setter()
	private Integer idUsuario;
	
	@Column(name="nombre")
	@Getter()
	@Setter()
	private String nombre;
	
	@Column(name="password")
	@Getter()
	@Setter()
	private String password;
	
	@Column(name="telefono")
	@Getter()
	@Setter()
	private char telefono;
	
	@Column(name="tipo_notif")
	@Getter()
	@Setter()
	private char notificacion;
}
