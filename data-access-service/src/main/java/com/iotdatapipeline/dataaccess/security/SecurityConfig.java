package com.iotdatapipeline.dataaccess.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for setting up security and authentication for the application.
 * Provides HTTP security configuration, user details management, and password encoding.
 *
 * <p>This configuration uses HTTP Basic Authentication and in-memory user details management for simplicity.
 * The application allows access to the /api/sensor-data/aggregated endpoint only for authenticated users.</p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Logger for the class
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    /**
     * Configures HTTP security to define access rules and authentication mechanisms.
     *
     * <p>This method disables CSRF protection for simplicity (note: not recommended for production),
     * secures the `/api/sensor-data/aggregated` endpoint for authenticated users, and allows all other requests.</p>
     *
     * @param http The HttpSecurity object used to configure web-based security.
     * @return The configured SecurityFilterChain object that applies the security settings.
     * @throws Exception If the security configuration fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring HTTP security...");

        http.csrf().disable() // Disable CSRF for simplicity (not recommended for production)
                .authorizeRequests()
                .requestMatchers("/api/sensor-data/aggregated").authenticated() // Secure the endpoint
                .anyRequest().permitAll() // Allow all other requests
                .and()
                .httpBasic();  // Enable HTTP Basic Authentication

        logger.info("HTTP security configured successfully.");

        return http.build(); // Return the configured SecurityFilterChain
    }

    /**
     * Configures in-memory user details service with a predefined user ("admin") for authentication.
     *
     * <p>This method creates an in-memory user store, where the "admin" user has the password "password" (BCrypt encoded)
     * and a role of "USER". This is for demonstration purposes and should be replaced by a more secure user store in production.</p>
     *
     * @return The UserDetailsService implementation that provides user details for authentication.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        logger.info("Setting up in-memory user details service...");

        // Configure in-memory authentication with a user "admin"
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder().encode("password")) // BCrypt encoded password
                .roles("USER")
                .build());

        logger.info("In-memory user details service configured with user 'admin'.");

        return manager; // Return the configured user details service
    }

    /**
     * Configures the password encoder used to encode passwords for security.
     *
     * <p>In this configuration, the BCrypt password encoder is used to securely encode user passwords.
     * BCrypt is a strong one-way encryption algorithm for hashing passwords.</p>
     *
     * @return A PasswordEncoder bean configured to use BCrypt for password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Setting up BCryptPasswordEncoder for password encoding...");

        return new BCryptPasswordEncoder(); // Return the BCrypt password encoder
    }
}
