package kepco.common.site;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.validation.constraints.NotNull;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import kepco.cms.visitCount.VisitCountService;
import kepco.cms.visitCount.VisitCountVo;
import kepco.util.StrUtil;


@WebListener
public class VisitCounter implements HttpSessionListener {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * 디바이스 종류
     */
    enum ClientDevice {
        MOBILE, TABLET, DESKTOP;
    }
    
    /**
     * 
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        String referer = request.getHeader("Referer");
        String userAgent = request.getHeader("USER-AGENT");
        
        String os = getClientOS(userAgent);
        String browser = getClientBrowser(userAgent);
        String device = getClientDevice(userAgent);
        
        String ip = StrUtil.getClientIP();
        
        String url = request.getRequestURI();
        
        String sessionId = event.getSession().getId();
        
        VisitCountVo vo = new VisitCountVo();
        vo.setVisitReferer(referer);
        vo.setVisitUserAgent(userAgent);
        vo.setVisitOs(os);
        vo.setVisitBrowser(browser);
        vo.setVisitDevice(device);
        vo.setVisitIp(ip);
        vo.setVisitUrl(url);
        vo.setVisitSessionId(sessionId);
        
        //TODO: 서비스를 액세스하는 좋은 방법이 있는지 검토해보자.
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
        VisitCountService visitCountService = ctx.getBean(VisitCountService.class);
        
        long result = 0;
        result = visitCountService.insert(vo);
    }
    
    /**
     * 
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	log.debug("sessionDestroyed : " + event.getSession().getId());
    }
    
    /**
     * 브라우저 종류
     * @param userAgent
     * @return
     */
    public static String getClientBrowser(@NotNull String userAgent) {
        String browser = "";
        
        if (userAgent.contains("Edg/")) {
            // Microsoft Edge (Chromium-based)
            browser = "Microsoft Edge";
        } else if (userAgent.indexOf("Trident/7.0") > -1) {
            // Internet Explorer 11
            browser = "IE11";
        } else if (userAgent.indexOf("MSIE 10") > -1) {
            // Internet Explorer 10
            browser = "IE10";
        } else if (userAgent.indexOf("MSIE 9") > -1) {
            // Internet Explorer 9
            browser = "IE9";
        } else if (userAgent.indexOf("MSIE 8") > -1) {
            // Internet Explorer 8
            browser = "IE8";
        } else if (userAgent.contains("Chrome/")) {
            // Google Chrome
            browser = "Chrome";
        } else if (userAgent.contains("Firefox/")) {
            // Mozilla Firefox
            browser = "Firefox";
        } else if (userAgent.contains("Safari/") && userAgent.indexOf("Chrome/") == -1) {
            // Apple Safari
            browser = "Safari";
        } else {
            // Other browsers
            browser = "Other";
        }
        return browser;
    }
    
    /**
     * 운영체제 종류
     * @param userAgent
     * @return
     */
    public static String getClientOS(@NotNull String userAgent) {
        String os = "";
        userAgent = userAgent.toLowerCase();
        if (userAgent.indexOf("windows nt 10.0") > -1) {
            os = "Windows10";
        } else if (userAgent.indexOf("windows nt 6.1") > -1) {
            os = "Windows7";
        } else if (userAgent.indexOf("windows nt 6.2") > -1 || userAgent.indexOf("windows nt 6.3") > -1) {
            os = "Windows8";
        } else if (userAgent.indexOf("windows nt 6.0") > -1) {
            os = "WindowsVista";
        } else if (userAgent.indexOf("windows nt 5.1") > -1) {
            os = "WindowsXP";
        } else if (userAgent.indexOf("windows nt 5.0") > -1) {
            os = "Windows2000";
        } else if (userAgent.indexOf("windows nt 4.0") > -1) {
            os = "WindowsNT";
        } else if (userAgent.indexOf("windows 98") > -1) {
            os = "Windows98";
        } else if (userAgent.indexOf("windows 95") > -1) {
            os = "Windows95";
        } else if (userAgent.indexOf("iphone") > -1) {
            os = "iPhone";
        } else if (userAgent.indexOf("ipad") > -1) {
            os = "iPad";
        } else if (userAgent.indexOf("android") > -1) {
            os = "android";
        } else if (userAgent.indexOf("mac") > -1) {
            os = "mac";
        } else if (userAgent.indexOf("linux") > -1) {
            os = "Linux";
        } else {
            os = "Other";
        }
        return os;
    }
    
    /**
     * 기기 종류
     * @param userAgent
     * @return
     */
    public static String getClientDevice(@NotNull String userAgent) {
        String device = "";
        userAgent = userAgent.toUpperCase();
        if (userAgent.indexOf("MOBILE") > -1) {
            if (userAgent.indexOf("PHONE") == -1) {
                device = ClientDevice.MOBILE.name();
            } else {
                device = ClientDevice.TABLET.name();
            }
        } else {
            device = ClientDevice.DESKTOP.name();
        }
        return device;
    }
}
		

