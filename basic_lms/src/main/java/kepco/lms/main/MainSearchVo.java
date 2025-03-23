package kepco.lms.main;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import kepco.common.json.JsonResponse.jsonView;

@Component
public class MainSearchVo {
	
	private int scHistoryIdx;
	private String scWord;
	private String scType;
	
	@JsonView(jsonView.PRIVATE.class)
	private int insertIdx;
	@JsonView(jsonView.PRIVATE.class)
	private String insertIp;
	@JsonView(jsonView.PRIVATE.class)
	private Date insertDate;
	public int getScHistoryIdx() {
		return scHistoryIdx;
	}
	public void setScHistoryIdx(int scHistoryIdx) {
		this.scHistoryIdx = scHistoryIdx;
	}
	public String getScWord() {
		return scWord;
	}
	public void setScWord(String scWord) {
		this.scWord = scWord;
	}
	public String getScType() {
		return scType;
	}
	public void setScType(String scType) {
		this.scType = scType;
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
}
