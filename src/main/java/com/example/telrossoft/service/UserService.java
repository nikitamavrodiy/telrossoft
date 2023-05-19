package com.example.telrossoft.service;

import com.example.telrossoft.dto.UserContactDto;
import com.example.telrossoft.dto.UserDetailsDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    /**
     * Creates a new user and filling in his contact details
     * if a user with the same email does not exist yet.
     *
     * @param userContactDto New user contact details
     * @return UserContactDto
     */
    UserContactDto addUserContact(UserContactDto userContactDto);

    /**
     * Find user contacts by ID
     *
     * @param id ID user
     */
    UserContactDto getUserContactById(long id);

    /**
     * Finds a user by ID and makes changes regarding contact details.
     *
     * @param id User id
     * @param userContactDto New data
     * @return UserContactDto
     */
    UserContactDto updateUserContact(long id, UserContactDto userContactDto);

    /**
     * Finds the user by ID and deletes it.
     *
     * @param id User id
     */
    void deleteUser(long id);

    /**
     * Finds the user by ID and returns more detailed user data.
     *
     * @param id User id
     * @return UserDetailsDto
     */
    UserDetailsDto getUserDetailsById(long id);

    /**
     * Finds a user by ID and makes changes to a part of more detailed user data.
     *
     * @param id User id
     * @return UserDetailsDto
     */
    UserDetailsDto updateUserDetails(long id, UserDetailsDto userDetailsDto);

    /**
     * Finds a user by ID and updates the avatar.
     *
     * @param id User id
     * @param image Avatar for user
     * @return byte[] User's avatar
     */
    byte[] updateUserAvatar(long id, MultipartFile image);

    /**
     * Finds the user by ID and returns it.
     *
     * @param id User id
     * @return byte[] User's avatar
     */
    byte[] getUserAvatarById(long id);

    /**
     * Finds the user by ID and deletes it.
     *
     * @param id User id
     * @return String A message with a deleted ID
     */
    String deleteUserAvatarById(long id);
}
