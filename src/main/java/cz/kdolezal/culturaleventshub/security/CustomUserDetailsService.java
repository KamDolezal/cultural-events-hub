package cz.kdolezal.culturaleventshub.security;

import cz.kdolezal.culturaleventshub.entity.UserEntity;
import cz.kdolezal.culturaleventshub.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserJpaRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with name: " + username + " not found"));

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetail(user);
    }


    //@Override
    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        UserEntity user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        return new CustomUserDetail(user);
    }
}
