package com.generation.ladylink.model;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // gerar tabela no banco de dados
@Table(name = "tb_postagens") // nomeando a tabela do banco de dados
public class Postagem {

	@Id // indicando que esse atributo do tipo id será a minha primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // essa chave criada sera gerada pelo banco de dados, e o
														// strategy irá gerar um auto_increment
	private long id;

	@NotBlank(message = "Este atributo é de preenchimento obrigatório")
	@Size(min = 1, max = 80, message = "Este atributo tem que ter no minimo 1 caracteres e no máximo 80 caracteres")
	private String titulo;

	@NotBlank(message = "Este atributo é de preenchimento obrigatório")
	@Size(min = 10, max = 1000, message = "Este atributo tem que ter no minimo 10 caracteres e no máximo 1000 caracteres")
	private String conteudo;


	//contador de Views para as postagens
	private int visualiacao;

	public int getVisualiacao() {
		return visualiacao;
	}

	public void setVisualiacao(int visualiacao) {
		this.visualiacao = visualiacao;
	}

	@UpdateTimestamp
	private LocalDateTime data;
	
	@ManyToOne
	@JsonIgnoreProperties("usuario")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

}
