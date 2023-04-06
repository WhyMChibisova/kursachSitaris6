package com.example.kursach.services;

import com.example.kursach.models.Course;
import com.example.kursach.models.Role;
import com.example.kursach.models.User;
import com.example.kursach.repositories.RoleRepository;
import com.example.kursach.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    public List<User> allUsers() {
        return (List<User>) userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByLogin(user.getLogin());
        if (userFromDB != null) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            User user = findUserById(userId);
            for (Role role : user.getRoles()) {
                if (role.getName().equals("ADMIN")) {
                    return false;
                } else {
                    userRepository.deleteById(userId);
                    return true;
                }
            }
        }
        return false;
    }

    public User getAuthorized() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void editUser(int id, String name, String surname, int age, String login) {
        User user = findUserById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setAge(age);
        user.setLogin(login);
        userRepository.save(user);
    }
}
