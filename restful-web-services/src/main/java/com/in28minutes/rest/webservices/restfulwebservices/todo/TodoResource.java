package com.in28minutes.rest.webservices.restfulwebservices.todo;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ✅ Add RestController so Spring Boot detects this as an API
//@RestController
@RequestMapping // ✅ Base URL for all endpoints
@CrossOrigin(origins = "http://localhost:3000") // ✅ Allow frontend requests
public class TodoResource {

    private final TodoService todoService;

    // ✅ Ensure Spring Boot injects the service correctly
    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    // ✅ Fetch all todos for a user
    @GetMapping("/users/{username}/todos")
    public List<Todo> retrieveTodos(@PathVariable String username) {
        return todoService.findByUsername(username);
    }

    // ✅ Fetch a single todo
    @GetMapping("/users/{username}/todos/{id}")
    public Todo retrieveTodo(@PathVariable String username, @PathVariable int id) {
        return todoService.findById(id);
    }

    // ✅ Delete a todo
    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable int id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,
            @PathVariable int id, @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodo(todo); // ✅ Make sure `updateTodo` returns Todo
        return ResponseEntity.ok(updatedTodo); // ✅ Return updated Todo object
    }


    // ✅ Create a new todo
    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Todo> createTodo(@PathVariable String username, @RequestBody Todo todo) {
        Todo createdTodo = todoService.addTodo(username, todo.getDescription(), todo.getTargetDate(), todo.isDone());
        return ResponseEntity.ok(createdTodo);
    }
}
