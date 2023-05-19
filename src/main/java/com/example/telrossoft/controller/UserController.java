package com.example.telrossoft.controller;

import com.example.telrossoft.dto.UserContactDto;
import com.example.telrossoft.dto.UserDetailsDto;
import com.example.telrossoft.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/contact")
    public ResponseEntity<UserContactDto> addUserContact(@Valid @RequestBody UserContactDto userContactDto) {
        return ResponseEntity.ok(userServiceImpl.addUserContact(userContactDto));

    }

    @GetMapping("/{id}/contact")
    public ResponseEntity<UserContactDto> getUserContactById(@PathVariable("id") long id) {
        return ResponseEntity.ok(userServiceImpl.getUserContactById(id));
    }

    @PatchMapping("/{id}/contact")
    public ResponseEntity<UserContactDto> updateUserContact(@PathVariable("id") long id,
                                                            @Valid @RequestBody UserContactDto userContactDto) {
        return ResponseEntity.ok(userServiceImpl.updateUserContact(id, userContactDto));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<UserDetailsDto> getUserDetailsById(@PathVariable("id") long id) {
        return ResponseEntity.ok(userServiceImpl.getUserDetailsById(id));
    }

    @PatchMapping("/{id}/details")
    public ResponseEntity<UserDetailsDto> updateUserDetails(@PathVariable("id") long id,
                                                            @Valid @RequestBody UserDetailsDto userDetailsDto) {
        return ResponseEntity.ok(userServiceImpl.updateUserDetails(id, userDetailsDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.ok(String.format("The user with ID %s has been deleted.", id));
    }

    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateUserAvatar(@PathVariable("id") long id,
                                                   @NotNull @RequestBody MultipartFile avatar) {
        return ResponseEntity.ok().body(userServiceImpl.updateUserAvatar(id, avatar));
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getUserAvatar(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userServiceImpl.getUserAvatarById(id));
    }

    @DeleteMapping("/{id}/avatar")
    public ResponseEntity<String> deleteUserAvatar(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(userServiceImpl.deleteUserAvatarById(id));
    }
}
