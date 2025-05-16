package br.com.eds.api.eds.service.gerenciamento.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Configuração de CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sessionless (stateless)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/user/**", "/design/**", "/software/**", "/conserto/**", "/print/**", "/search/**", "/download/**").permitAll();
                    req.anyRequest().authenticated(); // Requer autenticação para outros endpoints
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Adicionando o filtro customizado
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configuração de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("https://edsfrontenddevelopment.vercel.app"); // URL do frontend em homologação
        corsConfiguration.addAllowedOrigin("http://127.0.0.1:5500"); // URL do frontend em desenvolvimento (Live Server)
        corsConfiguration.addAllowedMethod("*"); // Permite todos os métodos (GET, POST, etc)
        corsConfiguration.addAllowedHeader("*"); // Permite todos os cabeçalhos
        corsConfiguration.setAllowCredentials(true); // Permite o envio de cookies e credenciais

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Aplica a configuração para todos os endpoints
        return source;
    }
}
