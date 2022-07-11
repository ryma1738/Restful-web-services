package com.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    UserRepository userRep;

    @Autowired
    PostRepository postRep;

    @GetMapping("/users")
    public List<User> findAll() {
        return userRep.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findById(@PathVariable int id) {
        Optional<User> user = userRep.findById(id);

        if (user.isPresent()) {
            EntityModel<User> model = EntityModel.of(user.get());
            WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findAll());
            model.add(linkToUsers.withRel("all-users"));
            return model;
        } else throw new UserNotFoundException("id-" + id);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
        User savedUser = userRep.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        Optional<User> user = userRep.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("id-" + id);
        userRep.deleteById(id);
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> getAllUserPosts(@PathVariable int id) {
        Optional<User> user = userRep.findById(id);
        if(!user.isPresent()) throw new UserNotFoundException("id-" + id);
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userRep.findById(id);
        if(!userOptional.isPresent()) throw new UserNotFoundException("id-" + id);

        User user = userOptional.get();

        post.setUser(user);

        postRep.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    // old Non-Database Routes
//    @Autowired
//    UserDaoService userService;
//
//    @GetMapping("/old/users")
//    public List<User> findAllUsers() {
//        return userService.findAll();
//    }
//
//    @GetMapping("/old/users/{id}")
//    public EntityModel<User> findUser(@PathVariable int id) {
//        User user = userService.findOne(id);
//        if (user == null) throw new UserNotFoundException("id- " + id);
//
//        EntityModel<User> model = EntityModel.of(user);
//        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findAllUsers());
//        model.add(linkToUsers.withRel("all-users"));
//        return model;
//    }
//
//    @PostMapping("/old/users")
//    public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
//        User savedUser = userService.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedUser.getId())
//                .toUri();
//        return ResponseEntity.created(location).build();
//
//    }
//
//    @DeleteMapping("/old/users/{id}")
//    public void deleteUser(@PathVariable int id) {
//        User user = userService.deleteById(id);
//        if (user == null) throw new UserNotFoundException("id- " + id);
//
//    }
//
//    //uses Dynamic filtering to only include certain fields in the response
//    @GetMapping("/old/users/filtered/{id}")
//    public MappingJacksonValue findFilteredUser(@PathVariable int id) {
//        User user = userService.findOne(id);
//        if (user == null) throw new UserNotFoundException("id- " + id);
//
//        //Adding Links to get all users
//        EntityModel<User> model = EntityModel.of(user);
//        WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).findAllUsers());
//        model.add(linkToUsers.withRel("all-users"));
//
//        //Dynamic Filtering (works on list too)
//        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
//        FilterProvider filters = new SimpleFilterProvider().addFilter("TestFilter", filter);
//        MappingJacksonValue mapping = new MappingJacksonValue(model);
//        mapping.setFilters(filters);
//
//
//        return mapping;
//    }

}
