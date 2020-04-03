package com.devs.ticketapp.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ticket")
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_ticket")
	@Getter()
	@Setter()
	private Long idTicket;
	
	@Column(name="posicion")
	@Getter()
	@Setter()
	private Short posicion;
	
	@Column(name="estado")
	@Getter()
	@Setter()
	private Integer estado;
	
	@Column(name="generado_en")
	@Getter()
	@Setter()
	private Timestamp generadoEn;
	
	@Column(name="inicio_turno")
	@Getter()
	@Setter()
	private Timestamp inicioTurno;
	
	@Column(name="vencimiento")
	@Getter()
	@Setter()
	private Timestamp vencimiento;
	
	@Column(name="atendido_en")
	@Getter()
	@Setter()
	private Timestamp atendidoEn;
	
	@Column(name="ultima_notificacion")
	@Getter()
	@Setter()
	private Timestamp ultimaNotificacion;
	
	@Column(name="id_usuario")
	@Getter()
	@Setter()
	private Integer idUsuario;
	
	@Column(name="id_cola")
	@Getter()
	@Setter()
	private Integer idCola;
}
