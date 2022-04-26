package com.fintech.restcontroller;

import com.fintech.jwt.JwtUtil;
import com.fintech.openapi.api.JwtSecurityManagementApi;
import com.fintech.openapi.model.JwtRequestDTO;
import com.fintech.openapi.model.JwtResponseDTO;
import com.fintech.service.impl.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtSecurityManagementApiRestController implements JwtSecurityManagementApi {

    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<JwtResponseDTO> generateToken(JwtRequestDTO jwtRequestDTO) throws Exception {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequestDTO.getUserName(), jwtRequestDTO.getPassword()));
        } catch (UsernameNotFoundException ex) {
            ex.printStackTrace();
            throw new Exception("Bad Credentials");
        }
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequestDTO.getUserName());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDTO().token(token));
    }
}