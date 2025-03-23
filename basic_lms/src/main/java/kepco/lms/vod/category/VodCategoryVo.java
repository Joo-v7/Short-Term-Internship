package kepco.lms.vod.category;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class VodCategoryVo {
	/** 모듈IDX */
	private int vodCategoryIdx;
	
	private String categoryNm;
	
	private int categoryOrder;
	
	private String useYn;
	
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private String insertDate;
	@JsonView(jsonView.PRIVATE.class)
	private int updateIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String updateIp;
	@JsonView(jsonView.PRIVATE.class)
	private String updateDate;
	@JsonView(jsonView.PRIVATE.class)
	private int deleteIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteIp;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteDate;
	@JsonView(jsonView.PRIVATE.class)
	private String deleteYn;
	
	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public int getVodCategoryIdx() {
		return vodCategoryIdx;
	}

	public void setVodCategoryIdx(int vodCategoryIdx) {
		this.vodCategoryIdx = vodCategoryIdx;
	}

	public String getCategoryNm() {
		return categoryNm;
	}

	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}

	public int getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(int categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public String getDeleteYn() {
		return deleteYn;
	}

	public void setDeleteYn(String deleteYn) {
		this.deleteYn = deleteYn;
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
	
}