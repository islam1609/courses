package bitlab.security.site.courses.mapper;

import bitlab.security.site.courses.DTO.UserDTO;
import bitlab.security.site.courses.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
