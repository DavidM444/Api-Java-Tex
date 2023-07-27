package ctxt.textil.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
    @EnableWebSecurity
    public class SecurityConfigurations {

        @Autowired
        private  SecurityFilter securityFilter;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
            return httpSecurity.csrf().disable().cors(Customizer.withDefaults()).sessionManagement().
                    sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().authorizeRequests().requestMatchers(
                            new AntPathRequestMatcher("/login",HttpMethod.POST.toString()),
                            new AntPathRequestMatcher("/signup",HttpMethod.POST.toString()),
                            new AntPathRequestMatcher("/hello",HttpMethod.GET.toString())
                    )
                    .permitAll()
                    .anyRequest().authenticated().and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}