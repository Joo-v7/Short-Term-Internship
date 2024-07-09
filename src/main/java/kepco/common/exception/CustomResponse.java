package kepco.common.exception;
import java.util.Collections;
import java.util.List;

/**
 * 
 */
@Deprecated
public class CustomResponse {
	/** 정상,오류에 대한 메시지 */
	private String message;
	/** HTTP 상태코드에 대응되는 값 */
	private int status;
	/** 성공, 실패에 대한 코드값 */
	private String code;
	/** 필드의 검증 오류 */
	private List<FieldError> errors;
	
	public static class FieldError {
		private String field;
		private String value;
		private String message;
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	public List<FieldError> getErrors() {
		if(errors == null) {
			return (List<FieldError>) Collections.EMPTY_LIST;
		}
		return errors;
	}

	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}

}