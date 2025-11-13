package com.example.backend;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Qui dovresti recuperare le informazioni sull'utente dal tuo sistema o database.
        // Se l'utente non viene trovato, lanciare UsernameNotFoundException.

        // Esempio di recupero di un utente fittizio (da sostituire con la logica reale):
        if ("user".equals(username)) {
            // Creare un oggetto UserDetails personalizzato con i dati dell'utente.
            // Puoi utilizzare l'implementazione predefinita di UserDetails, ad esempio 
        	// org.springframework.security.core.userdetails.User.
            UserDetails user = org.springframework.security.core.userdetails.User
                .withUsername("user")
                .password("{noop}password") // La password dovrebbe essere criptata
                .roles("USER")
                .build();
            return user;
        } else {
            throw new UsernameNotFoundException("Utente non trovato: " + username);
        }
    }
}