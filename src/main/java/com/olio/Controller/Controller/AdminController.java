package com.olio.Controller.Controller;

import com.olio.Model.Model.User;
import com.olio.Services.Interface.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @GetMapping("/dashboard-status")
    public ResponseEntity<Map<String, Long>> getDashboardStatus(){
        return ResponseEntity.ok(adminService.getDashboardStatus());
    }

    @GetMapping("/seller-requests")
    public ResponseEntity<List<User>>getAllPendingSellers(){
        return ResponseEntity.ok(adminService.getAllPendingSellers());
    }

    @PostMapping("/seller-requests/{username}/{action}")
    public ResponseEntity<String> handleSellerApproval(
            @PathVariable String username,
            @PathVariable String action) {

        if (!action.equalsIgnoreCase("approve") && !action.equalsIgnoreCase("reject")) {
            return ResponseEntity.badRequest().body("Invalid action. Use 'approve' or 'reject'.");
        }

        adminService.handleSellerApproval(username, action.toLowerCase());

        String message = action.equalsIgnoreCase("approve")
                ? "Seller approved successfully."
                : "Seller rejected successfully.";

        return ResponseEntity.ok(message);
    }
}
