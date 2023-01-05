package com.example.stareshop.services;

import com.example.stareshop.model.User;
import com.example.stareshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public UserDetails loadUser(String username, String password) throws UsernameNotFoundException {
        User user = userService.login(username, password)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such email"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword()).authorities(user.getRole()).build();
    }

    public void createUser(UserDetails user) {
        userService.addOrUpdateUser((User) user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
