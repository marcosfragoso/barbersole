package com.edu.ifpb.barbersole.security;

import com.edu.ifpb.barbersole.util.PerfilTipo;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private static final String BARBEIRO = PerfilTipo.BARBEIRO.getDesc();
    private static final String CLIENTE = PerfilTipo.CLIENTE.getDesc();
    private static final String ADMIN = PerfilTipo.ADMIN.getDesc();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers(
                        "/webjars/**",
                        "/css/**",
                        "/images/**",
                        "/js/**"
                ).permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/cadastrar").permitAll()
                .requestMatchers("/remember").permitAll()
                .requestMatchers("/sendEmail").permitAll()
                .requestMatchers("/token").permitAll()
                .requestMatchers("/reset").permitAll()
                .requestMatchers("/resetPassword").permitAll()
                .requestMatchers("/home/**").authenticated()
                .requestMatchers(antMatcher("/perfil/**")).hasAnyAuthority(CLIENTE)
                .requestMatchers(antMatcher("/barbeiros/register/**")).hasAnyAuthority(ADMIN)
                .requestMatchers(antMatcher("/usuarios/editarBarbeiro/**")).hasAnyAuthority(ADMIN)
                .requestMatchers(antMatcher("/usuarios/excluirBarbeiro/**")).hasAnyAuthority(ADMIN, CLIENTE)
                .requestMatchers(antMatcher("/agendamentos/agendar/**")).hasAnyAuthority(ADMIN, CLIENTE)
                .anyRequest().authenticated()
        ).formLogin((formLogin) -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login-error")
                .permitAll()
        ).logout((logout) -> logout
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
        ).exceptionHandling(exception -> exception
                .accessDeniedPage("/login")
        ).sessionManagement((session) -> session
                .maximumSessions(1)
                .expiredUrl("/expired")
                .sessionRegistry(sessionRegistry())
        ).sessionManagement(session -> session
                .invalidSessionUrl("/expired")
        ).sessionManagement((session) -> session
                .sessionFixation()
                .newSession()
                .sessionAuthenticationStrategy(sessionAuthStrategy())
        );
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SessionAuthenticationStrategy sessionAuthStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<?> servletListenerRegistrationBean() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
