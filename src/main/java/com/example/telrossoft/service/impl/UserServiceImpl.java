package com.example.telrossoft.service.impl;

import com.example.telrossoft.dto.UserContactDto;
import com.example.telrossoft.dto.UserDetailsDto;
import com.example.telrossoft.entity.User;
import com.example.telrossoft.mapper.UserMapper;
import com.example.telrossoft.repository.UserRepository;
import com.example.telrossoft.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserContactDto addUserContact(UserContactDto userContactDto) {
        if (userRepository.existsByEmailIgnoreCase(userContactDto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.toEntity(userContactDto);

        return userMapper.toUserContactDto(userRepository.save(user));
    }

    public UserContactDto getUserContactById(long id) {
        return userMapper.toUserContactDto(findUserById(id));
    }

    public UserContactDto updateUserContact(long id, UserContactDto userContactDto) {
        User user = findUserById(id);

        user.setFirstName(userContactDto.getFirstName());
        user.setLastName(userContactDto.getLastName());
        user.setEmail(userContactDto.getEmail());
        user.setPhone(userContactDto.getPhone());

        return userMapper.toUserContactDto(userRepository.save(user));
    }

    public void deleteUser(long id) {
        userRepository.delete(findUserById(id));
    }

    public UserDetailsDto getUserDetailsById(long id) {
        return userMapper.toUserDetailsDto(findUserById(id));
    }

    public UserDetailsDto updateUserDetails(long id, UserDetailsDto userDetailsDto) {
        User user = findUserById(id);

        user.setFirstName(userDetailsDto.getFirstName());
        user.setLastName(userDetailsDto.getLastName());
        user.setPatronymic(userDetailsDto.getPatronymic());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        user.setBirthday(LocalDate.parse(userDetailsDto.getBirthday(), formatter));

        return userMapper.toUserDetailsDto(userRepository.save(user));
    }

    @SneakyThrows
    public byte[] updateUserAvatar(long id, MultipartFile image){
        if (image == null || image.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = findUserById(id);
        user.setAvatar(image.getBytes());
        return userRepository.save(user).getAvatar();
    }

    public byte[] getUserAvatarById(long id) {
        byte[] ava = findUserById(id).getAvatar();
        if (ava == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ava;
    }

    public String deleteUserAvatarById(long id) {
        User user = findUserById(id);
        if (user.getAvatar() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        user.setAvatar(null);
        userRepository.save(user);
        return "The avatar has been deleted.";
    }

    private User findUserById(long id) {
        logger.info("The private findUserById method was called");

        return userRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
