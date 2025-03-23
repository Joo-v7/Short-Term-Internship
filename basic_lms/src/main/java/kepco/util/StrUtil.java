package kepco.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class StrUtil {
	
	public static Boolean isEmpty(Object input) {
		if(input == null) {
			return true;
		}
		
		return ObjectUtils.isEmpty(input);
	}
	
	public static Boolean isBlank(Object input) {
		if(input == null) {
			return true;
		}
		// 문자열에 대해서만 비교하고 그 외 데이터 타입은 무조건 false
		if (input instanceof CharSequence) {
			return StringUtils.isBlank(input.toString());
		}
		
		return false;
	}
	
	public static Boolean isDate(String str) {
		
		try {
			SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd");
	        dateFormatParser.setLenient(false);
	        dateFormatParser.parse(str);
	        return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static Boolean isDatetime(String str) {
		
		try {
			SimpleDateFormat dateFormatParser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        dateFormatParser.setLenient(false);
	        dateFormatParser.parse(str);
	        return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isRegex(Object input, String str) {
	    return Pattern.matches(str, input.toString());
	}

	
	public static Boolean isInt(Object input) {
		try {
			Integer.parseInt((String) input);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public static Boolean isArray(Object input) {
	    return input != null && input.getClass().isArray();
	}
	
	public static String noNull(Object input) {
		if(input == null) {
			return "";
		}
		
		// TODO: 배열(array)에 대한 조치
		
		return input.toString();
	}
	
	public static int toInt(Object input) {
		return toInt(input, 0);
	}
	
	public static int toInt(Object input, int defaultValue) {
		if(input == null) {
			return defaultValue;
		}
		// TODO: int로 변환이 안되는 데이터 검증?
		String str = input.toString();
		
		return org.apache.commons.lang3.math.NumberUtils.toInt(str, defaultValue);
	}
	
	public static long toLng(Object input) {
		return toLng(input, 0);
	}
	
	public static long toLng(Object input, long defaultValue) {
		if(input == null) {
			return defaultValue;
		}
		String str = input.toString();
		
		return org.apache.commons.lang3.math.NumberUtils.toLong(str, defaultValue);
	}
	
	public static String toStr(Object input) {
		return toStr(input, "");
	}
	
	public static String toStr(Object input, String defaultValue) {
		if(input == null) {
			return defaultValue;
		}
		
		// TODO : 배열(array)에 대한 조치
		
		return input.toString();
	}

	public static boolean toBool(Object input) {
		return toBool(input, false);
	}
	
	public static boolean toBool(Object input, boolean defaultValue) {
		if(input == null) {
			return defaultValue;
		}
		
		return Boolean.parseBoolean(input.toString());
		
	}
	
	//월 두자리 수 맞추기
	public static String getMonth(int month) {
		if(month > 0 && month < 10) {
			return "0"+String.valueOf(month);
		}
		return String.valueOf(month);
	}

	//일 두자리 수 맞추기
	public static String getDay(int day) {
		if(day > 0 && day < 10) {
			return "0"+String.valueOf(day);
		}
		return String.valueOf(day);
	}
	
	// cms 접속 상태 가져오기
	public static String getContextPath() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String path = "";
		if("admin".equals(request.getRequestURI().split("/")[1])) {
			path = "ADMIN";
		}		
		return path;
	}
	
	// 회원 Role, RoleAuth, 가져오기
	public static Collection<? extends GrantedAuthority> getRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		
		return userDetails.getAuthorities();
	}
	
//	// 회원 Role 권한 확인
	public static String getUserRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String role = null;
		if(userDetails.getAuthorities().toString().contains("SUPER")) {
			role = "SUPER";
		} else if(userDetails.getAuthorities().toString().contains("ADMIN")) {
			role = "ADMIN";
		} else if(userDetails.getAuthorities().toString().contains("USER")) {
			role = "USER";
		}
		
		return role;
	}
	
	// 회원 Role 권한 확인
	public static String getUserRole2() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = (UserDetails)principal;
		String role = null;
//		role = userDetails.getAuthorities().toString();
		
		if(userDetails.getAuthorities().toString().contains("ADMIN_CMS")) {
			role = "ADMIN_CMS";
		} else if(userDetails.getAuthorities().toString().contains("ADMIN_LMS")) {
			role = "ADMIN_LMS";
		} else if(userDetails.getAuthorities().toString().contains("ADMIN_RAS")) {
			role = "ADMIN_RAS";
		} else if(userDetails.getAuthorities().toString().contains("ADMIN_SUPER")) {
			role = "ADMIN_SUPER";
		} else if(userDetails.getAuthorities().toString().contains("USER")) {
			role = "USER";
		}
		return role;
	}
	
	// 세션 idx 찾기
	public static int getSessionIdx() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		
		int idx = StrUtil.toInt(session.getAttribute("memberIdx"));
		return idx;
	}

	// 세션 name 찾기
	public static String getSessionName() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		
		String name = StrUtil.toStr(session.getAttribute("memberName"));
		return name;
	}
	
	// 실제 접속 IP 찾기
	public static String getClientIP() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
				
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	// 현재시간 포맷
	public static String getCurrentTime() {
		
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date now = new Date();

		String currentTime = time.format(now);
		
		return currentTime;
	}
	
    /**
     * 카멜케이스를 언더스코어케이스로 변환
     * @param input
     * @return
     */
    public static String camelCase2UnderScoreCase(String input) {
        StringBuilder sb = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String formatFileSize(long bytes) {
		DecimalFormat df = new DecimalFormat("0.00");
		
		double output = bytes;
		if (output < 1024) {
			return df.format(output) + "B";
		}
		output /= 1024.0;
		if (output < 1024) {
			return df.format(output) + "KB";
		}
		output /= 1024.0;
		if (output < 1024) {
			return df.format(output) + "MB";
		}
		output /= 1024.0;
		return df.format(output) + "GB";
	}
}
