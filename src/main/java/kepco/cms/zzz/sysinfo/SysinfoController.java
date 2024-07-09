package kepco.cms.zzz.sysinfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "cms/zzz/sysinfo")
public class SysinfoController {
	
	@Autowired
	private MetricsEndpoint metricsEndpoint;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "metrics")
	@ResponseStatus(value = HttpStatus.OK)
	public void metrics() {
		
		// /actuator/metrics 의 값 확인
		double val;
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.uptime", null).getMeasurements().get(0)).getValue();
		log.info("process.uptime\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", null).getMeasurements().get(0)).getValue();
		log.info("jvm.memory.used\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", List.of("area:heap", "id:G1 Survivor Space")).getMeasurements().get(0)).getValue();
		log.info("jvm.memory.used\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("disk.free", null).getMeasurements().get(0)).getValue();
		log.info("disk.free\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("disk.total", null).getMeasurements().get(0)).getValue();
		log.info("disk.total\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("hikaricp.connections.active", null).getMeasurements().get(0)).getValue();
		log.info("hikaricp.connections.active\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("system.cpu.usage", null).getMeasurements().get(0)).getValue();
		log.info("system.cpu.usage\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0)).getValue();
		log.info("process.cpu.usage\t: " + new BigDecimal(val).toString());
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.sessions.active.current", null).getMeasurements().get(0)).getValue();
		log.info("tomcat.sessions.active.current\t: " + new BigDecimal(val).toString());
		if(metricsEndpoint.metric("tomcat.connections.current", null) != null) {
			val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.connections.current", null).getMeasurements().get(0)).getValue();
			log.info("tomcat.sessions.active.current\t: " + new BigDecimal(val).toString());
		}
	}
	

}
