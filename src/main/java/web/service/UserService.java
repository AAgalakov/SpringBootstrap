package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import web.dto.UserDto;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> allUsers();

    boolean addUser(UserDto userDto);

    boolean updateUser(UserDto userDto);

    void deleteUser(Long id);

    User getUserById(Long id);

    User getUserByName(String name);

    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
