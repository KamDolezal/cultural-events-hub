package cz.kdolezal.culturaleventshub.dto;

import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Value
public class UserDTO {
    Long id;
    String username;
    String email;
    Set<RoleDTO> roles;
    OffsetDateTime createdAt;
    List<Long> ticketIdList;
    List<Long> paymentIdList;
}
