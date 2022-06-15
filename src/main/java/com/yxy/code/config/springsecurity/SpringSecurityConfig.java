package com.yxy.code.config.springsecurity;

import com.yxy.code.filter.LoginFilter;
import com.yxy.code.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.yxy.code.config.springsecurity")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Autowired
    SpringSecuritySuccessHandler springSecuritySuccessHandler;
    @Autowired
    SpringSecurityFailureHandler springSecurityFailureHandler;
    @Bean
    public LoginFilter loginFilter() throws Exception{
        LoginFilter loginFilter = new LoginFilter("/user/login");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(springSecuritySuccessHandler);
        loginFilter.setAuthenticationFailureHandler(springSecurityFailureHandler);
        return loginFilter;
    }

    @Bean
    public SpringSecurityAuthenticationProvider myAuthenticationProvider(){
        return new SpringSecurityAuthenticationProvider();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider());
    }

    @Autowired
    SecurityFilter securityFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
