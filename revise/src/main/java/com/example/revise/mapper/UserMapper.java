package com.example.revise.mapper;

import com.example.revise.dto.RequestDTO;
import com.example.revise.dto.ResponseDTO;
import com.example.revise.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;


/* can autowire vi khia bao la component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RequestDTO dto);
    ResponseDTO toResponseDTO(User user);

}
*/

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RequestDTO dto);
    void updateEntity(RequestDTO dto, @MappingTarget User user);

    ResponseDTO toResponseDTO(User user);
    List<ResponseDTO> toResponseList(List<User> users);

}
