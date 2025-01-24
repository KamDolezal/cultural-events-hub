package cz.kdolezal.culturaleventshub.repository;

import cz.kdolezal.culturaleventshub.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
}
