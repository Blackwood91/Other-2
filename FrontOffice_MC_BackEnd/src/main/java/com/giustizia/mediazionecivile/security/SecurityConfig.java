package com.giustizia.mediazionecivile.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
	@Value("${leonardo.setAllowedOrigins:}")
	protected String SET_ALLOWED_ORIGINS; 
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, CacheClearingLogoutHandler cacheClearingLogoutHandler) throws Exception {
		CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
		XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
		//SI IMPOSTERA' IL VALORE A NULL PER FAR SI CHE VENGA RICHIESTO IL CSRF AD OGNI RICHIESTA
		delegate.setCsrfRequestAttributeName(null);
		// Use only the handle() method of XorCsrfTokenRequestAttributeHandler and the
		// default implementation of resolveCsrfTokenValue() from CsrfTokenRequestHandler
		CsrfTokenRequestHandler requestHandler = delegate::handle;

		// AntPathRequestMatcher FIX PER NON AVERE PROBLEMI CON wildcards o alti pattern (**, *, ?) NEL PATH SPECIFICATO 
		http
				.authorizeHttpRequests((authorize) -> authorize
				        .requestMatchers(new AntPathRequestMatcher("/user/startSecurity")).permitAll()		
				        .requestMatchers(new AntPathRequestMatcher("/user/utentLoggato")).permitAll()			
				        .requestMatchers(new AntPathRequestMatcher("/user/registrazioneUtente")).permitAll()	
				        .requestMatchers(new AntPathRequestMatcher("/emissionePdgOdm/getAllEmissionePdgOdmForTable")).permitAll()	
						.requestMatchers(new AntPathRequestMatcher("/error/**")).permitAll()	
				        .requestMatchers(new AntPathRequestMatcher("/alboOdm/**")).permitAll()						
				        .requestMatchers(new AntPathRequestMatcher("/alboMediatori/**")).permitAll()
				        .requestMatchers(new AntPathRequestMatcher("/alboOdmSedi/**")).permitAll()	
				        .requestMatchers(new AntPathRequestMatcher("/alboOdmPdg/**")).permitAll()	

						.anyRequest().authenticated()
				)	
				//.cors(cors -> cors.disable())
				.sessionManagement((sessionManagement) -> sessionManagement
						.sessionFixation().newSession()
						.maximumSessions(1)
						.expiredUrl("/login?expired")
				)
				// DA QUI PARTE LO SBLOCCO PER L'ACCESSO
				.formLogin((login) -> login		
						.successHandler((request, response, authentication) -> response.setStatus(200)) 
		                .failureHandler(customAuthenticationFailureHandler())  // Usa il failure handler personalizzato
				) 
				// DA QUI PARTE L'USCITA A TUTTE LE AUTORIZZAZIONI CONCESSE 
				.logout((logout) -> logout
		                .addLogoutHandler(cacheClearingLogoutHandler) // Aggiungi il nostro LogoutHandler personalizzato
						.logoutSuccessHandler(new CsrfTokenAwareLogoutSuccessHandler(tokenRepository))
				)
				.cors(Customizer.withDefaults())
				// DA QUI PARTE LA GENERAZIONE IN CASO DI ERRORE NELL'AUTENTICAZIONE
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
				// DA QUI SI INDICA LA PROTEZIONE DEL CSRF 
				.csrf((csrf) -> csrf
						.csrfTokenRepository(tokenRepository)
						.csrfTokenRequestHandler(requestHandler)
				)
				;
		

		return http.build();
	}

	
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
    public CustomAuthenticationProvider customAuthenticationProvider() {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Imposta il servizio per caricare gli utenti
        return provider;
    }
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(customAuthenticationProvider());
    }



    // TUTTE LE CONFIGURAZIONE RELATIVE DOVE DOVRA' UTILIZZARE LE CONFIGURAZIONI INSERITE
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("X-XSRF-TOKEN");
		config.addAllowedHeader("Content-Type");
		config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
		//config.setAllowedOrigins(Arrays.asList(SET_ALLOWED_ORIGINS)); 
		config.setAllowedOrigins(Arrays.asList(SET_ALLOWED_ORIGINS)); //LOCALE
		config.setAllowCredentials(true); // This is important since we are using session cookies
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	// Per avviare la possibilità di criptazione 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
