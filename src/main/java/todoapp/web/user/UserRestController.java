package todoapp.web.user;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {
	
//	private UserSessionRepository sessionRepository;
//	
//	public UserRestController(UserSessionRepository sessionRepository) {
//		super();
//		this.sessionRepository = sessionRepository;
//	}
	
	//그냥 주면안되나?

	@RolesAllowed(UserSession.ROLE_USER)
	@GetMapping("/api/user/profile")
	public ResponseEntity<UserProfile> userProfile(UserSession session) {
//		UserSession session = sessionRepository.get();
		
		
		
		if(Objects.isNull(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
				
		return ResponseEntity.ok(new UserProfile(session.getUser()));
	}
}
