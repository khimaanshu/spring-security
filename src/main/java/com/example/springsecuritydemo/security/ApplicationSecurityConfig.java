package com.example.springsecuritydemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.google.common.collect.Sets;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {	
	private final PasswordEncoder passwordEncoder;	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
		
		this.passwordEncoder=passwordEncoder;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/v1/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.STUDENT.name())
		.anyRequest().authenticated().and()
		.formLogin().loginPage("/login").permitAll()
		.defaultSuccessUrl("/courses", true);
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService()
	{
		UserDetails student=User.builder()
				.username("student").password(passwordEncoder.encode("password"))
				//.roles(ApplicationUserRole.STUDENT.name())
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
				.build();
		UserDetails admin=User.builder()
				.username("admin").password(passwordEncoder.encode("password81"))
				//.roles(ApplicationUserRole.ADMIN.name())
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(student, admin);
	}
}
/*
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled=true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {	
	private final PasswordEncoder passwordEncoder;	
	@Autowired
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
		
		this.passwordEncoder=passwordEncoder;
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/v1/students/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.STUDENT.name())
//		.antMatchers(HttpMethod.POST, "/api/v1/courses/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT, "/api/v1/courses/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.DELETE, "/api/v1/courses/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.GET,"/api/v1/courses/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.STUDENT.name())
		.anyRequest().authenticated().and().httpBasic();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService()
	{
		UserDetails student=User.builder()
				.username("student").password(passwordEncoder.encode("password"))
				//.roles(ApplicationUserRole.STUDENT.name())
				.authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
				.build();
		UserDetails admin=User.builder()
				.username("admin").password(passwordEncoder.encode("password81"))
				//.roles(ApplicationUserRole.ADMIN.name())
				.authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(student, admin);
	}
}
*/
