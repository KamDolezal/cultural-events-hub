package cz.kdolezal.culturaleventshub.mapper;

import cz.kdolezal.culturaleventshub.dto.RoleDTO;
import cz.kdolezal.culturaleventshub.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperDTO {
    public RoleDTO convertToDto(RoleEntity roleEntity) {
        return new RoleDTO(
                roleEntity.getId(),
                roleEntity.getRoleName(),
                roleEntity.getDescription()
        );
    }
}
