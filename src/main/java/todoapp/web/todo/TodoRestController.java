package todoapp.web.todo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.core.todos.application.TodoEditor;
import todoapp.core.todos.application.TodoFinder;
import todoapp.core.todos.domain.Todo;

@RestController
@RequestMapping("api/todos")
public class TodoRestController {
	
	private final Logger log = LoggerFactory.getLogger(TodoRestController.class);
	
	private TodoFinder finder;
	
	private TodoEditor editor;
	
	public TodoRestController(TodoFinder finder, TodoEditor editor) {
		this.finder = finder;
		this.editor = editor;
	}
	
	@GetMapping
	public List<Todo> todos() {
		return finder.getAll();
	}
	
	@PostMapping
	public void create(@RequestBody TodoWriteCommand command) {
		log.debug("command.title: {}",command.getTitle());
		editor.create(command.getTitle());
	}
	
	@PutMapping("/{id}")
	public void update(@PathVariable Long id, @RequestBody TodoWriteCommand command ) {
		log.debug("command.title: {}, command.completed: {}",command.getTitle(),command.getCompleted());
		editor.update(id, command.getTitle(), command.getCompleted());
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		log.debug("********** delete success **********");
		editor.delete(id);
	}
	
	public static class TodoWriteCommand {
		
		private String title;
		
		private Boolean completed;
		

		public Boolean getCompleted() {
			return completed;
		}

		public void setCompleted(Boolean completed) {
			this.completed = completed;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
		
	}

}
