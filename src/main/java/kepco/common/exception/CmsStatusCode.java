package kepco.common.exception;

import org.springframework.lang.Nullable;

public enum CmsStatusCode {
	// org.springframework.http.HttpStatus 를 전부 차용하는 건 그렇고 천천히 고민하는 걸로...
	
	OK(200, "정상적으로 처리되었습니다."),
	BAD_REQUEST(400, "잘못된 요청입니다."),
	UNAUTHORIZED(401, "사용자 인증이 필요합니다."),
	FORBIDDEN(403, "권한,인증 실패로 처리할 수 없습니다."),
	NOT_FOUND(404, "데이터가 존재하지 않습니다."),
	CONFLICT(409, "시스템과 충돌되는 데이터 입니다."),
	INTERNAL_SERVER_ERROR(500, "시스템 오류로 처리할 수 없습니다."),
	SERVICE_UNAVAILABLE(503, "서비스를 이용할 수 없습니다.");
	
	private static final CmsStatusCode[] VALUES;

	static {
		VALUES = values();
	}
	
	private final int value;
	
	private final String message;
	
	CmsStatusCode(int value, String message) {
		this.value = value;
		this.message = message;
	}
	
	public int value() {
		return this.value;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	@Nullable
	public static CmsStatusCode resolve(int value) {
		for (CmsStatusCode cmsStatusCode : VALUES) {
			if (cmsStatusCode.value == value) {
				return cmsStatusCode;
			}
		}
		return null;
	}
	
	public static CmsStatusCode valueOf(int value) {
		CmsStatusCode cmsStatusCode = resolve(value);
		if (cmsStatusCode == null) {
			throw new IllegalArgumentException("No matching constant for [" + value + "]");
		}
		return cmsStatusCode;
	}
	
	@Override
	public String toString() {
		return this.value + " " + name();
	}
}
