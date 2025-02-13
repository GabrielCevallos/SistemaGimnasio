package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.TokenResponseDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody UsuarioDTO usuario) {
        return ResponseEntity.ok(authService.register(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody UsuarioDTO usuario) {
        return ResponseEntity.ok(authService.login(usuario))    ;
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@RequestHeader String Authorization) {
        return ResponseEntity.ok(authService.refresh(Authorization));
    }

    @GetMapping("/logout")
    public void logout() {

    }

}
