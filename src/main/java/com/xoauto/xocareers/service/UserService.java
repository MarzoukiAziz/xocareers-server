package com.xoauto.xocareers.service;

import com.xoauto.xocareers.model.Authority;
import com.xoauto.xocareers.model.User;
import com.xoauto.xocareers.repository.AuthorityRepository;
import com.xoauto.xocareers.repository.UserRepository;
import com.xoauto.xocareers.service.interfaces.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public List<User> selectAll() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User findUserByEmail(String username) {
        return userRepository.findByEmailIgnoreCase(username);
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Authority authority = authorityRepository.findByAuthority("ROLE_USER");
        user.setAuthorities(Set.of(authority));
        userRepository.save(user);
    }

    @Transactional
    public void saveAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        Authority authority = authorityRepository.findByAuthority("ROLE_ADMIN");
        user.setAuthorities(Set.of(authority));
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(long id, User user) {
        userRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
