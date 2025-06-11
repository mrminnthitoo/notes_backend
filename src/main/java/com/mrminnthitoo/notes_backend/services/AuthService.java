package com.mrminnthitoo.notes_backend.services;

import com.mrminnthitoo.notes_backend.models.dtos.LoginDto;
import com.mrminnthitoo.notes_backend.models.dtos.RegisterDto;
import com.mrminnthitoo.notes_backend.models.dtos.UserDto;

public interface AuthService {

    public UserDto register(RegisterDto userDto) throws Exception;
    public String login(LoginDto userDto);

}
