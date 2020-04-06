package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.dto.UserDto;
import web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserPage(Authentication authentication, Model model) {
        model.addAttribute("user", new UserDto(userService.getUserByName(authentication.getName())));
        return "userPage";
    }
}
