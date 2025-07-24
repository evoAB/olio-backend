package com.olio.Services.Interface;

import com.olio.Model.Model.User;

import java.util.List;

public interface IAdminService {
    List<User> getAllPendingSellers();
    void approveSeller(String username);
    void rejectSeller(String username);
}
