package web.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dto.UserDto;
import web.model.User;
import web.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAllUsers(Authentication authentication, Model model) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userService.allUsers()) {
            userDtoList.add(new UserDto(user));
        }
        model.addAttribute("user", new UserDto(userService.getUserByName(authentication.getName())));
        model.addAttribute("userList", userDtoList);
        return "table";
    }

    @PostMapping("/userAdd")
    public String addUser(UserDto userDto) {
        userService.addUser(userDto);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("user") UserDto userDto) {
        userService.updateUser(userDto);
        return "redirect:/admin";
    }
}
