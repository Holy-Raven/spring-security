package com.springsecurity.springsecurity.config;

import com.springsecurity.springsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
//    вариант 1 In memory
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
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.inMemoryAuthentication()
//                    .withUser("user")
//                    .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
//                    .roles("USER")
//                .and()
//                    .withUser("admin")
//                    .password("{bcrypt}$2a$12$mueU3Jxp8Ei.U82KcrT3ue1FoWRC4RPGEFzFTRW.0RmiMa7INvEvi")
//                    .roles("ADMIN", "USER");
//    }
//    вариант 3 Jdbs Authentication (у меня не сохраняет в базу)
//    @Bean
//    public JdbcUserDetailsManager users(DataSource dataSource) {
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
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//        //если юзер уже существувт, его необходимо удалить
//        if (users.userExists(user.getUsername())) {
//            users.deleteUser(user.getUsername());
//        }
//        //если админ уже существует его необходимо удалить
//        if (users.userExists(admin.getUsername())) {
//            users.deleteUser(admin.getUsername());
//        }
//        //сохраняем в базу юзера и адмна
//        users.createUser(user);
//        users.createUser(admin);
//
//        return users;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}