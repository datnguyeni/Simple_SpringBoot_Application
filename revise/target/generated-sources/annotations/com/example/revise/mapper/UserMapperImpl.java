package com.example.revise.mapper;

import com.example.revise.dto.RequestDTO;
import com.example.revise.dto.ResponseDTO;
import com.example.revise.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-23T00:59:15+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );
        user.setRole( dto.getRole() );

        return user;
    }

    @Override
    public void updateEntity(RequestDTO dto, User user) {
        if ( dto == null ) {
            return;
        }

        user.setUsername( dto.getUsername() );
        user.setPassword( dto.getPassword() );
        user.setRole( dto.getRole() );
    }

    @Override
    public ResponseDTO toResponseDTO(User user) {
        if ( user == null ) {
            return null;
        }

        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setId( user.getId() );
        responseDTO.setUsername( user.getUsername() );

        return responseDTO;
    }

    @Override
    public List<ResponseDTO> toResponseList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<ResponseDTO> list = new ArrayList<ResponseDTO>( users.size() );
        for ( User user : users ) {
            list.add( toResponseDTO( user ) );
        }

        return list;
    }
}
