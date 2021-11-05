package es.jguimar.safeboxAPI.config;
import es.jguimar.safeboxAPI.config.filter.JWTAuthorizationFilter;
import es.jguimar.safeboxAPI.service.impl.MongoUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties
@AllArgsConstructor //Constructor-Based Dependency Injection
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  MongoUserDetailsService userDetailsService;

  //Basic autentication
  @Configuration
  @Order(1)
  public class UserPassSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
              .antMatcher("/safebox/**/open")
              .authorizeRequests().anyRequest().authenticated()
              .and()
              .httpBasic()
              .authenticationEntryPoint(authExceptionEntryPoint())
              .and().sessionManagement().disable();
    }

    @Override
    public void configure(WebSecurity web) {
      web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui/**", "/webjars/**");
    }
  }


  //Token autenticaction
  @Configuration
  @Order(2)
  class TokenSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
              .addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
              .authorizeRequests()
              .antMatchers(HttpMethod.POST, "/safebox**").permitAll()
              .antMatchers("/safebox/**/items").authenticated();
    }
  }

  @Bean
  public AuthExceptionEntryPoint authExceptionEntryPoint() {
    return new AuthExceptionEntryPoint();
  }

  @Bean
  public JWTAuthorizationFilter jwtAuthorizationFilter() {
    return new JWTAuthorizationFilter();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void configure(AuthenticationManagerBuilder builder) throws Exception {
    builder.userDetailsService(userDetailsService);
  }
}