package todoapp.web.todo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
@RequestMapping("api/todos")
public class TodoRestController {
	
	private TodoFinder finder;
	
	public TodoRestController(TodoFinder finder) {
		this.finder = finder;
	}
	
	@GetMapping
	public List<Todo> todos() {
		return finder.getAll();
	}

}
