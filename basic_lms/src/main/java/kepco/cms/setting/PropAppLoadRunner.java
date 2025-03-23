package kepco.cms.setting;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.groovy.util.BeanUtils;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import kepco.common.web.RequestEgovMap;
import kepco.util.StrUtil;
/**
 * 환경설정
 */
@Component
public class PropAppLoadRunner implements ApplicationRunner {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PropAppService propAppService;
	
	@Override
	public void run(ApplicationArguments args) {
		propAppService.loadPropApp();
	}

}