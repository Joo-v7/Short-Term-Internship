package kepco.cms.zzz.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.stereotype.Component;

import kepco.util.CamelMap;

@Component("myPermissionChecker")
public class MyPermissionChecker {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public boolean myCmd(CamelMap req) {
		
		if( req.getInt("myId") == 1) {
			return true;
		}
		
		return false;
	}
}
