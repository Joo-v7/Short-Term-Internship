package kepco.common.json;
import java.util.Iterator;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

/**
 * 공통 JSON 포맷
 */
public class JsonResponse {
	/** 데이터 */
	private Object data = null;
	/** 정상,오류에 대한 메시지 */
	private String message = "";
	/** HTTP 상태코드에 대응되는 값 */
	private int status = 200;
	/** 성공, 실패에 대한 코드값 */
	private int code = 0;
	
	public JsonResponse(Object data) {
		this.data = data;
	}
	
	public JsonResponse(Builder builder) {
		this.data = builder.data;
		this.message = builder.message;
		this.status = builder.status;
		this.code = builder.code;
	}
	
	public class Error {
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
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	//json 출력값 구분
	public static class jsonView {
		
		public interface NORMAL{}
		  
		public interface PRIVATE{}
	}
	
	public static class Builder {
		private Object data = null;
		private int status = 200;
		private String message = "";
		private int code = 0;
		private List<Error> errors = null;
		
		public Builder data(Object data) {
			this.data = data;
			return this;
		}
		
		public Builder status(int status) {
			this.status = status;
			if(status != 200 && this.code == 0) {
				this.code = status * 10;
			}
			return this;
		}
		public Builder message(String message) {
			this.message = message;
			return this;
		}
		public Builder code(int code) {
			this.code = code;
			return this;
		}
		public Builder errors(List<Error> errors) {
			this.errors = errors;
			return this;
		}
		
		public Builder model(Model model) {
			ModelMap myData = new ModelMap();
			
//			BeanUtils.copyProperties(model, myData, "status", "code", "message");
//			jsonResponse.setData(myData);
//			jsonResponse.setCode("0000");
//			jsonResponse.setStatus(200);
//			jsonResponse.setMessage("");
			
			Iterator<String> it = model.asMap().keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(key.equals("status")) {
					this.status = (int)model.getAttribute(key);
				}
				else if(key.equals("message")) {
					this.message = (String)model.getAttribute(key);
				}
				else if(key.equals("code")) {
					this.code = (int)model.getAttribute(key);
				}
				else {
					myData.addAttribute(key, model.getAttribute(key));
				}
			}
			this.data = myData;
			
			return this;
		}
		
		public JsonResponse build() {
			return new JsonResponse(this);
		}
	}
}