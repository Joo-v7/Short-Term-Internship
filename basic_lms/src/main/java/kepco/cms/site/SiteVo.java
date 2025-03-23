package kepco.cms.site;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class SiteVo {
	
	private int siteIdx;
	private String site;
	private String siteNm;
	private String defaultSiteYn;
	private String domain;
	private String homeTitle;
	private int layoutIdx;
	private int pageIdx;
	
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	private int updateIdx;
	private String updateIp;
	private String updateDate;
	private int deleteIdx;
	private String deleteIp;
	private String deleteDate;
	private String deleteYn;
	
	/** 메인 레이아웃 */
	private String layoutNm;
	/** 메인 페이지 */
	private String pageNm;
	
	public int getSiteIdx() {
		return siteIdx;
	}
	public void setSiteIdx(int siteIdx) {
		this.siteIdx = siteIdx;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSiteNm() {
		return siteNm;
	}
	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	public String getDefaultSiteYn() {
		return defaultSiteYn;
	}
	public void setDefaultSiteYn(String defaultSiteYn) {
		this.defaultSiteYn = defaultSiteYn;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getHomeTitle() {
		return homeTitle;
	}
	public void setHomeTitle(String homeTitle) {
		this.homeTitle = homeTitle;
	}
	public int getLayoutIdx() {
		return layoutIdx;
	}
	public void setLayoutIdx(int layoutIdx) {
		this.layoutIdx = layoutIdx;
	}
	public int getPageIdx() {
		return pageIdx;
	}
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
	public int getInsertIdx() {
		return insertIdx;
	}
	public void setInsertIdx(int insertIdx) {
		this.insertIdx = insertIdx;
	}
	public String getInsertIp() {
		return insertIp;
	}
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public int getUpdateIdx() {
		return updateIdx;
	}
	public void setUpdateIdx(int updateIdx) {
		this.updateIdx = updateIdx;
	}
	public String getUpdateIp() {
		return updateIp;
	}
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getDeleteIdx() {
		return deleteIdx;
	}
	public void setDeleteIdx(int deleteIdx) {
		this.deleteIdx = deleteIdx;
	}
	public String getDeleteIp() {
		return deleteIp;
	}
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(String deleteDate) {
		this.deleteDate = deleteDate;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getLayoutNm() {
		return layoutNm;
	}
	public void setLayoutNm(String layoutNm) {
		this.layoutNm = layoutNm;
	}
	public String getPageNm() {
		return pageNm;
	}
	public void setPageNm(String pageNm) {
		this.pageNm = pageNm;
	}
	
}
