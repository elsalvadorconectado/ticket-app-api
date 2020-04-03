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
@Table(name = "empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_empresa")
	@Getter()
	@Setter()
	private Integer idEmpresa;
	
	@Column(name="nombre")
	@Getter()
	@Setter()
	private String nombre;
	
	@Column(name="nombre_corto")
	@Getter()
	@Setter()
	private String nombreCorto;
	
	@Column(name="nit")
	@Getter()
	@Setter()
	private String nit;
	
	@Column(name="id_tipo_empresa")
	@Getter()
	@Setter()
	private Integer idTipoEmpresa;
}
