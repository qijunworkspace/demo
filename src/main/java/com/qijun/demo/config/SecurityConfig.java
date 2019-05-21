package com.qijun.demo.config;

import com.qijun.demo.common.GlobalConstants;
import com.qijun.demo.security.CustomAccessDeniedHandler;
import com.qijun.demo.security.CustomAuthenticationFailureHandler;
import com.qijun.demo.security.CustomAuthenticationSuccessHandler;
import com.qijun.demo.security.KaptchaValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * 配置用户登录信息 使用Spring-Security
 * 具体路径可从配置文件读取
 * @author Qijun
 * @date 11/23/18 3:17 PM
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    /**
     * 静态资源路径
     */
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPath;

    /**
     * 获取用户信息
     */
    private UserDetailsService userDetailsService;

    /**
     * 密码处理
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 自定义登录成功处理器
     */
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * 自定义登录失败处理器
     */
    private CustomAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * redis session操作接口
     * RedisOperationsSessionRepository -> RedisSession
     */
    private FindByIndexNameSessionRepository sessionRepository;

    /**
     * 验证码过滤器
     */
    private KaptchaValidationFilter kaptchaValidationFilter;

    /**
     * Creates an instance with the default configuration enabled.
     */
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder,
                          CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler,
                          FindByIndexNameSessionRepository sessionRepository, KaptchaValidationFilter kaptchaValidationFilter) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.sessionRepository = sessionRepository;
        this.kaptchaValidationFilter = kaptchaValidationFilter;
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     * used for configuration settings that impact global security
     * (ignore resources, set debug mode, reject requests by implementing a custom firewall definition).
     * 不需要登录的路径
     * @param web web安全对象
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(staticPath);
        web.ignoring().antMatchers(GlobalConstants.LOGIN_PAGE);
        web.ignoring().antMatchers(GlobalConstants.LOGIN_KAPTHCA);
        web.ignoring().antMatchers(GlobalConstants.ERROR_PAGE);
        web.ignoring().antMatchers(GlobalConstants.ERROR_404_PAGE);
        web.ignoring().antMatchers(GlobalConstants.UNAUTHORIZED_PAGE);
        web.ignoring().antMatchers(GlobalConstants.LOGIN_REGISTRATION_URL);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider()
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    /**
     * allows configuration of web based security at a resource level, based on a selection match
     * @param http http访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Adds the Security headers to the response.
        // This is activated by default when using WebSecurityConfigurerAdapter
        //.tokenRepository("xxx")
        http.headers()
                .and()
                .rememberMe()
                //设置前台传送的Checkbox名称
                .rememberMeParameter("remember")
                //使用spring-session实现rememberMe
                .rememberMeServices(springSessionRememberMeServices());

        //设置form登陆请求
        http.formLogin()
                .loginPage(GlobalConstants.LOGIN_PAGE)
                //.usernameParameter("username") //默认值
                //.passwordParameter("password")
                .loginProcessingUrl(GlobalConstants.LOGIN_PROCESS_URL)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
                //.defaultSuccessUrl(HOME_PAGE) //登录成功后重定向的页面

        //添加验证码过滤器
        http.addFilterBefore(kaptchaValidationFilter, UsernamePasswordAuthenticationFilter.class);

        //验证规则
        http.authorizeRequests()
                //配置actuator权限
                //.antMatchers("/manager").hasAuthority().hasRole()
                //.antMatchers(LOGIN_PAGE).permitAll()
                //.antMatchers(LOGIN_PROCESS_URL).permitAll()
                .anyRequest().authenticated();


        //没有操作权限对应的处理操作
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler());
                //.accessDeniedPage(GlobalConstants.UNAUTHORIZED_PAGE)

        //退出系统
        http.logout()
                //如果使用了CSRF，必须使用POST方式退出
                .logoutUrl(GlobalConstants.LOGOUT_PROCESS_URL)
                //非要用GET，可以使用下面的配置
                //.logoutRequestMatcher(new AntPathRequestMatcher(GlobalConstants.LOGOUT_PROCESS_URL))
                .logoutSuccessUrl(GlobalConstants.LOGIN_PAGE)
                .clearAuthentication(true)
                .deleteCookies("SESSION","JSESSIONID")
                .invalidateHttpSession(true);

        //关闭csrf防护, 否则出现302重定向(因为是从login.js中访问的)
        http.csrf().disable();

        //session配置
        //.sessionAuthenticationStrategy()
        http.sessionManagement()
                //Session无效的跳转页面
                .invalidSessionUrl(GlobalConstants.LOGIN_PAGE)
                //设置只允许一个用户登录
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                //失效跳转
                //.expiredUrl(GlobalConstants.LOGIN_PAGE)
                //会话注册在spring-session中
                .sessionRegistry(sessionRegistry());
    }

    /**
     * RememberMe 默认保存器七天
     * @return springSessionRememberMeServices
     */
    @Bean
    public SpringSessionRememberMeServices springSessionRememberMeServices(){
        SpringSessionRememberMeServices springSessionRememberMeServices = new SpringSessionRememberMeServices();
        //有效时间7天
        springSessionRememberMeServices.setValiditySeconds(GlobalConstants.REMEMBER_ME_VALIDITY_SECONDS);
        //前台checkbox的名称
        springSessionRememberMeServices.setRememberMeParameterName("remember");
        return springSessionRememberMeServices;
    }


    /**
     * 在spring-session中记录会话，可用于控制会话数量
     * @return SpringSessionBackedSessionRegistry
     */
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(sessionRepository);
    }

    @Bean
    public AccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

}
