package com.example.demo.configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JWTSecurityConfiguration {
	static     	String secret = "s/4KMb61LOrMYYAn4rfaQYSgr+le5SMrsMzKw8G6bXc=";

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
        .authorizeHttpRequests(
        		(authorize) -> authorize
        		.requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
        )
        
        .cors().disable()
        .csrf().disable()
        .httpBasic().disable()
        .oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtAccessTokenDecoder()) ) );
    return http.build();
	}
	
    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder() {
    	SecretKey key = new SecretKeySpec(secret.getBytes(), "ES256");
        return NimbusJwtDecoder.withSecretKey(key).build();
    }

    @Bean
    @Primary
    public JwtEncoder jwtAccessTokenEncoder() {
		SecretKey key = new SecretKeySpec(secret.getBytes(), "ES256");
		JWKSource<SecurityContext> jwks = new ImmutableSecret<SecurityContext>(key);
        return new NimbusJwtEncoder(jwks);
    }

}
