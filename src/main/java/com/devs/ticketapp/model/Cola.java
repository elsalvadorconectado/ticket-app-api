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
@Table(name = "cola")
public class Cola {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_cola")
	@Getter()
	@Setter()
	private Integer idCola;
	
	@Column(name="descripcion")
	@Getter()
	@Setter()
	private String descripcion;
	
	@Column(name="id_establecimiento")
	@Getter()
	@Setter()
	private Integer idEstablecimiento;
	
	@Column(name="cupos")
	@Getter()
	@Setter()
	private Short cupos;
	
	@Column(name="ultimo_atendido")
	@Getter()
	@Setter()
	private Long ultimoAtendido;
}
