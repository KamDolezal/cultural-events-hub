package cz.kdolezal.eventmanagementsystem.mapper;

import cz.kdolezal.eventmanagementsystem.dto.RoleDTO;
import cz.kdolezal.eventmanagementsystem.entity.RoleEntity;
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
