package kepco.cms.email;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailVo {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/** 발신자 */
	private String from;
	/** 수신자 */
	private String to;
	/** 제목 */
	private String subject;
	/** 내용 */
	private String text;
	/** 내용 HTML 여부 */
	private boolean useHtml;
	/** 내용 템플릿(Thymeleaf) 여부 */
	private boolean useTemplate;
	/** 템플릿 변수 */
	private Map<String, Object> templateMap;
	/** 첨부파일 */
	private LinkedHashMap<String, Object> attachMap;
	/** 인라인 */
	private LinkedHashMap<String, Object> inlineMap;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isUseHtml() {
		return useHtml;
	}
	public void setUseHtml(boolean useHtml) {
		this.useHtml = useHtml;
	}
	public boolean isUseTemplate() {
		return useTemplate;
	}
	public void setUseTemplate(boolean useTemplate) {
		this.useTemplate = useTemplate;
	}
	public Map<String, Object> getTemplateMap() {
		return templateMap;
	}
	public void setTemplateMap(Map<String, Object> templateMap) {
		this.templateMap = templateMap;
	}
	public LinkedHashMap<String, Object> getAttachMap() {
		return attachMap;
	}
	public void setAttachMap(LinkedHashMap<String, Object> attachMap) {
		this.attachMap = attachMap;
	}
	public LinkedHashMap<String, Object> getInlineMap() {
		return inlineMap;
	}
	public void setInlineMap(LinkedHashMap<String, Object> inlineMap) {
		this.inlineMap = inlineMap;
	}
	
	
}
