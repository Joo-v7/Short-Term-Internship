package kepco.cms.code;

import org.springframework.stereotype.Component;

@Component
public class CodeVo {
	
	private String code;
	private String codeNm;
	private String codeParent;
	private int codeDepth;
	private int codeOrder;
	private String useYn;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getCodeParent() {
		return codeParent;
	}
	public void setCodeParent(String codeParent) {
		this.codeParent = codeParent;
	}
	public int getCodeDepth() {
		return codeDepth;
	}
	public void setCodeDepth(int codeDepth) {
		this.codeDepth = codeDepth;
	}
	public int getCodeOrder() {
		return codeOrder;
	}
	public void setCodeOrder(int codeOrder) {
		this.codeOrder = codeOrder;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
}