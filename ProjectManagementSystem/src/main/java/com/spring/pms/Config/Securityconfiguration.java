package com.spring.pms.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.security.access.AccessDeniedException;
import com.spring.pms.Filter.Authfilter;
import com.spring.pms.Service.Userinfouserdetailservice;



@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class Securityconfiguration {
//	@Autowired
//	private Authfilter authfilter;
	    @Autowired
	    @Qualifier("handlerExceptionResolver")
	    private HandlerExceptionResolver exceptionResolver;
	    
	  @Bean
	    public Authfilter authfilter(){
	        return new Authfilter(exceptionResolver);
	    }
	@Bean
	public UserDetailsService userDetailsService()
	{
		return new  Userinfouserdetailservice();
	}

	@Bean
	public PasswordEncoder encoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider AuthenticationProvider=new DaoAuthenticationProvider();
		AuthenticationProvider.setUserDetailsService(userDetailsService());
		AuthenticationProvider.setPasswordEncoder(encoder());
		return AuthenticationProvider;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception
	{
	return http.csrf().disable().authorizeHttpRequests().requestMatchers("/user/token","/swagger-ui/**","/v3/api-docs/**","/user/","/project/user","/user/refreshToken").permitAll()
	.and().authorizeHttpRequests().requestMatchers("/user/**","/project/**","/task/**","/subtask/**").authenticated().and()
	 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    .authenticationProvider(authenticationProvider()).addFilterBefore(authfilter(), UsernamePasswordAuthenticationFilter.class).build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
		

}
