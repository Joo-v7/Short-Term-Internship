package kepco.common.file;

import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class FileInfo {
	
	private String origFileName;
	private String saveFileName;
	private String contentType;
	private long size;
	
	// TODO: 이런건 나중에 고민하자.
	private Map<String, String> metadata;
	
	
	public String getOrigFileName() {
		return origFileName;
	}
	public void setOrigFileName(String origFileName) {
		this.origFileName = origFileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String toString() {
		return "{" +
				"origFileName=" + origFileName +
				", saveFileName=" + saveFileName +
				", contentType=" + contentType +
				"}";
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
}
