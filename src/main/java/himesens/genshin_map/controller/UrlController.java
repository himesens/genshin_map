package himesens.genshin_map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UrlController {
	
	@GetMapping({"/","/index"})
	public String diaryIndex(){
		return "index";
	}
	
}
