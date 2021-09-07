package stylepatrick.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;

@Configuration
// Following Annotation is necessary to enable Methode security
@EnableGlobalMethodSecurity(prePostEnabled = true)
// If a Configuration Class extends the WebSecurityConfigurerAdapter the autoconfiguration from spring-security will be turned off and custom setup can be used
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

    // Customize configuration for http secrets
    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .headers()
                .frameOptions().disable()
                .cacheControl().disable()
                .and()
                .antMatcher("/**")
                // Important is the sort of the antMatchers. If the first one matches the aspect is used. It means that /api/secret/** needs to be in front of /api/**
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/secret/**")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .and()
                // Will display a login page. Depending on the Content Negotiation.
                .formLogin()
                .and()
                .logout();
    }

    // If a Bean from type UserDetailsService exist a 3-rd party provider like a DB can be used for the user credentials.
    // When no Bean with UserDetailsService exist the autoconfiguration will be used and the user can be configured via the property file or on every start a new password will be generated
    @Bean
    public UserDetailsService userDetailsService(
            final UserRepository userRepository
    ) {
        return username -> userRepository
                .findOneByLogin(username)
                .map(entity -> new User(
                        entity.getLogin(),
                        entity.getHashedPassword(),
                        new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority(entity.getRole())))))
                .orElseThrow(() ->
                        new UsernameNotFoundException(username)
                );
    }

    // If a Bean with PasswordEncoder exist the prefix for the passwords can be set like {bcrypt}
    // This means that the password is hashed by Bcrypt. Other hashes can be used too.
    // {noop} stands for clear text password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }

}