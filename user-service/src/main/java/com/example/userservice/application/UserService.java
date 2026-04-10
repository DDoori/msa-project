package com.example.userservice.application;

import com.example.userservice.domain.User;
import com.example.userservice.domain.UserRole;
import com.example.userservice.dto.LoginRequest;
import com.example.userservice.dto.SignUpRequest;
import com.example.userservice.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    public void signUp(SignUpRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        userRepository.save(new User(
                request.getEmail(),
                request.getPassword(),
                UserRole.valueOf(request.getRole())
        ));
    }

    @Transactional(readOnly = true)
    public void login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
