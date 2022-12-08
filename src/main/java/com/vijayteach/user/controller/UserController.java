package com.vijayteach.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vijayteach.user.model.User;
import com.vijayteach.user.repository.UserRepository;

import lombok.var;

@RestController
public class UserController {

	@Autowired
	UserRepository userservice;

	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {

		final var userDb = userservice.save(user);

		final var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(userDb.getId()).toUri();
		return  ResponseEntity.created(location).body(userDb);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable("id") String id) {

		final var userDb = userservice.findById(Integer.parseInt(id))
				.orElseThrow(() -> new RuntimeException(String.format("User with ID as %s not found", id)));

		userservice.delete(userDb);

		return ResponseEntity.noContent().build();
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser( @PathVariable("id") String id, @Valid @RequestBody User user) {

		final var userDb = userservice.findById(Integer.parseInt(id))
				.orElseThrow(() -> new RuntimeException(String.format("User with ID as %s not found", id)));

		userDb.setId(userDb.getId());
		userservice.save(userDb);

		return ResponseEntity.accepted().build();
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> retrieveAllUser() {

		return ResponseEntity.ok(userservice.findAll());
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<EntityModel<User>> retrieveUserById(@PathVariable("id") String id) {

		final var userDb = userservice.findById(Integer.parseInt(id))
				.orElseThrow(() -> new RuntimeException(String.format("User with ID as %s not found", id)));

		final EntityModel<User> model = EntityModel.of(userDb);

		final WebMvcLinkBuilder newLink = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUser());
		final Link link = newLink.withRel("Additional User info Link");
		model.add(link);

		return ResponseEntity.ok(model);
	}

}
