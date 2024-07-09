package kepco.cms.stat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

/** 방문자 */
@Component
public class StatVo {

	/** PK */
	private int visitIdx;
	
	/** 세션 */
	private String visitSessionId;
	
	/** 브라우저 */
	private String visitBrowser;
	
	/** 운영체제 */
	private String visitOs;
	
	/** 모바일 */
	private String visitMobile;
	
	/** 검색어 */
	private String visitKeyword;
	
	/** 검색엔진 */
	private String visitSearchEngine;
	
	/** 접속정보 */
	private String visitUserAgent;
	
	/** 이전주소 */
	private String visitRefererUrl;
	
	/** 접속페이 */
	private String visitUrl;
	
	/** 접속일 */
	private String visitDate;
	
	/** 메뉴 */
	private int menuIdx;
	
	/** 사이트이름 */
	private String siteNm;
	
	/** ip 주소 */
	private String visitIp;
	
	/** 국가 */
	private String visitCountry;
	
	/** 지역 */
	private String visitCity;
	
	/** 좌표 */
	private String visitLoc;
	
	/** 방문자 */
	private int visitCount;

	/** 모바일접속자 */
	private int mobTotal;

	/** 컴퓨터접속자 */
	private int comTotal;

	/**하루 방문자 수*/
	private int dayVisit;
	
	/**일주일 방문자 수*/
	private int weekVisit;
	
	/**한달 방문자 수*/
	private int monthVisit;
	
	/**일년 방문자 수*/
	private int yearVisit;
	
	/**시스템 구분*/
	private String systemType;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	
	public void setVisitIdx(int visitIdx) {
		this.visitIdx = visitIdx;
	}
	public int getVisitIdx() {
		return this.visitIdx;
	}
	
	public void setVisitSessionId(String visitSessionId) {
		this.visitSessionId = visitSessionId;
	}
	public String getVisitSessionId() {
		return this.visitSessionId;
	}
	
	public void setVisitBrowser(String visitBrowser) {
		this.visitBrowser = visitBrowser;
	}
	public String getVisitBrowser() {
		return this.visitBrowser;
	}
	
	public void setVisitOs(String visitOs) {
		this.visitOs = visitOs;
	}
	public String getVisitOs() {
		return this.visitOs;
	}
	
	public void setVisitMobile(String visitMobile) {
		this.visitMobile = visitMobile;
	}
	public String getVisitMobile() {
		return this.visitMobile;
	}
	
	public void setVisitKeyword(String visitKeyword) {
		this.visitKeyword = visitKeyword;
	}
	public String getVisitKeyword() {
		return this.visitKeyword;
	}
	
	public void setVisitSearchEngine(String visitSearchEngine) {
		this.visitSearchEngine = visitSearchEngine;
	}
	public String getVisitSearchEngine() {
		return this.visitSearchEngine;
	}
	
	public void setVisitUserAgent(String visitUserAgent) {
		this.visitUserAgent = visitUserAgent;
	}
	public String getVisitUserAgent() {
		return this.visitUserAgent;
	}
	
	public void setVisitRefererUrl(String visitRefererUrl) {
		this.visitRefererUrl = visitRefererUrl;
	}
	public String getVisitRefererUrl() {
		return this.visitRefererUrl;
	}
	
	public void setVisitUrl(String visitUrl) {
		this.visitUrl = visitUrl;
	}
	public String getVisitUrl() {
		return this.visitUrl;
	}
	
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}
	public String getVisitDate() {
		return this.visitDate;
	}
	
	public void setMenuIdx(int menuIdx) {
		this.menuIdx = menuIdx;
	}
	public int getMenuIdx() {
		return this.menuIdx;
	}
	
	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	public String getSiteNm() {
		return this.siteNm;
	}
	
	public void setVisitIp(String visitIp) {
		this.visitIp = visitIp;
	}
	public String getVisitIp() {
		return this.visitIp;
	}
	
	public void setVisitCountry(String visitCountry) {
		this.visitCountry = visitCountry;
	}
	public String getVisitCountry() {
		return this.visitCountry;
	}
	
	public void setVisitCity(String visitCity) {
		this.visitCity = visitCity;
	}
	public String getVisitCity() {
		return this.visitCity;
	}
	
	public void setVisitLoc(String visitLoc) {
		this.visitLoc = visitLoc;
	}
	public String getVisitLoc() {
		return this.visitLoc;
	}
	
	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}


	public int getMobTotal() {
		return mobTotal;
	}


	public void setMobTotal(int mobTotal) {
		this.mobTotal = mobTotal;
	}


	public int getComTotal() {
		return comTotal;
	}


	public void setComTotal(int comTotal) {
		this.comTotal = comTotal;
	}


	public int getDayVisit() {
		return dayVisit;
	}


	public void setDayVisit(int dayVisit) {
		this.dayVisit = dayVisit;
	}


	public int getWeekVisit() {
		return weekVisit;
	}


	public void setWeekVisit(int weekVisit) {
		this.weekVisit = weekVisit;
	}


	public int getMonthVisit() {
		return monthVisit;
	}


	public void setMonthVisit(int monthVisit) {
		this.monthVisit = monthVisit;
	}


	public int getYearVisit() {
		return yearVisit;
	}


	public void setYearVisit(int yearVisit) {
		this.yearVisit = yearVisit;
	}


	public String getSystemType() {
		return systemType;
	}


	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

}