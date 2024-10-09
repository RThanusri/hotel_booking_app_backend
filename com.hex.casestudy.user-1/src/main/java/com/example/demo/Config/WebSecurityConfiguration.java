package com.example.demo.Config;


	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
	import org.springframework.security.authentication.AuthenticationProvider;
	import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
	import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
	import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
	import org.springframework.security.config.annotation.web.builders.HttpSecurity;
	import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
	import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
	import org.springframework.security.config.http.SessionCreationPolicy;
	import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
	import org.springframework.security.crypto.password.PasswordEncoder;
	import org.springframework.security.web.SecurityFilterChain;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.Enum.Role;
import com.example.demo.Service.UserService;

	


   
	@Configuration
	@EnableWebSecurity
	@EnableMethodSecurity
	public class WebSecurityConfiguration implements WebMvcConfigurer {
		private final JwtAuthenticationFilter jwtAuthenticationFilter;

	    @Autowired
	    public WebSecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	    }
		    @Autowired
		    private  UserService userService;
		    
		    @Bean
		    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		        httpSecurity.csrf(AbstractHttpConfigurer::disable)
		                .authorizeHttpRequests(req -> req
		                        .requestMatchers("/api/auth/**").permitAll()
		                        .requestMatchers("/uploads/**").permitAll()

		                        .requestMatchers("/api/upload").permitAll()
		                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
		                        .requestMatchers("/api/admin/**").hasAnyAuthority(Role.ADMIN.name())
		                        .requestMatchers("/api/user/**").hasAnyAuthority(Role.USER.name(),Role.ADMIN.name())
		                        .requestMatchers("/api/owner/**").hasAnyAuthority(Role.HOTEL_OWNER.name(),Role.ADMIN.name())
		                        .requestMatchers("/api/shared/**").hasAnyAuthority(Role.ADMIN.name(), Role.HOTEL_OWNER.name(),Role.USER.name()) 
		                        .anyRequest().authenticated()).sessionManagement
		                        (management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		                .authenticationProvider(authenticationProvider())
		                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		        return httpSecurity.build();
		    }
		    @Bean
		    public PasswordEncoder passwordEncoder() {
		        return new BCryptPasswordEncoder();
		    }

		    @Bean
		    public AuthenticationProvider authenticationProvider() {
		        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		        daoAuthenticationProvider.setUserDetailsService(userService.userDetailsService());
		        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		        return daoAuthenticationProvider;
		    }

		    @Bean
		    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		        return authenticationConfiguration.getAuthenticationManager();
		    }
		   @Override
		    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		        registry.addResourceHandler("/uploads/**")
		                .addResourceLocations("file:///C:/Users/91730/Downloads/hotelPictures/");
		    }

	}


