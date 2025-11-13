package giustiziariparativa.security;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

	@Value("${leonardo.setAllowedOrigins:}")
	protected String SET_ALLOWED_ORIGINS; 

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
		                .requestMatchers("/mediatori-pubblici/**").permitAll() // Percorso senza autenticazione
		                .requestMatchers("/user/startSecurity").permitAll() // Percorso senza autenticazione
		                // Ruoli BackOffice
		                .requestMatchers("/ente/**").hasAnyRole("1", "2", "4")
		                .requestMatchers("/mediatori/**").hasAnyRole("1", "2", "4")
						.requestMatchers("/utente-abilitato/**").hasAnyRole("1", "4")
		                // Ruoli Frontoffice
		                .requestMatchers("/mediatori-frontoffice/**").hasRole("3")
						.anyRequest().authenticated()
				)
				.sessionManagement((sessionManagement) -> sessionManagement
						.sessionFixation().newSession()
						.maximumSessions(1)
						.expiredUrl("/login?expired")
				)
				// DA QUI PARTE LO SBLOCCO PER L'ACCESSO
				.formLogin((login) -> login		
						.successHandler((request, response, authentication) -> response.setStatus(200)) 
				) 
				// DA QUI PARTE L'USCITA A TUTTE LE AUTORIZZAZIONI CONCESSE 
				.logout((logout) -> logout
						.logoutSuccessHandler(new CsrfTokenAwareLogoutSuccessHandler(tokenRepository))
				)
				.cors(Customizer.withDefaults())
				// DA QUI PARTE LA GENERAZIONE IN CASO DI ERRORE NELL'AUTENTICAZIONE
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
				)
//				.csrf().disable()
//            	.cors().disable();      
				// DA QUI SI INDICA LA PROTEZIONE DEL CSRF 
				.csrf((csrf) -> csrf
						.csrfTokenRepository(tokenRepository)
						.csrfTokenRequestHandler(requestHandler)
				);

		return http.build();
	}

	
	//IL METODO IN CUI VERRA' IMPOSTATO SE L'UTENTE HA I REQUISITI PER POTER ACCEDERE
	/*@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
				.username("prova").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}*/
	
	// Con questo verrà implementato l'userDetailsService personalizzato
    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    // TUTTE LE CONFIGURAZIONE RELATIVE DOVE DOVRA' UTILIZZARE LE CONFIGURAZIONI INSERITE
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedHeader("X-XSRF-TOKEN");
		config.addAllowedHeader("Content-Type");
		config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedOrigins(Arrays.asList(SET_ALLOWED_ORIGINS)); 
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
