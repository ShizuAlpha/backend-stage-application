//package com.marsa.backendstageapplication.Config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration @EnableWebSecurity @RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final UserDetailsService userDetailsService;
//    private final BCryptPasswordEncoder passwordEncoder;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDaetailsService).passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers("/login/**", "/api/users/refreshtoken").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/register/**").permitAll();
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/users/role/addtouser").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ADMIN");
//        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/forms/**", "/api/responses/**", "/api/inputs/**", "/api/receptions/**").hasAnyAuthority("FORM_CREATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/forms/**", "/api/responses/**", "/api/inputs/**", "/api/receptions/**").hasAnyAuthority("FORM_CREATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("FORM_CREATOR");
//        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/responses/**").hasAnyAuthority("FORM_READER");
//        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("FORM_READER");
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(new AuthFilter(authenticationManagerBean()));
//        http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}
