package it.uniroma3.siwbooks.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.service.CredentialsService;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired private CredentialsService userDetailsService;
    @Autowired private PasswordEncoder     passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authProvider() {
      DaoAuthenticationProvider p = new DaoAuthenticationProvider();
      p.setUserDetailsService(userDetailsService);
      p.setPasswordEncoder(passwordEncoder);
      return p;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
        .authenticationProvider(authProvider())
        .csrf().disable()
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/", "/books/**", "/register", "/login", "/css/**", "/images/**")
              .permitAll()
            .requestMatchers("/admin/**").hasRole(Credentials.ADMIN_ROLE)
            .requestMatchers("/user/**").hasRole(Credentials.DEFAULT_ROLE)
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("username")
            .passwordParameter("pwd")
            // ← redirect here on success
            .defaultSuccessUrl("/books", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutSuccessUrl("/")
            .permitAll()
        );
      return http.build();
    }
}
