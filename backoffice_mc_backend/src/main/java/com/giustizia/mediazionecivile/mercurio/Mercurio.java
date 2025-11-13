package com.giustizia.mediazionecivile.mercurio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Mercurio {
	@Value("${leonardo.user_mercurio:}")
    private String user;
	@Value("${leonardo.password_mercurio:}")
    private String password;
	@Value("${leonardo.application_mercurio:}")
    private String application;
	@Value("${leonardo.application_code_mercurio:}")
    private String applicationCode;
	@Value("${leonardo.context_mercurio:}")
    private String context;  
	@Value("${leonardo.domain_code_mercurio:}")
    private String domainCode;
	@Value("${leonardo.principal_uri_mercurio:}")
    private String principalUri;

    protected String getUser() {
		return user;
	}
	protected String getPassword() {
		return password;
	}
	protected String getApplication() {
		return application;
	}
	
	protected String getApplicationCode() {
		return applicationCode;
	}
	
	protected String getContext() {
		return context;
	}
	
	protected String getDomainCode() {
		return domainCode;
	}
	
	protected String getPrincipalUri() {
		return principalUri;
	}
	
}
