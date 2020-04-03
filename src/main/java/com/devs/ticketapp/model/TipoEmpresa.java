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
@Table(name = "tipo_empresa")
public class TipoEmpresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_empresa")
	@Getter()
	@Setter()
	private Integer idTipoEmpresa;
	
	@Column(name="tipo_empresa")
	@Getter()
	@Setter()
	private String tipo;
	
	@Column(name="descripcion")
	@Getter()
	@Setter()
	private String descripcion;
	
	@Column(name="imagen")
	@Getter()
	@Setter()
	private String imagen;
}
