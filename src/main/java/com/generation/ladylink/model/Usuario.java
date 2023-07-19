package com.generation.ladylink.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // gerar tabela no banco de dados
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id // indicando que esse atributo do tipo id será a minha primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank (message = "Este campo é OBRIGATÓRIO")
	@Size(min = 3, max = 100, message = "Este atributo tem que ter no minimo 3 caracteres e no máximo 100 caracteres")
	private String nome;
	
	@NotBlank (message = "Este campo é OBRIGATÓRIO")
	@Schema(example = "email@email.com.br")
	@Size(min = 3, max = 80, message = "Este atributo tem que ter no minimo 3 caracteres e no máximo 80 caracteres")

	private String usuario;
	
	@NotBlank (message = "Este campo é OBRIGATÓRIO") 
	@Size (min = 6, max= 77)
	private String senha;
	
	
	private String foto;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List <Postagem> postagem;

	public Usuario(Long id, String nome, String usuario, String senha, String foto) {
		super();
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	public Usuario() {

	}



	public List<Postagem> getPostagem() {
		return postagem;
	}


	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getFoto() {
		return foto;
	}


	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

}
