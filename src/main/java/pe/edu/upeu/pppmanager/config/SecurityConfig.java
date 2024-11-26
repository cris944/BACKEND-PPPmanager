package pe.edu.upeu.pppmanager.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	    private UserDetailsService userDetailsService;
	    private JwtAuthenticationEntryPoint authenticationEntryPoint;
	    private JwtAuthenticationFilter authenticationFilter;
	
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf().disable()
	            .authorizeHttpRequests(authorize -> authorize
	                .requestMatchers("/api/auth/**","/api/**").permitAll() 
	                .anyRequest().authenticated()  
	            )
	            .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint)) 
	            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);  

	        return http.build();
	    }
	
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }
	}