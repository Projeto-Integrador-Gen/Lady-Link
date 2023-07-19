package com.generation.ladylink.controller;

import com.generation.ladylink.model.Usuario;
import com.generation.ladylink.model.UsuarioLogin;
import com.generation.ladylink.repository.UsuarioRepository;
import com.generation.ladylink.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRespository;

    @BeforeAll
    void start() {

        usuarioRespository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", ""));
    }

    @Test
    @DisplayName("Cadastrar um Usuário")
    public void deveCriarUmUsuario() {

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Mike", "mike.marquezini@mike.com.br", "12345678", "-"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());

    }

    @Test
    @DisplayName("Não Deve permitir a duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Lucas Reck", "Lucas_Reck@enois.com.br", "12345678", "-"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Lucas Reck", "Lucas_Reck@enois.com.br", "12345678", "-"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());

    }

    @Test
    @DisplayName("Deve Atualizar os dados do Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Bianca", "barbara.bianca@bianca.com", "12345678", "-"));

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(
                usuarioCadastrado.get().getId(), "Bianca", "barbara@bianca.com", "12345678", "-"));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

    }

    @Test
    @DisplayName("Deve Listar todos os Usuários")
    public void deveMostrarTodosUsuarios() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Reynaldo", "reynaldo.sales@reynaldo.com", "12345678", "-"));

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Sartori", "fernando.sartori@fernando.com.br", "ramon78", "-"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }

    @Test
    @DisplayName("Listar Um Usuário Específico")
    public void deveListarApenasUmUsuario() {

        Optional<Usuario> usuarioBusca = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Stella", "stella@email.com.br", "laura12345", "-"));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/" + usuarioBusca.get().getId(), HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());

    }

    @Test
    @DisplayName("Login do Usuário")
    public void deveAutenticarUsuario() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Usuario Controller Test", "teste@email.com.br", "13465278", "-"));

        HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(new UsuarioLogin(0L,
                "", "teste@email.com.br", "13465278", "", ""));

        ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate
                .exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());

    }

}