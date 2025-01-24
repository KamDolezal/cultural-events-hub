package cz.kdolezal.eventmanagementsystem.api;

import cz.kdolezal.eventmanagementsystem.api.request.UserAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.UserLoginRequest;
import cz.kdolezal.eventmanagementsystem.dto.UserDTO;
import cz.kdolezal.eventmanagementsystem.entity.UserEntity;

import java.util.List;

public interface UserService {
    long add(UserAddRequest request);

    void delete(long id);

    UserDTO get(long id);

    long getUserIdByUsername(String username);

    UserEntity getUserEntity(long id);

    List<UserEntity> getAllUserEntity();

    List<UserDTO> getAll();

    String verify(UserLoginRequest request);
}
