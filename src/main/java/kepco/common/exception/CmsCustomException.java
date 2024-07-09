package kepco.common.exception;

public class CmsCustomException extends RuntimeException {
	private CmsStatusCode cmsStatusCode;
	private int code = 0;
	private String message = "";
	
	public CmsCustomException(CmsStatusCode cmsStatusCode) {
		super(cmsStatusCode.getMessage());
		
		// TODO: 상세한 건 나중에 처리하자.
		this.cmsStatusCode = cmsStatusCode;
		if(cmsStatusCode.value() != 200) {
			code = cmsStatusCode.value() * 10;
		}
		this.message = cmsStatusCode.getMessage();
	}
	
	public CmsCustomException(CmsStatusCode cmsStatusCode, String message) {
		super(cmsStatusCode.getMessage());
		this.cmsStatusCode = cmsStatusCode;
		if(cmsStatusCode.value() != 200) {
			code = cmsStatusCode.value() * 10;
		}
		this.message = message;
	}
	
	public CmsCustomException(CmsStatusCode cmsStatusCode, int code, String message) {
		this.cmsStatusCode = cmsStatusCode;
		this.code = code;
		this.message = message;
	}
	
	public CmsStatusCode getCmsStatusCode() {
		return this.cmsStatusCode;
	}
	
	public int code() {
		return this.code;
	}
	
	public String getMessage() {
		return this.message;
	}
}