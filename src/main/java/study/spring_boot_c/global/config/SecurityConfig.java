package study.spring_boot_c.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    /*
        Swagger 접속을 위한 위한 Spring Security 입니다.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/swagger-ui/**")
                .authorizeHttpRequests(
                        auth -> auth.anyRequest().hasRole("ADMIN") // swagger-ui 접근은 admin 이상 권한 요구
                ).formLogin(Customizer.withDefaults())
                .sessionManagement(
                        session -> session.invalidSessionUrl("/login") // 세션 만료시 로그인 페이지로 이동
                                .maximumSessions(4)) // ADMIN 로그인 4명까지 가능 (스터디원 4명)
                .csrf(
                        csrf -> csrf.disable()); // csrf 끄기



        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest()
                .permitAll())
                .csrf(
                        csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults()); // 임시 테스트 용으로, 일단 swagger 제외한 모든 api 제한없이 접근 가능

        return http.build();
    }

    /*
        임시 유저 정보들...
     */
    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        UserDetails admin = User.withUsername("admin").password("{noop}1111").roles("ADMIN").build();
        return  new InMemoryUserDetailsManager(user, admin);
    }

    /*
        role 간의 계층적 구조 추가를 위하여 Hierarchy를 추가합니다.
        상위 role은 하위 role의 권한이 필요하더라도 접근할 수 있습니다.
        (Hierarchy 설정을 안하면 접근 못합니다!)
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy(
                "ROLE_ADMIN > ROLE_USER"
        );
    }
}
