package com.olio.Controller.Controller;

import com.olio.Dto.Request.LoginRequest;
import com.olio.Dto.Request.UserRequest;
import com.olio.Dto.Response.LoginResponse;
import com.olio.Dto.Response.UserResponse;
import com.olio.Services.Interface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest request){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/become-seller")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> becomeSeller(@AuthenticationPrincipal UserDetails userDetails){
        userService.requestToBecomeSeller(userDetails.getUsername());
        return ResponseEntity.ok("Seller request submitted successfully");
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> users(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<String>> user(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/sellers")
    public ResponseEntity<List<String>> seller(){
        return ResponseEntity.ok(userService.getSellers());
    }
    @GetMapping("/pending-sellers")
    public ResponseEntity<List<String>> pendingSeller(){
        return ResponseEntity.ok(userService.getPendingSellers());
    }
    @GetMapping("/admins")
    public ResponseEntity<List<String>> admin(){
        return ResponseEntity.ok(userService.getAdmins());
    }
}
