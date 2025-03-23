package kepco.cms.page;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PageVo {
	
	private int pageIdx;
	private String pageNm;
	private String pageCd;
	private String pageType;
	private String pagePath;
	private String useYn;
	private String deleteYn;
	
	@Deprecated
	private String defaultSiteYn;
	@Deprecated
	private int siteIdx;
	/** 사이트코드 */
	private String site;
	
	/** 페이지 확장자 */
	private String pageExt;
	
	@Deprecated
	private String pageHtml;
	
	/** 페이지 내용 */
	private String pageContent;
	
	private int insertIdx;
	private String insertIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	private int updateIdx;
	private String updateIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	private int deleteIdx;
	private String deleteIp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deleteDate;
	
	public String getDefaultSiteYn() {
		return defaultSiteYn;
	}
	public void setDefaultSiteYn(String defaultSiteYn) {
		this.defaultSiteYn = defaultSiteYn;
	}
	public int getPageIdx() {
		return pageIdx;
	}
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
	public String getPageNm() {
		return pageNm;
	}
	public void setPageNm(String pageNm) {
		this.pageNm = pageNm;
	}

	public String getPageCd() {
		return pageCd;
	}
	public void setPageCd(String pageCd) {
		this.pageCd = pageCd;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	public String getPagePath() {
		return pagePath;
	}
	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
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
	public String getPageExt() {
		return pageExt;
	}
	public void setPageExt(String pageExt) {
		this.pageExt = pageExt;
	}
	public String getPageHtml() {
		return pageHtml;
	}
	public void setPageHtml(String pageHtml) {
		this.pageHtml = pageHtml;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
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
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
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
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	

}
