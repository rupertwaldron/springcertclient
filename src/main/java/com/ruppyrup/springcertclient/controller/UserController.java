package com.ruppyrup.springcertclient.controller;

import com.jayway.jsonpath.JsonPath;
import com.ruppyrup.springcertclient.dto.UserDTO;
import com.ruppyrup.springcertclient.jwt.JWTStore;
import com.ruppyrup.springcertclient.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class UserController {

    private JWTStore jwtStore;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public ModelAndView homeView() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("jwt", jwtStore.getToken());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView loginView() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("userDTO", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/login")
    public String authenticateUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        String token;
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            ResponseEntity<String> authenticateResponse = userService.authenticateUser(userDTO);
            if (!authenticateResponse.getStatusCode().is2xxSuccessful()) {
                return "error";
            }
            token = JsonPath.parse(authenticateResponse.getBody()).read("$.token");
            jwtStore = new JWTStore(token);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
        return "redirect:home";
    }

    @GetMapping("/register")
    public ModelAndView registerView() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("userDTO", new UserDTO());
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            ResponseEntity<String> authenticateResponse = userService.registerUser(userDTO);
            if (!authenticateResponse.getStatusCode().is2xxSuccessful()) {
                return "error";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
        return "redirect:login";
    }

}
