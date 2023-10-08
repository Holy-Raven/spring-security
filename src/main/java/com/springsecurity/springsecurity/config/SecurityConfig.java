package com.springsecurity.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                    .antMatchers("/login*").permitAll()
                    .antMatchers("/authenticated/**").authenticated()
                    .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                    .formLogin()
                    //.successHandler("тут можно настроить страничку на которую юзер попадет после авторизации")
                    .successHandler(myAuthenticationSuccessHandler())
                .and()
                    .logout().logoutSuccessUrl("/");
    }


//    два созданных юзера лежат в памяти

//    вариант 1
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
//                .roles("USER", "ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//    вариант 2
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                    .withUser("user")
                    .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
                    .roles("USER")
                .and()
                    .withUser("admin")
                    .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
                    .roles("ADMIN", "USER");
    }
}


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .passwordEncoder(NoOpPasswordEncoder.getInstance())
//                .usersByUsernameQuery("select username, password, active from t_user where username = ?")
//                .authoritiesByUsernameQuery("select u.username, ur.roles from t_user u inner user_role ur on u.id = ur.user_id where u.username = ?");
//    }