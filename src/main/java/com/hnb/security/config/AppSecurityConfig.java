package com.hnb.security.config;

import com.hnb.jwt.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AppSecurityConfig(PasswordEncoder passwordEncoder, @Qualifier("myUDS") UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/valute").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/srednjitecaj/**/**/**").hasAnyAuthority("USER", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/praznine/provjera").hasAnyAuthority("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/praznine/popunjavanje").hasAnyAuthority("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/users/").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/users").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/download/**/**/**").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/log").hasAnyAuthority("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
