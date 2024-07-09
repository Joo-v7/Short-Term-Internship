package kepco.cms.layout;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class LayoutVo {

	private int layoutIdx;
	private int siteIdx;
	private String layoutNm;
	private String layoutCd;
	private String layoutContent;
	private String layoutPath;
	private String site;
	
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
	private String deleteYn;
	
	public int getLayoutIdx() {
		return layoutIdx;
	}
	public void setLayoutIdx(int layoutIdx) {
		this.layoutIdx = layoutIdx;
	}
	public int getSiteIdx() {
		return siteIdx;
	}
	public void setSiteIdx(int siteIdx) {
		this.siteIdx = siteIdx;
	}
	public String getLayoutNm() {
		return layoutNm;
	}
	public void setLayoutNm(String layoutNm) {
		this.layoutNm = layoutNm;
	}
	public String getLayoutCd() {
		return layoutCd;
	}
	public void setLayoutCd(String layoutCd) {
		this.layoutCd = layoutCd;
	}
	public String getLayoutContent() {
		return layoutContent;
	}
	public void setLayoutContent(String layoutContent) {
		this.layoutContent = layoutContent;
	}
	public String getLayoutPath() {
		return layoutPath;
	}
	public void setLayoutPath(String layoutPath) {
		this.layoutPath = layoutPath;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
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
	public String getDeleteYn() {
		return deleteYn;
	}
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	
	
	
}
