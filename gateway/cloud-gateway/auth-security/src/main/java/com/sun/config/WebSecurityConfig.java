package com.sun.config;

import com.google.common.collect.Lists;
import com.sun.authorize.service.*;
import com.sun.authorize.service.impl.CommonUserDetailsService;
import com.sun.login.filter.AjaxUsernamePasswordLoginFilter;
import com.sun.login.filter.JWTAuthorizeCheckFilter;
import com.sun.login.filter.JWTLoginFilter;
import com.sun.login.filter.JWTTokenService;
import com.sun.login.handler.AjaxAuthenticationFailureHandler;
import com.sun.login.handler.AjaxAuthenticationSuccessHandler;
import com.sun.login.handler.AjaxExceptionHandler;
import com.sun.login.handler.AjaxSessionExpiredStrategyHandler;
import com.sun.login.provider.UsernameDaoAuthenticationProvider;
import com.sun.permission.AjaxAccessDeniedHandler;
import com.sun.permission.SecurityAccessDecisionManager;
import com.sun.permission.SecurityUrlResourceMappingHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import java.util.List;

/**
 * SpringSecurity 配置
 *
 * <p>
 * （1）用户登陆
 * 会被 AuthenticationProcessingFilter 拦截，调用AuthenticationManager的实现，
 * 而且AuthenticationManager会调用ProviderManager来获取用户验证信息
 * （不同的Provider调用的服务不同，因为这些信息可以是在数据库上，可以是在LDAP服务器上，可以是xml配置文件上等），
 * 如果验证通过后会将用户的权限信息封装一个User放到spring的全局缓存SecurityContextHolder中，以备后面访问资源时使用。
 * <p>
 * （2）访问资源（即授权管理）
 * 访问url时，会通过AbstractSecurityInterceptor拦截器拦截，
 * 其中会调用FilterInvocationSecurityMetadataSource的方法来获取被拦截url所需的全部权限，
 * 再调用授权管理器AccessDecisionManager，这个授权管理器会通过spring的全局缓存SecurityContextHolder获取用户的权限信息，
 * 还会获取被拦截的url和被拦截url所需的全部权限，
 * 然后根据所配的策略（有：一票决定，一票否定，少数服从多数等），如果权限足够，则返回，权限不够则报错并调用权限不足页面。
 *
 * @author SunChenjie
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.enable:true}")
    private boolean authEnable;
    @Value("${auth.loginUrl:/web/usercenter/user/login}")
    private String loginUrl;
    @Value("${auth.logoutUrl:/web/usercenter/user/logout}")
    private String logoutUrl;
    @Value("${auth.permitAllUrls:}")
    private String[] permitAllUrls;

    @Value("${auth.jwt.enable:true}")
    private boolean jwtEnable;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityAuthService securityAuthService;

    @Autowired
    private SecurityPermissionService securityPermissionService;

    @Autowired
    private JWTTokenService jwtTokenService;

    @Autowired
    private LoginAroundInterceptService loginAroundInterceptService;

    @Autowired
    private SecurityNoLoginUrlService securityNoLoginUrlService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域请求
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 跨域
                .and().cors()
                // 关闭csrd拦截
                .and().csrf().disable();

        // 是否进行权限拦截
        if (authEnable) {
            // 禁用session
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.headers().frameOptions().disable()
                    .and()
                    // 登录url
                    .formLogin().loginProcessingUrl(loginUrl)
                    // 登出url
                    .and().logout().logoutUrl(logoutUrl)
                    .and().exceptionHandling().authenticationEntryPoint(ajaxExceptionHandler())
//                    .and().rememberMe()
//                    .tokenRepository(null)
//                    .tokenValiditySeconds(100)
//                    .userDetailsService(userDetailsService())
//                    .and()
//                    .sessionManagement()
//                .invalidSessionStrategy(invalidSessionStrategy)
                    // 最大session并发数量1
//                    .maximumSessions(1)
                    // 之后的登录踢掉之前的登录
//                    .maxSessionsPreventsLogin(true)
                    // 自定义session过期策略，替代默认的{@link ConcurrentSessionFilter.ResponseBodySessionInformationExpiredStrategy}，
                    // 复写onExpiredSessionDetected方法，默认方法只输出异常，没业务逻辑。这里需要返回json
//                    .expiredSessionStrategy(ajaxSessionExpiredStrategyHandler())
//                    .and().and().logout().deleteCookies("JSESSIONID")
                    // 以下的请求都不需要认证
                    .and().authorizeRequests()
                    //对preflight放行 options请求
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
//                    .antMatchers(loginUrl).permitAll()
                    .antMatchers(logoutUrl).permitAll()
                    .antMatchers("/v2/api-docs",
                            "/webjars/**",//swagger api json
                            "/swagger-resources/configuration/ui",//用来获取支持的动作
                            "/swagger-resources",//用来获取api-docs的URI
                            "/swagger-resources/configuration/security",//安全选项
                            "/swagger-ui.html").permitAll()
                    .antMatchers("/cloud/**").permitAll();

            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.headers().and().authorizeRequests();

            List<String> urls = Lists.newArrayList();

            if (ArrayUtils.isNotEmpty(permitAllUrls)) {
                urls.addAll(Lists.newArrayList(permitAllUrls));
            }
            List<String> noNeedLoginUrls = securityNoLoginUrlService.noLoginUrl();
            if (CollectionUtils.isNotEmpty(noNeedLoginUrls)) {
                urls.addAll(noNeedLoginUrls);
            }
            urls.stream().distinct().filter(StringUtils::isNotBlank).forEach(url -> expressionInterceptUrlRegistry.antMatchers(url).permitAll());

            // 转换json登录请求
            expressionInterceptUrlRegistry.and()
                    .addFilterAt(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(jwtAuthorizeCheckFilter(), UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling().accessDeniedHandler(ajaxAccessDeniedHandler());

            // 任何请求权限验证
            expressionInterceptUrlRegistry.anyRequest().authenticated();
        }
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public UsernameDaoAuthenticationProvider authenticationProvider() {
        UsernameDaoAuthenticationProvider usernameDaoAuthenticationProvider = new UsernameDaoAuthenticationProvider();
        usernameDaoAuthenticationProvider.setSecurityAuthService(securityAuthService);
        usernameDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
        usernameDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return usernameDaoAuthenticationProvider;
    }

    @Override
    @ConditionalOnExpression("${auth.enable:true}")
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // add provider
        builder.authenticationProvider(authenticationProvider());
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    @Override
    @ConditionalOnExpression("${auth.enable:true}")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxUsernamePasswordLoginFilter ajaxAuthenticationFilter() throws Exception {
        AjaxUsernamePasswordLoginFilter ajaxUsernamePasswordLoginFilter;
        if (jwtEnable) {
            ajaxUsernamePasswordLoginFilter = new JWTLoginFilter(jwtTokenService);
        } else {
            ajaxUsernamePasswordLoginFilter = new AjaxUsernamePasswordLoginFilter();
        }
        ajaxUsernamePasswordLoginFilter.setFilterProcessesUrl(loginUrl);

        ajaxUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManagerBean());
        // 登录成功处理器，返回JSON
        ajaxUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        // 登录失败处理器 返回json
        ajaxUsernamePasswordLoginFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());
        return ajaxUsernamePasswordLoginFilter;
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public CommonUserDetailsService userDetailsService(SecurityUserService securityUserService) {
        return new CommonUserDetailsService(securityUserService);
    }


    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler = new AjaxAuthenticationFailureHandler();
        ajaxAuthenticationFailureHandler.setLoginAroundInterceptService(loginAroundInterceptService);
        return ajaxAuthenticationFailureHandler;
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler = new AjaxAuthenticationSuccessHandler();
        ajaxAuthenticationSuccessHandler.setLoginAroundInterceptService(loginAroundInterceptService);
        return ajaxAuthenticationSuccessHandler;
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxSessionExpiredStrategyHandler ajaxSessionExpiredStrategyHandler() {
        return new AjaxSessionExpiredStrategyHandler();
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxAccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    AjaxExceptionHandler ajaxExceptionHandler() {
        return new AjaxExceptionHandler();
    }

    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public JWTAuthorizeCheckFilter jwtAuthorizeCheckFilter() {
        JWTAuthorizeCheckFilter jwtAuthorizeCheckFilter = new JWTAuthorizeCheckFilter(securityAuthService, jwtTokenService);
        return jwtAuthorizeCheckFilter;
    }

    /**
     * @return 加密算法Encoder
     */
    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 权限部分
    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public SecurityAccessDecisionManager securityAccessDecisionManager() {
        return new SecurityAccessDecisionManager();
    }

    /**
     * @return url与权限映射加载类
     */
    @Bean
    @ConditionalOnExpression("${auth.enable:true}")
    public SecurityUrlResourceMappingHandler securityUrlResourceMappingHandler() {
        SecurityUrlResourceMappingHandler handler = new SecurityUrlResourceMappingHandler();
        handler.setSecurityPermissionService(securityPermissionService);
        return handler;
    }

}
