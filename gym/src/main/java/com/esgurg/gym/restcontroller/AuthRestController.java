package com.esgurg.gym.restcontroller;

import com.esgurg.gym.dto.TokenResponseDTO;
import com.esgurg.gym.dto.UsuarioDTO;
import com.esgurg.gym.service.AuthService;
import com.esgurg.gym.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



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

    @GetMapping("/user-authenticated")
    public ResponseEntity<Map<String, Object>> userAuthenticated(@RequestHeader String Authorization) {
        return new ResponseBuilder()
            .responseWithOperation(
                () -> authService.userAuthenticated(Authorization),
                 "Usuario autenticado",
                  HttpStatus.OK,
                  "Usuario no autenticado",
                    HttpStatus.UNAUTHORIZED
            );
    }
    
    

}
