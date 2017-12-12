package br.com.fabricadeprogramador.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String irParaLogin() {
		return "login";
	}
	
	@RequestMapping("/index")
	public String irParaIndex() {
		return "index";
	}
	

}
