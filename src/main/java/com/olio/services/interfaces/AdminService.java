package com.olio.services.interfaces;

import com.olio.model.User;

import java.util.List;
import java.util.Map;

public interface AdminService {
    List<User> getAllPendingSellers();
    void handleSellerApproval(String username, String action);
    Map<String, Long> getDashboardStatus();
}
