package by.clevertec.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTest {
    private final UserMapper userMapper = new UserMapper();
    private final UserMapperMapStruct userMapperMapStruct = Mappers.getMapper(UserMapperMapStruct.class);

    @Test
    public void testConvertToDto() {
        // Given
        User user = new User();
        user.setId(1);
        user.setName("Test");
        user.setEmail("test@example.com");
        user.setPhoneNumber("+375447764650");

        // When
        UserDto actual = userMapper.convertToDto(user);
        UserDto expected = userMapperMapStruct.convertToDto(user);

        // Then
        assertEquals(expected, actual);
    }

    @Test
    public void testConvertToEntity() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setName("Test");
        userDto.setEmail("test@example.com");
        userDto.setPhoneNumber("+375447764650");

        // When
        User actual = userMapper.convertToEntity(userDto);
        User expected = userMapperMapStruct.convertToEntity(userDto);

        // Then
        assertEquals(expected, actual);
    }

}