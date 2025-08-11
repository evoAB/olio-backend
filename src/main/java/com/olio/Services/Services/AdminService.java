package com.olio.Services.Services;

import com.olio.Model.Model.Order;
import com.olio.Model.Model.User;
import com.olio.Repository.Repository.OrderRepository;
import com.olio.Repository.Repository.UserRepository;
import com.olio.Services.Interface.IAdminService;
import com.olio.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<User> getAllPendingSellers(){
        return userRepository.findAllByRole(Role.PENDING_SELLER);
    }

    @Override
    public Map<String, Long> getDashboardStatus() {
        List<User> allUsers = userRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        List<User> users = new ArrayList<>();
        List<User> pendingSellerRequests = new ArrayList<>();
        List<User> sellers = new ArrayList<>();

        for (User user : allUsers) {
            if (user.getRole() == Role.USER) {
                users.add(user);
            } else if (user.getRole() == Role.PENDING_SELLER) {
                pendingSellerRequests.add(user);
            } else if (user.getRole() == Role.SELLER) {
                sellers.add(user);
            }
        }

        Map<String, Long> statusMap = new HashMap<>();
        statusMap.put("totalUsers", (long) users.size());
        statusMap.put("pendingSellerRequests", (long) pendingSellerRequests.size());
        statusMap.put("totalSellers", (long) sellers.size());
        statusMap.put("totalOrders", (long) orders.size());

        return statusMap;
    }


    @Override
    public void handleSellerApproval(String username, String action){
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if(!user.getRole().equals(Role.PENDING_SELLER))
                throw new IllegalStateException("User has not requested seller access.");

        switch (action.toLowerCase()) {
            case "approve":
                user.setRole(Role.SELLER);
                break;
            case "reject":
                user.setRole(Role.USER);
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action + ". Must be 'approve' or 'reject'.");
        }

        userRepository.save(user);
    }
}
