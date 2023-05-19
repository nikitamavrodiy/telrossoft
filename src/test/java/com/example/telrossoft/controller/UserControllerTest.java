package com.example.telrossoft.controller;

import com.example.telrossoft.dto.UserContactDto;
import com.example.telrossoft.dto.UserDetailsDto;
import com.example.telrossoft.entity.User;
import com.example.telrossoft.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserController userController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
        Assertions.assertThat(userController).isNotNull();
    }

    @Test
    void addUserContact() throws Exception {
        userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        UserContactDto userDto = new UserContactDto();

        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");

        mockMvc.perform(post("/user/contact").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.email").value("a@mail.ru"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.phone").value("+79991254698"));
    }

    @Test
    void getUserContactById() throws Exception {
        User user = userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        mockMvc.perform(get("/user/1/contact").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }

    @Test
    void updateUserContact() throws Exception {
        userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        UserContactDto userDto = new UserContactDto();

        userDto.setEmail("a@mail.ru");
        userDto.setFirstName("Ivan");
        userDto.setLastName("Ivanov");
        userDto.setPhone("+79991254698");

        mockMvc.perform(patch("/user/1/contact").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("a@mail.ru"))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.phone").value("+79991254698"));
    }

    @Test
    void getUserDetailsById() throws Exception {
        User user = userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        mockMvc.perform(get("/user/1/details").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.patronymic").value(user.getPatronymic()))
                .andExpect(jsonPath("$.birthday").value(user.getBirthday().toString()));
    }

    @Test
    void updateUserDetails() throws Exception {
        userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        UserDetailsDto dto = new UserDetailsDto();

        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setPatronymic("Ivanovich");
        dto.setBirthday("1951-10-10");

        mockMvc.perform(patch("/user/1/details").with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.patronymic").value("Ivanovich"))
                .andExpect(jsonPath("$.birthday").value("1951-10-10"));
    }

    @Test
    void deleteUser() throws Exception {
        userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", null));

        mockMvc.perform(delete("/user/1").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete("/user/1").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateUserAvatar() throws Exception {
        mockMvc.perform(patch("/user/1/avatar").with(httpBasic("admin", "admin"))
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .content((byte[]) null)
                        .accept(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserAvatar() throws Exception {
        User user = userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", new byte[3]));

        mockMvc.perform(get("/user/1/avatar").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(user.getAvatar()));
    }

    @Test
    void deleteUserAvatar() throws Exception {
        userRepository.save(new User(1L, "Nikita", "Nikitov", "Nikitovich",
                LocalDate.parse("1998-12-12", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                "mna1212@mail.ru", "+78123001212", new byte[3]));

        mockMvc.perform(delete("/user/1/avatar").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("The avatar has been deleted."));

        mockMvc.perform(delete("/user/1/avatar").with(httpBasic("admin", "admin")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
