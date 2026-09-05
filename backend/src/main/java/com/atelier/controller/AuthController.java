package com.atelier.controller;

import com.atelier.dto.auth.LoginRequest;
import com.atelier.dto.auth.LoginResponse;
import com.atelier.security.JwtService;
import com.atelier.security.UtilisateurPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse())
        );
        UtilisateurPrincipal principal = (UtilisateurPrincipal) authentication.getPrincipal();
        String token = jwtService.genererToken(principal);
        LoginResponse response = new LoginResponse(
                token,
                principal.getUsername(),
                principal.getUtilisateur().getRole().name()
        );
        return ResponseEntity.ok(response);
    }
}