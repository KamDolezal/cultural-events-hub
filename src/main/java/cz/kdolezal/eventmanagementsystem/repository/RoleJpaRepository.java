package cz.kdolezal.eventmanagementsystem.repository;

import cz.kdolezal.eventmanagementsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long> {
}
