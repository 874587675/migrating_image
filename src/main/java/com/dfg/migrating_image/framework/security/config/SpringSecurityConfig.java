package com.dfg.migrating_image.framework.security.config;

import com.dfg.migrating_image.framework.security.filter.JwtAuthenticationTokenFilter;
import com.dfg.migrating_image.framework.security.handle.AccessDeniedImplHandler;
import com.dfg.migrating_image.framework.security.handle.AuthenticationEntryPointImplHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.annotation.Resource;

/**
 * Spring Security配置类
 *
 * @author zgc
 */
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SpringSecurityConfig {
    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImplHandle authenticationEntryPointImplHandle;
    /**
     * 无权限处理类
     */
    @Resource
    private AccessDeniedImplHandler accessDeniedImplHandler;
    /**
     * JWT过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private UserDetailsService frontendUserDetailsService;

    @Resource
    private UserDetailsService backendUserDetailsService;

    @Resource
    private CorsConfigurationSource corsConfigurationSource;

    @Bean
    @Primary
    public AuthenticationManager frontendAuthenticationManager() {
        // 为前台配置
        DaoAuthenticationProvider frontendProvider = new DaoAuthenticationProvider();
        frontendProvider.setUserDetailsService(frontendUserDetailsService);
        frontendProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(frontendProvider);
    }

    @Bean
    public AuthenticationManager backendAuthenticationManager()  {
        // 为后台配置
        DaoAuthenticationProvider backendProvider = new DaoAuthenticationProvider();
        backendProvider.setUserDetailsService(backendUserDetailsService);
        backendProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(backendProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // 启用 CORS（使用 Spring Security 内置的 CORS 支持）
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                // 禁用CSRF保护
                .csrf(AbstractHttpConfigurer::disable)
                // 认证失败处理类
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPointImplHandle)
                        .accessDeniedHandler(accessDeniedImplHandler)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).headers((headersCustomizer) -> {
                    headersCustomizer.cacheControl(HeadersConfigurer.CacheControlConfig::disable).frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin);
                })
                // 授权配置
                .authorizeHttpRequests(requests -> {
                    //放行swagger相关接口
                    requests.antMatchers("/**/back/**", "/**/front/**").authenticated().anyRequest().permitAll();  // 其余请求放行
                })
                // 先添加JWT过滤器
                .addFilterAfter(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }


    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
