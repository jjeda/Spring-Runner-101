package todoapp.web.user;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

@Controller
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class); 

	private UserPasswordVerifier verifier;
	private UserJoinder joinder;
	private UserSessionRepository sessionRepository;
	
	
	public LoginController(UserPasswordVerifier verifier, UserJoinder joinder, UserSessionRepository sessionRepository) {
		super();
		this.verifier = verifier;
		this.joinder = joinder;
		this.sessionRepository = sessionRepository;
	}

	@GetMapping("/login")
	public void loginForm() {
		
	}
	
	@PostMapping("/login")
	public String loginProcess(@Valid LoginCommand loginCommand, BindingResult bindingResult, Model model) {
		log.info("username: {}, password: {}",loginCommand.getUsername(),loginCommand.getPassword());
		
//		if (bindingResult.hasErrors()) {
//			model.addAttribute("bindingResult", bindingResult);
//			model.addAttribute("message","입력값이 올바르지 않습니다.");
//			return "login";
//		}
		
		
		// 사용자가 입력한 아이디와 비밀번호로 사용자 검증
		User user;
		 try {
			 user = verifier.verify(loginCommand.getUsername(), loginCommand.getPassword());
			 
		 } catch (UserPasswordNotMatchedException error) {
			 model.addAttribute("message",error.getMessage());
			 return "login";
		 } catch (UserEntityNotFoundException error) {
		// 등록된 사용자가 없으면, 신규 사용자로 가입을 시켜줌
			 user = joinder.join(loginCommand.getUsername(), loginCommand.getPassword());
		 }
		 log.info("current user: {}", user);
		 sessionRepository.set(new UserSession(user));
		 
		return "redirect:/todos"; 
	}
	
	@ExceptionHandler(BindException.class)
	public String handleBindException(BindException error, Model model) {
		model.addAttribute("bindingResult", error.getBindingResult());
		model.addAttribute("message","입력값이 올바르지 않습니다.");
		return "login";
	}
	
	@RequestMapping("/logout")
	public View logout() {
		sessionRepository.clear();
		return new RedirectView("/todos");
		//return "redirect:/todos" 와 결과적으로 동일
	}
	
	
	
	
	
	
	
	
	
	public static class LoginCommand {
		
		@Size(min=4, max =20)
		private String username;
		private String password;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		
	}
	
}
