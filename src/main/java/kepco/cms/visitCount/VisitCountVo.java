package kepco.cms.visitCount;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 사이트접속 */
@Component
public class VisitCountVo {

    /** 사이트접속IDX */
    private int visitIdx;
    
    /** 세션 */
    @NotBlank
    @Size(max = 255)
    private String visitSessionId;
    
    /** 브라우저 */
    @NotBlank
    @Size(max = 255)
    private String visitBrowser;
    
    /** 운영체제 */
    @NotBlank
    @Size(max = 255)
    private String visitOs;
    
    /** 기기 */
    @NotBlank
    @Size(max = 50)
    private String visitDevice;
    
    /** 유저에이전트 */
    @Size(max = 255)
    private String visitUserAgent;
    
    /** 주소 */
    @Size(max = 255)
    private String visitUrl;
    
    /** 접속일시 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitDate;
    
    /** IP주소 */
    @Size(max = 255)
    private String visitIp;
    
    /** 접속일시 연도 */
    private int visitYear;
    
    /** 접속일시 월 */
    @Size(max = 10)
    private String visitMonth;
    
    /** 접속일시 일 */
    private int visitDay;
    
    /** 접속 리퍼러 */
    @Size(max = 255)
    private String visitReferer;
    
    
    
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
    
    public void setVisitDevice(String visitDevice) {
        this.visitDevice = visitDevice;
    }
    public String getVisitDevice() {
        return this.visitDevice;
    }
    
    public void setVisitUserAgent(String visitUserAgent) {
        this.visitUserAgent = visitUserAgent;
    }
    public String getVisitUserAgent() {
        return this.visitUserAgent;
    }
    
    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }
    public String getVisitUrl() {
        return this.visitUrl;
    }
    
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    public Date getVisitDate() {
        return this.visitDate;
    }
    
    public void setVisitIp(String visitIp) {
        this.visitIp = visitIp;
    }
    public String getVisitIp() {
        return this.visitIp;
    }
    
    public void setVisitYear(int visitYear) {
        this.visitYear = visitYear;
    }
    public int getVisitYear() {
        return this.visitYear;
    }
    
    public void setVisitMonth(String visitMonth) {
        this.visitMonth = visitMonth;
    }
    public String getVisitMonth() {
        return this.visitMonth;
    }
    
    public void setVisitDay(int visitDay) {
        this.visitDay = visitDay;
    }
    public int getVisitDay() {
        return this.visitDay;
    }
    
    public void setVisitReferer(String visitReferer) {
        this.visitReferer = visitReferer;
    }
    public String getVisitReferer() {
        return this.visitReferer;
    }
    
}