package com.ainsiel.twitterclonebackend.security.auth;

import com.ainsiel.twitterclonebackend.features.profiles.ProfileEntity;
import com.ainsiel.twitterclonebackend.features.profiles.ProfileService;
import com.ainsiel.twitterclonebackend.features.users.IUserRepository;
import com.ainsiel.twitterclonebackend.features.users.Role;
import com.ainsiel.twitterclonebackend.features.users.UserEntity;
import com.ainsiel.twitterclonebackend.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final ProfileService profileService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        ProfileEntity profile = profileService.createProfile(request.getUsername());

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode( request.getPassword()))
                .role(Role.USER)
                .profile(profile)
                .build();


        profile.setUser(user);

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }
}
