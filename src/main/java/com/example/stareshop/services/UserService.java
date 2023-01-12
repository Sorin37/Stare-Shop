package com.example.stareshop.services;

import com.example.stareshop.model.User;
import com.example.stareshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passEnc;

    public List<User> getAllUsers(){
        return new ArrayList<>(userRepository.findAll());
    }

    public void addOrUpdateUser(User user){
        user.setPassword(passEnc.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> login(String email, String password){
        Optional<User> found = userRepository.findByEmail(email);

        if(found.isEmpty())
            return Optional.empty();

        if(BCrypt.checkpw(password, found.get().getPassword())){
            return found;
        }else{
            return Optional.empty();
        }
    }

    public boolean deleteUser(Long id){
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
