package com.example.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/registration").permitAll()
                .antMatchers("/participant").permitAll()
                .antMatchers("/tournament").permitAll()
                .antMatchers("/movie").permitAll()
                .antMatchers("/country").permitAll()
                .antMatchers("/sponsor").permitAll()
                .antMatchers("/tournament/create").hasRole("ADMIN")
                .antMatchers("/tournament/edit").hasRole("ADMIN")
                .antMatchers("/tournament/add_sponsor").hasRole("ADMIN")
                .antMatchers("/tournament/remove_sponsor").hasRole("ADMIN")
                .antMatchers("/result/create").hasRole("ADMIN")
                .antMatchers("/result/create-next").hasRole("ADMIN")
                .antMatchers("/result/edit").hasRole("ADMIN")
                .antMatchers("/participant/create").hasRole("ADMIN")
                .antMatchers("/participant/edit").hasRole("ADMIN")
                .antMatchers("/country/create").hasRole("ADMIN")
                .antMatchers("/country/edit").hasRole("ADMIN")
                .antMatchers("/sponsor/create").hasRole("ADMIN")
                .antMatchers("/sponsor/edit").hasRole("ADMIN")
                .antMatchers("/tournament/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/result/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/participant/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/country/details").hasRole("ADMIN")
                .antMatchers("/sponsor/details").hasAnyRole("USER","ADMIN")
                .antMatchers("/tournament/delete").hasRole("ADMIN")
                .antMatchers("/result/delete").hasRole("ADMIN")
                .antMatchers("/participant/delete").hasRole("ADMIN")
                .antMatchers("/country/delete").hasRole("ADMIN")
                .antMatchers("/sponsor/delete").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable()
                /*.exceptionHandling().accessDeniedPage("/access/denied")*/;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(encoder())
                .usersByUsernameQuery("select username, password, active from user_entity where username = ?")
                .authoritiesByUsernameQuery("select u.username, ur.roles " +
                        "from user_entity u inner join user_entity_roles ur on u.id = ur.user_entity_id where u.username=?");
    }
}