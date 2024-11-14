package com.cursos.sistema_cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
public class Ac2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ac2Application.class, args);
	}
	
	@RequestMapping ("/")
	@ResponseBody
	String home()
	{
		return "Hello World";
}


}
