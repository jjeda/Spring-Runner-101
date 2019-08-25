package todoapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import todoapp.web.model.FeatureTogglesProperties;

@RestController
public class FeatureTogglesRestController {
	
	private FeatureTogglesProperties feature;

	
	public FeatureTogglesRestController(FeatureTogglesProperties feature) {
		this.feature = feature;
	}
	
	@GetMapping("/api/feature-toggles")
//	@ModelAttribute("feature")
	public FeatureTogglesProperties featureToggles() {
		return feature;
	}
	

	

}
