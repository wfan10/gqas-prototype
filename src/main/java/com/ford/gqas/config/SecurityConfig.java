package com.ford.gqas.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// use x-auth-token header for security.
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// find the user in question from some list here ???
		auth.inMemoryAuthentication()
				.withUser("admin").password("password").roles("ADMIN", "USER")
				.and()
				.withUser("wfan10").password("password2").roles("USER");
	}

	// ignore security when going to resources.
	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers("/resources"); 
	}

	/*
	 * Reference:
	 * http://www.sedooe.com/2016/04/rest-authentication-using-spring-security-
	 * and-spring-session/(non-Javadoc)
	 * 
	 * @see org.springframework.security.config.annotation.web.configuration.
	 * WebSecurityConfigurerAdapter#configure(org.springframework.security.
	 * config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				// disable csrf for localhost testing.
				// Reference:
				// http://docs.spring.io/spring-security/site/docs/4.0.x/reference/htmlsingle/#csrf-configure
				.csrf().disable()
				
				.authorizeRequests()
					// Allow cross origin OPTIONS requests. 
					// Reference: http://www.sedooe.com/2016/08/cors-and-authentication/
					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					// authenticate all the rest. 
					.anyRequest()
						.authenticated()
				// cache protected pages until login. 
				.and()
					.requestCache()
					.requestCache(new NullRequestCache())
				.and()
					.httpBasic()
				// manage sessions: only 1 session per user and migrate session variables. 
				// reference: http://www.baeldung.com/spring-security-session
				.and()
					.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation().migrateSession()
						.maximumSessions(1)					
				;

	}
}
