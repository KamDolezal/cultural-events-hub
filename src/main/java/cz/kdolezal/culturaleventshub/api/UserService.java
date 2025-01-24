package cz.kdolezal.culturaleventshub.api;

import cz.kdolezal.culturaleventshub.api.request.UserAddRequest;
import cz.kdolezal.culturaleventshub.api.request.UserLoginRequest;
import cz.kdolezal.culturaleventshub.dto.UserDTO;
import cz.kdolezal.culturaleventshub.entity.UserEntity;

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
