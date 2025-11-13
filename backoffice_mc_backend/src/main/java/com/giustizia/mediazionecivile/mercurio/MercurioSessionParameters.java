package com.giustizia.mediazionecivile.mercurio;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MercurioSessionParameters {
    private String barerToken;

    
	public String getBarerToken() {
		return barerToken;
	}

	public void setBarerToken(String barerToken) {
		this.barerToken = barerToken;
	}

}
