package com.mrminnthitoo.notes_backend.controller.api;

import com.mrminnthitoo.notes_backend.API.APIResponse;
import com.mrminnthitoo.notes_backend.API.RESTResponser;
import com.mrminnthitoo.notes_backend.exceptions.ValidationException;
import com.mrminnthitoo.notes_backend.models.dtos.LoginDto;
import com.mrminnthitoo.notes_backend.models.dtos.RegisterDto;
import com.mrminnthitoo.notes_backend.models.dtos.UserDto;
import com.mrminnthitoo.notes_backend.services.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private APIResponse apiResponse;

    @PostMapping("/register")
    public ResponseEntity<RESTResponser> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) throws Exception {

        if (!bindingResult.hasErrors()){

            UserDto registeredUser = this.authService.register(registerDto);
            return this.apiResponse.successResponse(
                    HttpStatus.CREATED,
                    "registered successfully.",
                    registeredUser
            );

        }else {
            throw new ValidationException("validation fail.", bindingResult.getFieldErrors());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<RESTResponser> login(@Valid @RequestBody LoginDto loginDto){
        String token = this.authService.login(loginDto);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        return this.apiResponse.successResponse(
                HttpStatus.OK,
                "logged in successfully",
                tokenMap
        );
    }

}
