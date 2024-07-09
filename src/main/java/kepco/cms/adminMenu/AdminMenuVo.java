package kepco.cms.adminMenu;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 관리자 메뉴 */
@Component
public class AdminMenuVo {

	/** 메뉴ID */
	private int menuIdx;
	
	/** 사이트코드 */
	@NotBlank
	@Max(20)
	private String site;
	
	/** 상위메뉴ID */
	@NotNull
	private int parentMenuIdx;
	
	/** 메뉴 깊이 */
	@NotNull
	private int menuDepth;
	
	/** 메뉴 순서 */
	@NotNull
	private int menuOrder;
	
	/** 메뉴코드 */
	@Size(max=50)
	private String mn;
	
	/** 메뉴명 */
	@NotBlank
	@Size(max=100)
	private String menuNm;
	
	/** 메뉴 주소 (URL인코딩 전) */
	@Size(max=200)
	private String menuUrl;
	
	/** 메뉴 아이콘 */
	@Size(max=50)
	private String menuIco;
	
	/** 메뉴 종류 (없음, 모듈, 페이지, 리디렉션) */
	@Size(max=8)
	private String menuType;
	
	/** 사용여부 */
	@NotBlank
	@Size(min=1, max=1)
	private String useYn;
	
	/** 등록IDX */
	private int insertIdx;
	
	/** 등록IP */
	@Size(max=20)
	private String insertIp;
	
	/** 등록일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	/** 수정IDX */
	private int updateIdx;
	
	/** 수정IP */
	@Size(max=20)
	private String updateIp;
	
	/** 수정일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	/** 삭제IDX */
	private int deleteIdx;
	
	/** 삭제IP */
	@Size(max=20)
	private String deleteIp;
	
	/** 삭제일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deleteDate;
	
	/** 삭제여부 */
	@Size(min=1, max=1)
	private String deleteYn;
	
	
	/** 부모 메뉴명 */
	private String parentMenuNm;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	
	public void setMenuIdx(int menuIdx) {
		this.menuIdx = menuIdx;
	}
	public int getMenuIdx() {
		return this.menuIdx;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	public String getSite() {
		return this.site;
	}
	
	public void setParentMenuIdx(int parentMenuIdx) {
		this.parentMenuIdx = parentMenuIdx;
	}
	public int getParentMenuIdx() {
		return this.parentMenuIdx;
	}
	
	public void setMenuDepth(int menuDepth) {
		this.menuDepth = menuDepth;
	}
	public int getMenuDepth() {
		return this.menuDepth;
	}
	
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public int getMenuOrder() {
		return this.menuOrder;
	}
	
	public void setMn(String mn) {
		this.mn = mn;
	}
	public String getMn() {
		return this.mn;
	}
	
	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}
	public String getMenuNm() {
		return this.menuNm;
	}
	
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuUrl() {
		return this.menuUrl;
	}
	
	public void setMenuIco(String menuIco) {
		this.menuIco = menuIco;
	}
	public String getMenuIco() {
		return this.menuIco;
	}
	
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getMenuType() {
		return this.menuType;
	}
	
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getUseYn() {
		return this.useYn;
	}
	
	public void setInsertIdx(int insertIdx) {
		this.insertIdx = insertIdx;
	}
	public int getInsertIdx() {
		return this.insertIdx;
	}
	
	public void setInsertIp(String insertIp) {
		this.insertIp = insertIp;
	}
	public String getInsertIp() {
		return this.insertIp;
	}
	
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public Date getInsertDate() {
		return this.insertDate;
	}
	
	public void setUpdateIdx(int updateIdx) {
		this.updateIdx = updateIdx;
	}
	public int getUpdateIdx() {
		return this.updateIdx;
	}
	
	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}
	public String getUpdateIp() {
		return this.updateIp;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return this.updateDate;
	}
	
	public void setDeleteIdx(int deleteIdx) {
		this.deleteIdx = deleteIdx;
	}
	public int getDeleteIdx() {
		return this.deleteIdx;
	}
	
	public void setDeleteIp(String deleteIp) {
		this.deleteIp = deleteIp;
	}
	public String getDeleteIp() {
		return this.deleteIp;
	}
	
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public Date getDeleteDate() {
		return this.deleteDate;
	}
	
	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
	}
	public String getDeleteYn() {
		return this.deleteYn;
	}


	public String getParentMenuNm() {
		return parentMenuNm;
	}


	public void setParentMenuNm(String parentMenuNm) {
		this.parentMenuNm = parentMenuNm;
	}
	
}