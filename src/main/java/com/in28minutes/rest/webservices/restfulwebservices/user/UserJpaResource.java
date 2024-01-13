package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJpaResource {

  private UserRepository repository;
  private PostRepository postRepository;

  public UserJpaResource(
    UserRepository repository,
    PostRepository postRepository
  ) {
    this.repository = repository;
    this.postRepository = postRepository;
  }

  @GetMapping("/jpa/users")
  public List<User> retrieveAllUsers() {
    return repository.findAll();
  }

  @GetMapping("/jpa/users/{id}")
  public EntityModel<User> retrieveUser(@PathVariable Integer id) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("id: " + id);
    }
    EntityModel<User> entityModel = EntityModel.of(user.get());
    WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(
      WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()
    );
    entityModel.add(link.withRel("all-users"));
    return entityModel;
  }

  @PostMapping("/jpa/users")
  public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
    User savedUser = repository.save(user);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedUser.getId())
      .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/jpa/users/{id}")
  public void deleteUser(@PathVariable Integer id) {
    repository.deleteById(id);
  }

  @GetMapping("/jpa/users/{id}/post")
  public List<Post> retrievePostsForUser(@PathVariable Integer id) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("id: " + id);
    }
    return user.get().getPost();
  }

  @PostMapping("/jpa/users/{id}/post")
  public ResponseEntity<Object> createPostForUser(
    @PathVariable Integer id,
    @Valid @RequestBody Post post
  ) {
    Optional<User> user = repository.findById(id);
    if (user.isEmpty()) {
      throw new UserNotFoundException("id: " + id);
    }
    post.setUser(user.get());
    Post savedPost = postRepository.save(post);
    URI location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{id}")
      .buildAndExpand(savedPost.getId())
      .toUri();
    return ResponseEntity.created(location).build();
  }
}
