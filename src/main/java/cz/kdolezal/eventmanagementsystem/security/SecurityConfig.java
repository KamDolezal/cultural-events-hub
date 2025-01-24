package cz.kdolezal.eventmanagementsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())    // CORS rules are in CorsConfig
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("user/login").permitAll()  //done
                        .requestMatchers("user/status").permitAll() //done
                        .requestMatchers(HttpMethod.POST, "/user").permitAll()  //done
                        .requestMatchers(HttpMethod.GET, "/event/**").permitAll()   //done
                        .requestMatchers(HttpMethod.POST, "/ticket/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.GET, "/payment/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.POST, "/payment/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.GET, "/qrcode/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.POST, "/qrcode/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.PUT, "/qrcode/**").permitAll()  //TODO add authority
                        .requestMatchers(HttpMethod.POST, "/event/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
                        .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();    //This type of provider is suitable for databases
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}