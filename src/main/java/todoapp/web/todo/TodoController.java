package todoapp.web.todo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import todoapp.web.model.SiteProperties;

@Controller
public class TodoController {
	
	private SiteProperties site;
	
	public TodoController(SiteProperties site) {
		this.site = site;
	}
	
//	@RequestMapping("/todos")
//	public ModelAndView todos() {
//		
////		String view = "classpath:templates" + viewName + ".html";
////		viewResolve 인터페이스 컴퍼넌트가 해주는 역할 따라서 viewName만 return하면
//		
////		SiteProperties model = new SiteProperties();
//		// author, description에 해당하는 모델을 제공 #2-1
//		
////		model.setAuthor("jjeda"); // 이런식으로 설정을 할 수 있지만..
////		1. private Environment env; //를 주입 받아서 application.yml 에 설정한 외부환경변수를 받아올 수 있음
////	    2. @value("${site.author}") /SPEL을 통해서도가능
////	    3. boot가 제공하는 기능은 @ConfigurationProperties("site") 애노태이션을 통해 구현 
//		
//		
//		return new ModelAndView("todos", "site", site);
//	}
	
	@RequestMapping("/todos")
	public String todos(Model model) {
		model.addAttribute("site",site);
		return "todos";
	}
	
}
