package com.devs.ticketapp.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "establecimiento")
public class Establecimiento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_establecimiento")
	@Getter()
	@Setter()
	private Integer idEstablecimiento;
	
	@Column(name="id_empresa")
	@Getter()
	@Setter()
	private Integer idEmpresa;
	
	@Column(name="nombre_establecimiento", length = 250)
	@Getter()
	@Setter()
	private String nombre;
	
	@Column(name="direccion1", length = 250)
	@Getter()
	@Setter()
	private String direccion1;
	
	@Column(name="direccion2", length = 250)
	@Getter()
	@Setter()
	private String direccion2;
	
	@Column(name="duracion_vencimiento_minutos")
	@Getter()
	@Setter()
	private Integer duracionvenmin;
	
	@Column(name="latitud")
	@Getter()
	@Setter()
	private BigDecimal latitud;
	
	@Column(name="longitud")
	@Getter()
	@Setter()
	private BigDecimal longitud;
}
