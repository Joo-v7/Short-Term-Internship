package kepco.cms.sysinfoLog;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import kepco.util.StrUtil;

@Service
public class SysinfoLogService {
	
	@Autowired
	private SysinfoLogMapper sysinfoLogMapper;
	
	@Autowired
	private MetricsEndpoint metricsEndpoint;
	
	public List<SysinfoLogVo> selectList(EgovMap req) {
		
		int pageNum = StrUtil.toInt(req.get("pageNum"),1);
		int pageSize = StrUtil.toInt(req.get("pageSize"), 10);
		PageHelper.startPage(pageNum, pageSize);
		
		return sysinfoLogMapper.selectList(req);
	}

	public List<SysinfoLogVo> selectAll(EgovMap req) {
		return sysinfoLogMapper.selectList(req);
	}
	
	public SysinfoLogVo select(EgovMap req) {
		return sysinfoLogMapper.select(req);
	}
	
//	public HashMap<String, Double> avgByPeriod(EgovMap req) {
//		return sysinfoLogMapper.avgByPeriod(req);
//	}
	
	public List<SysinfoLogVo> chartList(EgovMap req) {
		return sysinfoLogMapper.chartList(req);
	}
	
	/**
	 * 메트릭스 N개 추가
	 * @param map K,V의 메트릭스명과 값
	 * @return
	 */
	@Transactional
	public long insert(HashMap<String, Double> map) {
		
		EgovMap req = new EgovMap();
		
		// 현재시각
		Date today = java.util.Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

		req.put("insertDate", today);
		
		// 맵으로 받은 메트릭스 값들을 INSERT
		map.forEach((key, val)-> {
			req.put("sysinfo_key", key);
			req.put("sysinfo_val", val);
			
			sysinfoLogMapper.insert(req);
		});
		
		
		return 1;
	}
	
	/**
	 * 메트릭스 조회 및 추가
	 */
	@Scheduled(cron = "0 * * * * ?")
	public void insertMetricsValue() {
		
		HashMap<String, Double> map = new HashMap<>();
		
		double val;
		
		// "description": "The uptime of the Java virtual machine",
		// "baseUnit": "seconds",
//		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.uptime", null).getMeasurements().get(0)).getValue();
//		map.put("process.uptime", val);
		
		// "description": "The amount of used memory",
		// "baseUnit": "bytes",
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", List.of("area:heap")).getMeasurements().get(0)).getValue();
		map.put("jvm.memory.used-heap", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", List.of("area:nonheap")).getMeasurements().get(0)).getValue();
		map.put("jvm.memory.used-nonheap", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", null).getMeasurements().get(0)).getValue();
		map.put("jvm.memory.used", val);
		// description": "The \"recent cpu usage\" for the Java Virtual Machine process",
		// "baseUnit": null,
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0)).getValue();
		map.put("process.cpu.usage", val);
		
		// "description": "The \"recent cpu usage\" of the system the application is running in",
		// "baseUnit": null,
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("system.cpu.usage", null).getMeasurements().get(0)).getValue();
		map.put("system.cpu.usage", val);
		
		// "description": null,
		// "baseUnit": "sessions",
		// 임베디드 톰캣이 아니면 존재하지 않음. 삭제.
//		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.sessions.active.current", null).getMeasurements().get(0)).getValue();
//		map.put("tomcat.sessions.active.current", val);
		
		// "description": 
		// "baseUnit": "connections"
		if(metricsEndpoint.metric("tomcat.connections.current", null) != null) {
			val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.connections.current", null).getMeasurements().get(0)).getValue();
			map.put("tomcat.connections.current", val);
		}
		
		// "description": "Active connections",
		// "baseUnit": null,
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("hikaricp.connections.active", null).getMeasurements().get(0)).getValue();
		map.put("hikaricp.connections.active", val);
		
		insert(map);
		
		return;
	}
	
	/**
	 * 오래된 데이터(1달 경과) 삭제
	 */
	@Scheduled(cron = "0 0 1 * * ?")
	public void deleteOldData() {
		
		EgovMap req = new EgovMap();
		long ret = sysinfoLogMapper.deleteOldData(req);
	}
	
	/**
	 * 현재시점의 메트릭스 값 조회
	 * @param req
	 * @return
	 */
	public HashMap<String, Double> currMetrics(EgovMap req) {
		
		HashMap<String, Double> map = new HashMap<>();
		
		double val;
		
		// The amount of used memory (byte)
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.used", null).getMeasurements().get(0)).getValue();
		map.put("jvmMemoryUsed", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("jvm.memory.max", null).getMeasurements().get(0)).getValue();
		map.put("jvmMemoryMax", val);
		
		// Usable space for path (byte)
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("disk.free", null).getMeasurements().get(0)).getValue();
		map.put("diskFree", val);
		
		// Total space for path (byte)
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("disk.total", null).getMeasurements().get(0)).getValue();
		map.put("diskTotal", val);
		
		// Time taken to start the application (sec) 
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("application.started.time", null).getMeasurements().get(0)).getValue();
		map.put("applicationStartedTime", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("http.server.requests", null).getMeasurements().get(0)).getValue();
		map.put("httpServerRequestCount", val);
		
		// The uptime of the Java virtual machine (sec)
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.uptime", null).getMeasurements().get(0)).getValue();
		map.put("processUptime", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0)).getValue();
		map.put("processCpuUsage", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("system.cpu.count", null).getMeasurements().get(0)).getValue();
		map.put("systemCpuCount", val);
		
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("system.cpu.usage", null).getMeasurements().get(0)).getValue();
		map.put("systemCpuUsage", val);
		
		
		// 로컬(임베디드)과 서버 구분
		if(metricsEndpoint.metric("tomcat.connections.current", null) != null) {
			val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.connections.current", null).getMeasurements().get(0)).getValue();
			map.put("tomcatConnectionsCurrent", val);
		} else if(metricsEndpoint.metric("tomcat.sessions.active.current", null) != null) {
			val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("tomcat.sessions.active.current", null).getMeasurements().get(0)).getValue();
			map.put("tomcatConnectionsCurrent", val);
		} else {
			map.put("tomcatConnectionsCurrent", null);
		}
		
		// Connection usage time"
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("hikaricp.connections.usage", null).getMeasurements().get(0)).getValue();
		map.put("hikaricpConnectionsUsageCount", val);
		val = ((MetricsEndpoint.Sample)metricsEndpoint.metric("hikaricp.connections.usage", null).getMeasurements().get(1)).getValue();
		map.put("hikaricpConnectionsUsageTotalTime", val);
		
		return map;
	}
}
