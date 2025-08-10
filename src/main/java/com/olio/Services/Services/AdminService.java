package com.olio.Services.Services;

import com.olio.Model.Model.User;
import com.olio.Repository.Repository.UserRepository;
import com.olio.Services.Interface.IAdminService;
import com.olio.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllPendingSellers(){
        return userRepository.findAllByRole(Role.PENDING_SELLER);
    }

    @Override
    public void approveSeller(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!user.getRole().equals(Role.PENDING_SELLER))
                throw new IllegalStateException("User has not requested seller access.");

        user.setRole(Role.SELLER);
        userRepository.save(user);
    }
    @Override
    public void rejectSeller(String username){
        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new RuntimeException("Invalid username"));

        if(!user.getRole().equals(Role.PENDING_SELLER))
                throw new IllegalStateException("User has not requested seller access.");

        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
