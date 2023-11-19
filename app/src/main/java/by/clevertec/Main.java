package by.clevertec;

import by.clevertec.util.YamlReader;

public class Main {
    public static void main(String[] args) {
//        UserDtoValidator validator = new UserDtoValidator();
//        UserDto userDto = new UserDto();
//
//        // Заполните поля userDto
//        userDto.setName("Имя");
//        userDto.setEmail("email@example.com");
//        userDto.setPhoneNumber("+3752991234567");
//
//        validator.validate(userDto);


        String cacheType = YamlReader.chooseCacheType();
        String cacheCapacity = YamlReader.chooseCacheCapacity();

        System.out.println("Cache Type: " + cacheType);
        System.out.println("Cache Capacity: " + cacheCapacity);
    }
}


