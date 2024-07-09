package kepco.cms.sysinfoLog;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

@Component
public class SysinfoLogVo {
	
	/** 로그IDX */
	private long sysinfoLogIdx;
	
	/** 수집시각 */
	@JsonFormat(pattern = "HH:mm", timezone = "Asia/Seoul")
	private Date insertDate;
	
	/** 데이터 키 */
	private String sysinfoKey;
	
	/** 데이터 값 */
	private Double sysinfoVal;
	
	/** jvm.memory.used-heap 값*/
	private Double jmuhVal;
	/** jvm.memory.used-nonheap 값*/
	private Double jmunhVal;
	/** jvm.memory.used 값*/
	private Double jmuVal;
	/** tomcat.connections.current 값*/
	private Double tccVal;
	/** hikaricp.connections.active 값*/
	private Double hcaVal;
	/** 데이터 순서*/
	private int rowNum;
	
	public Double getJmuhVal() {
		return jmuhVal;
	}

	public void setJmuhVal(Double jmuhVal) {
		this.jmuhVal = jmuhVal;
	}

	public Double getJmunhVal() {
		return jmunhVal;
	}

	public void setJmunhVal(Double jmunhVal) {
		this.jmunhVal = jmunhVal;
	}

	public Double getJmuVal() {
		return jmuVal;
	}

	public void setJmuVal(Double jmuVal) {
		this.jmuVal = jmuVal;
	}

	public Double getTccVal() {
		return tccVal;
	}

	public void setTccVal(Double tccVal) {
		this.tccVal = tccVal;
	}

	public Double getHcaVal() {
		return hcaVal;
	}

	public void setHcaVal(Double hcaVal) {
		this.hcaVal = hcaVal;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public long getSysinfoLogIdx() {
		return sysinfoLogIdx;
	}

	public void setSysinfoLogIdx(long sysinfoLogIdx) {
		this.sysinfoLogIdx = sysinfoLogIdx;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public String getSysinfoKey() {
		return sysinfoKey;
	}

	public void setSysinfoKey(String sysinfoKey) {
		this.sysinfoKey = sysinfoKey;
	}

	public Double getSysinfoVal() {
		return sysinfoVal;
	}

	public void setSysinfoVal(Double sysinfoVal) {
		this.sysinfoVal = sysinfoVal;
	}
	
}