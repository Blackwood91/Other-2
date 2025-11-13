package com.giustizia.mediazionecivile.security;

import org.springframework.security.core.Authentication;
import org.springframework.cache.CacheManager;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CacheClearingLogoutHandler implements LogoutHandler {

    private final CacheManager cacheManager;

    public CacheClearingLogoutHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            // Esempio di svuotamento di una cache specifica
            if (cacheManager.getCache("cacheVE") != null) {
                cacheManager.getCache("cacheVE").clear();
            }
        }
    }
}
