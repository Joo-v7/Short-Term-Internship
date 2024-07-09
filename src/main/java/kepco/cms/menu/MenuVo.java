package kepco.cms.menu;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

/** 메뉴트리구조 */
@Component
public class MenuVo {
	
	/** 메뉴ID */
	private int menuIdx;
	
	/** 사이트명 */
	@NotNull
	private String site;
	
	/** 상위메뉴ID */
	@NotNull
	private int parentMenuIdx;
	
	/** 메뉴코드 */
	@NotNull
	private String mn;
	
	/** 메뉴명 */
	@NotNull
	private String menuNm;
	
	/** 메뉴 주소 (URL인코딩 전) */
	private String menuUrl;
	
	/** 메뉴 종류 (없음, 모듈, 페이지, 리디렉션) */
	private String menuType;
	
	/** 사용여부 */
	private String useYn;
	
	/** 레이아웃ID */
	private int layoutIdx;
	
	/** 연결모듈ID */
	@Deprecated
	private String moduelId;
	
	/** 모듈명 */
	@Deprecated
	private String menuModule;
	
	/** 윗글 HTML */
	private String menuHeadHtml;
	
	/** 아랫글 HTML  */
	private String menuTailHtml;
	
	/** 페이지ID */
	private int pageIdx;
	
	/**  */
	@Deprecated
	private String pageName;
	
	/** 등록IDX */
	private int insertIdx;
	
	/** 등록IP */
	private String insertIp;
	
	/** 등록일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date insertDate;
	
	/** 수정IDX */
	private int updateIdx;
	
	/** 수정IP */
	private String updateIp;
	
	/** 수정일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	/** 삭제IDX */
	private int deleteIdx;
	
	/** 삭제IP */
	private String deleteIp;
	
	/** 삭제일시 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deleteDate;
	
	/** 삭제여부 */
	private String deleteYn;
	
	/** 메뉴순서 */
	private int menuOrder;
	
	/** 메뉴깊이 */
	private int menuDepth;
	
	@Deprecated
	private String menuTitle;
	
	/** 부모 메뉴명 */
	private String parentMenuNm;
	
	/** 레이아웃명 */
	private String layoutNm;
	
	/** 페이지명 */
	private String pageNm;
	
	private String menuIco;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public String getMenuIco() {
		return menuIco;
	}

	public void setMenuIco(String menuIco) {
		this.menuIco = menuIco;
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
	
	public void setLayoutIdx(int layoutIdx) {
		this.layoutIdx = layoutIdx;
	}
	public int getLayoutIdx() {
		return this.layoutIdx;
	}
	
	public void setModuelId(String moduelId) {
		this.moduelId = moduelId;
	}
	public String getModuelId() {
		return this.moduelId;
	}
	
	public void setMenuModule(String menuModule) {
		this.menuModule = menuModule;
	}
	public String getMenuModule() {
		return this.menuModule;
	}
	
	public void setMenuHeadHtml(String menuHeadHtml) {
		this.menuHeadHtml = menuHeadHtml;
	}
	public String getMenuHeadHtml() {
		return this.menuHeadHtml;
	}
	
	public void setMenuTailHtml(String menuTailHtml) {
		this.menuTailHtml = menuTailHtml;
	}
	public String getMenuTailHtml() {
		return this.menuTailHtml;
	}
	
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
	public int getPageIdx() {
		return this.pageIdx;
	}
	
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageName() {
		return this.pageName;
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
	
	public void setUpdateIdx(int string) {
		this.updateIdx = string;
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
	
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public int getMenuOrder() {
		return this.menuOrder;
	}
	
	public void setMenuDepth(int menuDepth) {
		this.menuDepth = menuDepth;
	}
	public int getMenuDepth() {
		return this.menuDepth;
	}
	
	public String getParentMenuNm() {
		return parentMenuNm;
	}
	public void setParentMenuNm(String parentMenuNm) {
		this.parentMenuNm = parentMenuNm;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
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