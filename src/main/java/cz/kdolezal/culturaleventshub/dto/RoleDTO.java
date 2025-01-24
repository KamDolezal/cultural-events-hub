package cz.kdolezal.eventmanagementsystem.dto;

import lombok.Value;

@Value
public class RoleDTO {
    Long id;
    String roleName;
    String description;
}
