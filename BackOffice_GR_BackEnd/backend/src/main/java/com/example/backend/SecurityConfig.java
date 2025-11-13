package com.example.backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
		//SI IMPOSTERA' IL VALORE A NULL PER FAR SI CHE VENGA RICHIESTO IL CSRF AD OGNI RICHIESTA
		delegate.setCsrfRequestAttributeName(null);
		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
		CsrfTokenRequestHandler requestHandler = delegate::handle;

		http
				.authorizeHttpRequests((authorize) -> authorize
						.anyRequest().authenticated()
				)
				//DA QUI PARTE LO SBLOCCO PER L'ACCESSO E LA PROTEZIONE DEL CSRF 
				.formLogin((login) -> login		
						.successHandler((request, response, authentication) -> response.setStatus(200)) // Just return 200 instead of redirecting to '/'
				) 
				//DA QUI PARTE L'USCITA A TUTTE LE AUTORIZZAZIONI CONCESSE 
				.logout((logout) -> logout
						.logoutSuccessHandler(new CsrfTokenAwareLogoutSuccessHandler(tokenRepository)) // Handler that generates and save a new CSRF token on logout
				)
				.cors(Customizer.withDefaults())
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
				.csrf((csrf) -> csrf
						.csrfTokenRepository(tokenRepository)
						.csrfTokenRequestHandler(requestHandler)
				);

		return http.build();
	}
	
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
	

	//IL METODO IN CUI VERRA' IMPOSTATO SE L'UTENTE HA I REQUISITI PER POTER ACCEDERE
	/*@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("prova").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}*/
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("X-XSRF-TOKEN");
		config.addAllowedHeader("Content-Type");
		config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		config.setAllowCredentials(true); // This is important since we are using session cookies
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	
	


}
