package kepco.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kepco.cms.sec.roleHierarchy.RoleHierarchyService;
import kepco.common.security.CmsAccessDeniedHandler;
import kepco.common.security.CmsAuthenticationEntryPoint;
import kepco.common.security.CmsAuthenticationFailureHandler;
import kepco.common.security.CmsAuthenticationSuccessHandler;
import kepco.common.security.CmsLogoutSuccessHandler;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		// You are asking Spring Security to ignore Ant [pattern='/pages/**'].
		// This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
		return (web)->web.ignoring().antMatchers("/assets/**","/pages/**","/datas/**");
		
	}
	
	@Autowired
	RoleHierarchyService roleHierarchyService;
	
	@Value("${global.admin-path}")
	private String adminPath;
	
	/**
	 * 관리자 시큐리티 필터
	 * @param http
	 * @return
	 * @throws Exception
	 * @see {@link #userConfig(HttpSecurity)}
	 */
	@Bean
	@Order(1)
	protected SecurityFilterChain adminConfig(HttpSecurity http) throws Exception {
		
		http
		.antMatcher("/" + adminPath + "/**")
		.authorizeRequests(authorizeRequests -> authorizeRequests
				.antMatchers("/" + adminPath + "/cms/member/auth/**").permitAll()
//				.antMatchers("/" + adminPath + "/lms/member/auth/**").permitAll()
//				.antMatchers("/" + adminPath + "/ras/member/auth/**").permitAll()
				.antMatchers("/" + adminPath + "/cms/**").hasRole("ADMIN_CMS")
				.antMatchers("/" + adminPath + "/lms/**").hasRole("ADMIN_LMS")
				.antMatchers("/" + adminPath + "/ras/**").hasRole("ADMIN_RAS")
				.antMatchers("/" + adminPath + "/**").hasRole("ADMIN_SUPER")
				.anyRequest().authenticated()
		);
		
		// 폼 로그인
		http.formLogin(formLogin -> formLogin
			.loginPage("/" + adminPath + "/cms/member/auth/")
			.usernameParameter("memberId")
			.passwordParameter("memberPw")
			.loginProcessingUrl("/" + adminPath + "/cms/member/auth/login")
			.successHandler(cmsAuthenticationSuccessHandler())
			.failureHandler(cmsAuthenticationFailureHandler())
			.permitAll()
		);
		
		// 로그아웃
		http.logout(logout -> logout
//			.logoutUrl("/" + adminPath + "/cms/member/auth/logout") // logoutUrl()은 안된다?
			.logoutRequestMatcher(new AntPathRequestMatcher("/" + adminPath + "/cms/member/auth/logout"))
			.logoutSuccessHandler(cmsLogoutSuccessHandler())
			.invalidateHttpSession(true)
		);
		
		// 401, 403
		http.exceptionHandling(exceptionHandling -> exceptionHandling
			.accessDeniedHandler(cmsAccessDeniedHandler()) // 403
			.authenticationEntryPoint(cmsAuthenticationEntryPoint()) // 401
		);
		
		// 세션
		http.sessionManagement(sessionManagement -> sessionManagement
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false)
		);
		
		return http.build();
	}
	
	
	@Value("${global.user.login-url}")
	private String loginUrl;
	
	/**
	 * 사용자 시큐리티 필터
	 * @param http
	 * @return
	 * @throws Exception
	 */
	@Bean
	@Order(4)
	protected SecurityFilterChain userConfig(HttpSecurity http) throws Exception {
		// 사용자
		
		http.csrf(csrf -> csrf
			.ignoringAntMatchers("/api/**") // API에 대해 CSRF 예외
//			.ignoringRequestMatchers(request -> "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) // AJAX에 대해 CSRF 예외
		);
		
		
		http
		.antMatcher("/**")
		.authorizeRequests(authorizeRequests -> authorizeRequests
			.anyRequest().permitAll()
		);
		
		// 폼 로그인
		http.formLogin(formLogin -> formLogin
			.loginPage(loginUrl)
			.usernameParameter("memberId")
			.passwordParameter("memberPw")
			.loginProcessingUrl("/cms/member/auth/login")
			.successHandler(cmsAuthenticationSuccessHandler())
			.failureHandler(cmsAuthenticationFailureHandler())
			.permitAll()
		);
		
		// 로그아웃
		http.logout(logout -> logout
//			.logoutUrl("/cms/member/auth/logout") // logoutUrl()은 안된다?
			.logoutRequestMatcher(new AntPathRequestMatcher("/cms/member/auth/logout"))
			.logoutSuccessHandler(cmsLogoutSuccessHandler())
			.invalidateHttpSession(true)
		);
		
		
		// 401, 403
		http.exceptionHandling(exceptionHandling -> exceptionHandling
			.accessDeniedHandler(cmsAccessDeniedHandler()) // 403
			.authenticationEntryPoint(cmsAuthenticationEntryPoint()) // 401
		);
		
		// 세션
		http.sessionManagement(sessionManagement -> sessionManagement
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // 기본값 : IF_REQUIRED
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false) // 중복로그인 허용
		);
		
		return http.build();
	}
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		
		// 데이터베이스를 통해 조회
		String roleList = roleHierarchyService.findAllHierarchy(null);
		/*
		Map<String, List<String>> roleHierarchyMap = new HashMap<>();
		roleHierarchyMap.put("ROLE_ADMIN_SUPER", Arrays.asList("ROLE_ADMIN"));
		roleHierarchyMap.put("ROLE_ADMIN", Arrays.asList("ROLE_ADMIN_CMS", "ROLE_ADMIN_LMS", "ROLE_ADMIN_RAS"));
		roleHierarchyMap.put("ROLE_USER", Arrays.asList("ROLE_MEMBER"));
		String roleList = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);
		*/
		
		roleHierarchy.setHierarchy(roleList);
		return roleHierarchy;
	}
	
	@Bean
	public AuthenticationSuccessHandler cmsAuthenticationSuccessHandler() {
		return new CmsAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler cmsAuthenticationFailureHandler() {
		return new CmsAuthenticationFailureHandler();
	}
	
	@Bean
	public AccessDeniedHandler cmsAccessDeniedHandler() {
		return new CmsAccessDeniedHandler();
	}
	
	@Bean
	public AuthenticationEntryPoint cmsAuthenticationEntryPoint() {
		return new CmsAuthenticationEntryPoint();
	}
	
	@Bean
	public LogoutSuccessHandler cmsLogoutSuccessHandler() {
		return new CmsLogoutSuccessHandler();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}