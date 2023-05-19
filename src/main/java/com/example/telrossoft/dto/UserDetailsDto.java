package com.example.telrossoft.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.example.telrossoft.constant.Regexp.BIRTHDAY_REGEXP;

@Data
public class UserDetailsDto {
    private long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String patronymic;
    @NotBlank
    @Pattern(regexp = BIRTHDAY_REGEXP)
    private String birthday;
}
