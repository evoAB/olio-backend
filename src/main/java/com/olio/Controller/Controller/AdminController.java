package com.olio.Controller.Controller;

import com.olio.Model.Model.User;
import com.olio.Services.Interface.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @GetMapping("/seller-requests")
    public ResponseEntity<List<User>>getAllPendingSellers(){
        return ResponseEntity.ok(adminService.getAllPendingSellers());
    }

    @PostMapping("/approve-seller/{username}")
    public ResponseEntity<String> approveSeller(@PathVariable String userName){
        adminService.approveSeller(userName);
        return ResponseEntity.ok("Seller approve successfully.");
    }

    @PostMapping("/reject-seller/{username}")
    public ResponseEntity<String> rejectSeller(@PathVariable String userName){
        adminService.rejectSeller(userName);
        return ResponseEntity.ok("Seller approve successfully.");
    }
}
