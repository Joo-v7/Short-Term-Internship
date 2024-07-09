package kepco.lms.vod.bundle;

import org.springframework.stereotype.Component;

import kepco.lms.vod.content.ContentVo;

@Component
public class BundleVo extends ContentVo {

	private int bundleIdx;
	private int vodIdx;
	private int contentIdx;
	private int contentOrder;
	
	private ContentVo contentVo;
	private String trainState;
	
	private int rn;
	private int insertIdx;
	private String insertIp;
	private String insertDate;
	
	public String getTrainState() {
		return trainState;
	}
	public void setTrainState(String trainState) {
		this.trainState = trainState;
	}
	public ContentVo getContentVo() {
		return contentVo;
	}
	public void setContentVo(ContentVo contentVo) {
		this.contentVo = contentVo;
	}
	public int getBundleIdx() {
		return bundleIdx;
	}
	public void setBundleIdx(int bundleIdx) {
		this.bundleIdx = bundleIdx;
	}
	public int getEduIdx() {
		return vodIdx;
	}
	public void setEduIdx(int vodIdx) {
		this.vodIdx = vodIdx;
	}
	public int getContentIdx() {
		return contentIdx;
	}
	public void setContentIdx(int contentIdx) {
		this.contentIdx = contentIdx;
	}
	public int getContentOrder() {
		return contentOrder;
	}
	public void setContentOrder(int contentOrder) {
		this.contentOrder = contentOrder;
	}
	public int getRn() {
		return rn;
	}
	public void setRn(int rn) {
		this.rn = rn;
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