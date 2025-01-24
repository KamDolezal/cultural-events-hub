package cz.kdolezal.culturaleventshub.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "roles")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    @Setter
    private String roleName;    //values could be: ROLE_USER, ROLE_EDITOR, RULE_ADMIN

    @Column(nullable = true)
    @Setter
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<UserEntity> userEntities = new HashSet<>();

    public RoleEntity(String roleName, String description, Set<UserEntity> userEntities) {
        this.roleName = roleName;
        this.description = description;
        this.userEntities = userEntities;
    }
}
