package by.clevertec.mapper;

import by.clevertec.dto.UserDto;
import by.clevertec.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapperMapStruct {

    UserDto convertToDto(User user);

    User convertToEntity(UserDto userDto);
}
