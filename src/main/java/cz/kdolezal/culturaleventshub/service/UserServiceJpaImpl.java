package cz.kdolezal.eventmanagementsystem.service;

import cz.kdolezal.eventmanagementsystem.api.UserService;
import cz.kdolezal.eventmanagementsystem.api.exception.BadRequestException;
import cz.kdolezal.eventmanagementsystem.api.exception.InternalErrorException;
import cz.kdolezal.eventmanagementsystem.api.exception.ResourceNotFoundException;
import cz.kdolezal.eventmanagementsystem.api.request.UserAddRequest;
import cz.kdolezal.eventmanagementsystem.api.request.UserLoginRequest;
import cz.kdolezal.eventmanagementsystem.dto.UserDTO;
import cz.kdolezal.eventmanagementsystem.entity.RoleEntity;
import cz.kdolezal.eventmanagementsystem.entity.UserEntity;
import cz.kdolezal.eventmanagementsystem.mapper.UserMapperDTO;
import cz.kdolezal.eventmanagementsystem.repository.RoleJpaRepository;
import cz.kdolezal.eventmanagementsystem.repository.UserJpaRepository;
import cz.kdolezal.eventmanagementsystem.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UserServiceJpaImpl implements UserService {
    private final UserJpaRepository userJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceJpaImpl.class);
    private final UserMapperDTO userMapperDTO;

    public UserServiceJpaImpl(UserJpaRepository userJpaRepository, UserMapperDTO userMapperDTO) {
        this.userJpaRepository = userJpaRepository;
        this.userMapperDTO = userMapperDTO;
    }

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RoleJpaRepository roleJpaRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Override
    public long add(UserAddRequest request) {
        try {
            //Adding default role USER
            RoleEntity defaultRole = roleJpaRepository.findById(1L)     //id=1: ROLE_USER
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            UserEntity userEntity = new UserEntity(request.getUsername(), encoder.encode(request.getPassword()), request.getEmail(), OffsetDateTime.now());
            userEntity.getRoles().add(defaultRole);
            return userJpaRepository.save(userEntity).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("User with email: " + request.getEmail() + " already exists");
        } catch (DataAccessException e) {
            logger.error("Error while adding user");
            throw new InternalErrorException("Error while adding user");
        }
    }

    @Override
    public void delete(long id) {
        if (this.get(id) != null) {
            userJpaRepository.deleteById(id);
        }
    }

    @Override
    public UserDTO get(long id) {
        UserEntity user = userJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " was not found"));
        return userMapperDTO.convertToDto(user);
    }

    @Override
    public long getUserIdByUsername(String username) {
        UserEntity user = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found"));
        return user.getId();
    }

    @Override
    public UserEntity getUserEntity(long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " was not found"));
    }

    @Override
    public List<UserEntity> getAllUserEntity() {
        return userJpaRepository.findAll().stream().toList();
    }

    @Override
    public List<UserDTO> getAll() {
        return userJpaRepository.findAll().stream().map(userMapperDTO::convertToDto).toList();
    }

    public String verify(UserLoginRequest request) {
        UserEntity user = userJpaRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + request.getUsername() + " not found"));

        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), request.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user);
        } else {
            throw new BadCredentialsException("Authentication failed: Invalid credentials");
        }
    }
}
