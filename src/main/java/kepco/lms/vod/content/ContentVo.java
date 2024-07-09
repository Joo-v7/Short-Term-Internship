package kepco.lms.vod.content;

import org.springframework.stereotype.Component;

@Component
public class ContentVo {

	private int contentIdx;
	private int vodIdx;
	private int vodCategoryIdx;
	private String contentTitle;
	private String contentFileName;
	private String contentFileOrigin;
	private String contentDesc;
	private int contentTime;
	
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
	private String useYn;
	
	private String insertNm;
	private String updateNm;
	
	private String categoryNm;
	private int studyTime;
	
	public int getStudyTime() {
		return studyTime;
	}
	public void setStudyTime(int studyTime) {
		this.studyTime = studyTime;
	}
	public String getCategoryNm() {
		return categoryNm;
	}
	public void setCategoryNm(String categoryNm) {
		this.categoryNm = categoryNm;
	}
	public String getInsertNm() {
		return insertNm;
	}
	public void setInsertNm(String insertNm) {
		this.insertNm = insertNm;
	}
	public String getUpdateNm() {
		return updateNm;
	}
	public void setUpdateNm(String updateNm) {
		this.updateNm = updateNm;
	}
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
	public String getContentDesc() {
		return contentDesc;
	}
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	public int getContentTime() {
		return contentTime;
	}
	public void setContentTime(int contentTime) {
		this.contentTime = contentTime;
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
	public int getContentIdx() {
		return contentIdx;
	}
	public void setContentIdx(int contentIdx) {
		this.contentIdx = contentIdx;
	}
	public int getVodIdx() {
		return vodIdx;
	}
	public void setVodIdx(int vodIdx) {
		this.vodIdx = vodIdx;
	}
	public String getContentTitle() {
		return contentTitle;
	}
	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}
	public String getContentFileName() {
		return contentFileName;
	}
	public void setContentFileName(String contentFileName) {
		this.contentFileName = contentFileName;
	}
	public String getContentFileOrigin() {
		return contentFileOrigin;
	}
	public void setContentFileOrigin(String contentFileOrigin) {
		this.contentFileOrigin = contentFileOrigin;
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
}