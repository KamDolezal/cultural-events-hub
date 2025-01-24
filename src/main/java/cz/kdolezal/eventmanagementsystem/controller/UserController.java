package cz.kdolezal.eventmanagementsystem.controller;

import cz.kdolezal.eventmanagementsystem.api.UserService;
import cz.kdolezal.eventmanagementsystem.api.request.UserAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.UserLoginRequest;
import cz.kdolezal.eventmanagementsystem.dto.UserDTO;
import cz.kdolezal.eventmanagementsystem.security.JwtService;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("#id == authentication.principal.userId or hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userService.get(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-id/{username}")
    public ResponseEntity<Long> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getUserIdByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok().body(userService.getAll());
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody UserAddRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        String token = userService.verify(request);

        // Settings of JWT token - cookies
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", token)
                .httpOnly(true)
                .secure(true) // Requires HTTPS
                .sameSite("None") // Enable sharing between different domains
                .path("/")
                .maxAge(10 * 60)    // The age must be the same as the token expiration
                .build();

        // Adding cookies to the response
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return ResponseEntity.ok().body("Login successful");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getUserStatus(HttpServletRequest request) {
        String token = jwtService.extractTokenFromCookies(request);
        if (token != null && jwtService.isTokenValid(token)) {
            Long userId = jwtService.extractUserId(token); // Getting userId from token
            List<String> roles = jwtService.extractAuthorities(token).stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList(); // Role list extraction

            return ResponseEntity.ok(Map.of(
                    "userId", userId, // userId
                    "roles", roles  // List of roles
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        // Settings of JWT token - cookies
        ResponseCookie jwtCookie = ResponseCookie.from("jwtToken", null)
                .httpOnly(true)
                .secure(true) // Requires HTTPS
                .sameSite("None") // Enable sharing between different domains
                .path("/")
                .maxAge(0)    // The age must be the same as the token expiration
                .build();

        // Adding cookies to the response
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());


        return ResponseEntity.ok("Logged out successfully");
    }
}
