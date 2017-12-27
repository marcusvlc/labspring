package br.com.lab2spring.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TelasController {
	
	@RequestMapping("/login")
	public String irParaLogin() {
		return "login";
	}
	
	@RequestMapping("/index")
	public String irParaIndex() {
		return "index";
	}
	
	@RequestMapping("/registrar")
	public String irParaRegistro() {
		return "registrar";
	}
	

}
