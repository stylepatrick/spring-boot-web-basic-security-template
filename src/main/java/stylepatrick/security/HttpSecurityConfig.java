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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

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
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/secret/**")
                .authenticated()
                .antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .and()
                .formLogin()
                .and()
                .logout();
    }

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }

}