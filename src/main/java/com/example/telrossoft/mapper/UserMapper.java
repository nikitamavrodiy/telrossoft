package com.example.telrossoft.mapper;

import com.example.telrossoft.dto.UserContactDto;
import com.example.telrossoft.dto.UserDetailsDto;
import com.example.telrossoft.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserContactDto toUserContactDto(User entity);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserContactDto userContactDto);

    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "birthdayMapping")
    UserDetailsDto toUserDetailsDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "birthday", source = "birthday", qualifiedByName = "birthdayMappingL")
    User toEntity(UserDetailsDto userDetailsDto);

    @Named("birthdayMapping")
    default String birthdayMapping(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.toString();
    }

    @Named("birthdayMappingL")
    default LocalDate birthdayMappingL(String s) {
        if (s == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(s, formatter);
    }
}
