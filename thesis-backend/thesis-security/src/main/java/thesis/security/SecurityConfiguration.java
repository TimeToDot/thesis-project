package thesis.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {

/*  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity){
    httpSecurity.cors().and().csrf().disable()
      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
  }*/

}