package kepco.cms.setting;

import org.springframework.stereotype.Component;

@Component
public class PropApp {

	/** 관리자 접근 화이트 IP */
	private String adminWhiteIp;
	
	/** 관리자 접근 블랙 IP */
	private String adminBlackIp;
	
	public String getAdminWhiteIp() {
		return adminWhiteIp;
	}
	
	public void setAdminWhiteIp(String adminWhiteIp) {
		this.adminWhiteIp = adminWhiteIp;
	}
	
	public String getAdminBlackIp() {
		return adminBlackIp;
	}
	
	public void setAdminBlackIp(String adminBlackIp) {
		this.adminBlackIp = adminBlackIp;
	}

}
