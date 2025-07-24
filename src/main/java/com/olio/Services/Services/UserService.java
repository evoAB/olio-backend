package com.olio.Services.Services;

import com.olio.Dto.Request.LoginRequest;
import com.olio.Dto.Request.UserRequest;
import com.olio.Dto.Response.LoginResponse;
import com.olio.Dto.Response.UserResponse;
import com.olio.Model.Model.User;
import com.olio.Repository.Repository.UserRepository;
import com.olio.Services.Interface.IUserService;
import com.olio.Services.Transformers.UserTransformer;
import com.olio.enums.Role;
import com.olio.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private JwtUtil jwtUtil;

    public UserResponse register(UserRequest request) {
        if(userRepository.existsByEmail((request.getEmail()))) {
            throw new RuntimeException("Email already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = UserTransformer.toEntity(request, encodedPassword);
        User saved = userRepository.save(user);
        return UserTransformer.toDto(saved);
    }

//    public UserResponse login(LoginRequest request) {
//        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
//
//        if(userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPassword())) {
//            throw new RuntimeException("Invalid email or password");
//        }
//
//        return UserTransformer.toDto(userOpt.get());
//    }

    public LoginResponse login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

        return new LoginResponse(user.getName(), user.getEmail(), user.getRole().toString(), token);
    }
    public String loginAndGetToken(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().toString());
    }

    @Override
    public void requestToBecomeSeller(String userName){
        User user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if(user.getRole() == Role.SELLER) throw new IllegalStateException("Already a Seller");
        if(user.getRole() == Role.PENDING_SELLER) throw new IllegalStateException("Seller request already submitted");

        user.setRole(Role.PENDING_SELLER);
        userRepository.save(user);
    }
}
