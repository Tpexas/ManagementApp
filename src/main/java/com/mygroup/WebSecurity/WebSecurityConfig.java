package com.mygroup.WebSecurity;

import com.mygroup.patient.CustomPatientDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomPatientDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pacientai/**").hasAnyAuthority("Admin", "Receptionist")
                .antMatchers("/apsilankymai/**").hasAnyAuthority("Admin", "Receptionist")
                .antMatchers("/gydytojai/**").hasAnyAuthority("Admin", "Receptionist")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .defaultSuccessUrl("/")
                    .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception{
        return (web) -> web.ignoring().antMatchers("/images/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        exposeDirectory("doctor-photos", registry);
    }

    private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path doctorUploadDir = Paths.get("./doctor-photos");
        String doctorUploadPath = doctorUploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/doctor-photos/**").addResourceLocations("file:/"+ doctorUploadPath + "/");
    }
}
