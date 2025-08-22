package demo.parkingsystem.service;

import demo.parkingsystem.model.security.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User save(User user);
    User create(User user);
    User getByUsername(String username);
    User getCurrentUser();
    UserDetailsService userDetailsService();
}
