package com.oracle.www.sistemaempleadosappmonolitica.controllers;

import com.oracle.www.sistemaempleadosappmonolitica.entities.UserLogin;
import com.oracle.www.sistemaempleadosappmonolitica.security.repository.UserLoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthApiController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserLoginRepository userLoginRepository;

    public AuthApiController(BCryptPasswordEncoder passwordEncoder, UserLoginRepository userLoginRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userLoginRepository = userLoginRepository;
    }

    @GetMapping({"/login"})
    public String loginView(){
        return "login";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model){

        UserLogin usuario = userLoginRepository.findByUsername(username);

        if((username == null) || (!passwordEncoder.matches(password, usuario.getPassword()))) {
            model.addAttribute("error", "usuario o contraseña incorrectas");
            return "login";
        }

        session.setAttribute("usuario", usuario);

        String redirect = (String) session.getAttribute("redirectAfterLogin");
        if(redirect != null){
            session.removeAttribute("redirectAfterLogin");
            return "redirect"+redirect;
        }

        return "redirect:/employee/inicio";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect/login";
    }
}
