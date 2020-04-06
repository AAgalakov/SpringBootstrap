package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.UserDto;
import web.model.User;
import web.repository.UserRepo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean addUser(UserDto userDto) {
        if (!isNameUnique(userDto)) {
            return false;
        }
        User user = fromForm(userDto);
        userRepo.save(user);
        return true;
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        if (getUserById(userDto.getId()).getName().equals(userDto.getName()) || isNameUnique(userDto)) {
            User user = fromForm(userDto);
            if (user.getPassword().isEmpty()) {
                user.setPassword(getUserById(user.getId()).getPassword());
            }
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public User getUserByName(String name) throws IllegalStateException {
        return userRepo.findByName(name).orElseThrow(() -> new IllegalStateException("User not find by name"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return getUserByName(s);
    }

    private void setRoles(User user, UserDto userDto) {
        user.setRoles(Arrays.stream(userDto.getRoles())
                .map(roleService::getRoleByName)
                .collect(Collectors.toSet()));
    }

    private boolean isNameUnique(UserDto userDto) {
        return !userRepo.findByName(userDto.getName()).isPresent();
    }

    private User fromForm(UserDto userDto) {
        User user = new User(userDto);
        setRoles(user, userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }
}
