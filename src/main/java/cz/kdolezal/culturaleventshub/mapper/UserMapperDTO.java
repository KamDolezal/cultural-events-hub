package cz.kdolezal.culturaleventshub.mapper;

import cz.kdolezal.culturaleventshub.dto.UserDTO;
import cz.kdolezal.culturaleventshub.entity.PaymentEntity;
import cz.kdolezal.culturaleventshub.entity.TicketEntity;
import cz.kdolezal.culturaleventshub.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapperDTO {
    private final RoleMapperDTO roleMapperDTO;

    public UserMapperDTO(RoleMapperDTO roleMapperDTO) {
        this.roleMapperDTO = roleMapperDTO;
    }

    public UserDTO convertToDto(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRoles()
                        .stream()
                        .map(roleMapperDTO::convertToDto)
                        .collect(Collectors.toSet()),
                userEntity.getCreatedAt(),
                userEntity.getTicketList()
                        .stream()
                        .map(TicketEntity::getId)
                        .toList(),
                userEntity.getPaymentList()
                        .stream()
                        .map(PaymentEntity::getId)
                        .toList()
        );
    }
}