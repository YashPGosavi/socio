package com.socio.socio.service;

import com.socio.socio.model.User;
import com.socio.socio.repository.UserRepository;
import com.socio.socio.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User login(String email, String password) {
      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("User not found"));

      if(!passwordEncoder.matches(password, user.getPassword())){
          throw new RuntimeException("Invalid credentials");
      }
      return user;
    }

    public String loginAndGetToken(String email, String password) {
        User user = login(email, password);
        return jwtUtil.generateToken(user.getId(), user.getEmail());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
