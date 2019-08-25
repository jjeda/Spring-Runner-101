package todoapp.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import todoapp.web.model.SiteProperties;

@ControllerAdvice // 모든 컨트롤러에게 적용
public class GlobalControllerAdvice {

	private SiteProperties site;
	
	public GlobalControllerAdvice(SiteProperties site) {
		this.site = site;
	}

	@ModelAttribute("site") 
	public SiteProperties site() {
		return site;
	}
}
