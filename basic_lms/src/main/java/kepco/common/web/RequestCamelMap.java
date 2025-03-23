package kepco.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestCamelMap {
	
	/** 사이트 기본 파라미터만 저장 여부 */
	boolean includeSc() default true;
}