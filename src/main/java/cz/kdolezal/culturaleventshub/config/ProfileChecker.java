package cz.kdolezal.culturaleventshub.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// class is used for log information about active profile
@Component
public class ProfileChecker {

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @PostConstruct
    public void checkProfile() {
        System.out.println("Active profile: " + activeProfile);
    }
}