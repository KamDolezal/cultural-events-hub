package cz.kdolezal.eventmanagementsystem.repository;

import cz.kdolezal.eventmanagementsystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
}
