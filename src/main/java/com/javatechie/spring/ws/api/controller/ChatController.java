package com.javatechie.spring.ws.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.javatechie.spring.ws.api.model.ChatMessage;
import com.javatechie.spring.ws.api.model.Usuario;
import com.javatechie.spring.ws.api.repository.UsuarioRepository;

@Controller
public class ChatController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value="/teste", method = RequestMethod.GET)
	public String teste() {
		return "index";
	}
	
	
	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}

	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		return chatMessage;
		
	}
	@RequestMapping(value="/cadastrar", method = RequestMethod.GET)
	public String cadastrar() {
		return "teste";
	}
	
	@RequestMapping(value="/cadastrar", method = RequestMethod.POST)
	public String cadastrar(Usuario usuario) {
		usuarioRepository.save(usuario);
		return "redirect:/";
	}

	//Mapeamento dos dados do Usuario
	
	@RequestMapping("/usuarios")
	public ModelAndView listaEventos(){
		ModelAndView mv = new ModelAndView("index");
		Iterable<Usuario> usuarios = usuarioRepository.findAll();
		mv.addObject("usuarios", usuarios);
		return mv;
	}

	
}	
