package com.example.telrossoft.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.telrossoft.constant.Regexp.EMAIL_REGEXP;
import static com.example.telrossoft.constant.Regexp.PHONE_REGEXP;

@Data
public class UserContactDto {
    private long id;
    @Email(regexp = EMAIL_REGEXP)
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Pattern(regexp = PHONE_REGEXP)
    private String phone;
}
