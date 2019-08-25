package todoapp.web.user;

import java.net.URI;
import java.util.Objects;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

@RestController
public class UserRestController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	private ProfilePictureChanger profilePictureChanger;
	private ProfilePictureStorage profilePictureStorage;
	private UserSessionRepository userSessionRepository;
	
	public UserRestController(ProfilePictureChanger profilePictureChanger,
			ProfilePictureStorage profilePictureStorage,
			UserSessionRepository userSessionRepository) {
		super();
		this.profilePictureChanger = profilePictureChanger;
		this.profilePictureStorage = profilePictureStorage;
		this.userSessionRepository = userSessionRepository;
	}

//	private UserSessionRepository sessionRepository;
//	
//	public UserRestController(UserSessionRepository sessionRepository) {
//		super();
//		this.sessionRepository = sessionRepository;
//	}
	
	//그냥 주면안되나?

	public UserRestController(ProfilePictureStorage profilePictureStorage) {
		super();
		this.profilePictureStorage = profilePictureStorage;
	}

	@RolesAllowed(UserSession.ROLE_USER)
	@GetMapping("/api/user/profile")
	public ResponseEntity<UserProfile> userProfile(UserSession session) {
//		UserSession session = sessionRepository.get();
		
		
		
		if(Objects.isNull(session)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
				
		return ResponseEntity.ok(new UserProfile(session.getUser()));
	}
	
	@RolesAllowed(UserSession.ROLE_USER)
	@PostMapping("/api/user/profile-picture")
	public UserProfile updateProfilePicture(MultipartFile profilePicture, UserSession session) {
		
		//프로필 이미지를 갱신하는 로직이 필요합니다.
		log.info("profilePicture {}:",profilePicture);
		
		URI profilePictureUri = profilePictureStorage.save(profilePicture.getResource());
		User updatedUser = profilePictureChanger.change(session.getName(), new ProfilePicture(profilePictureUri));
		
		userSessionRepository.set(new UserSession(updatedUser));
		
		return new UserProfile(updatedUser); 
	}
}
