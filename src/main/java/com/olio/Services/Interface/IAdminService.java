package com.olio.Services.Interface;

import com.olio.Model.Model.User;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    List<User> getAllPendingSellers();
    void handleSellerApproval(String username, String action);
    Map<String, Long> getDashboardStatus();
}
