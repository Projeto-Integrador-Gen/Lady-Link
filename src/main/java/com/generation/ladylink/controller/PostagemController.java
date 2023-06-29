package com.generation.ladylink.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.generation.ladylink.repository.TemaRepository;
import com.generation.ladylink.model.Postagem;
import com.generation.ladylink.repository.PostagemRepository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*" )
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

	@GetMapping
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);

		if (postagemOptional.isPresent()) {
			Postagem postagem = postagemOptional.get();
			postagem.setVisualiacao(postagem.getVisualiacao() + 1);
			postagemRepository.save(postagem);
			return ResponseEntity.ok(postagem);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		if (temaRepository.existsById(postagem.getTema().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Postagem> put(@PathVariable Long id, @Valid @RequestBody Postagem postagem) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);

		if (postagemOptional.isPresent()) {
			Postagem postagemAtual = postagemOptional.get();

			// Mantém a contagem de visualizações da postagem atual
			postagem.setVisualiacao(postagemAtual.getVisualiacao());

			if (temaRepository.existsById(postagem.getTema().getId())) {
				postagem.setId(id); // Define o ID da postagem atualizada
				return ResponseEntity.ok(postagemRepository.save(postagem));
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);

		if (postagemOptional.isPresent()) {
			postagemRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
