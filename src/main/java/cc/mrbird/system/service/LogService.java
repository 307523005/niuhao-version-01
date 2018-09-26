package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.SysLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
@CacheConfig(cacheNames = "LogService")
public interface LogService extends IService<SysLog> {
	@Cacheable(key = "'findAllLogs'+#log.toString()")
	List<SysLog> findAllLogs(SysLog log);
	@CacheEvict(allEntries = true)
	void deleteLogs(String logIds);

	@Async
	@CacheEvict(allEntries = true)
	void saveLog(ProceedingJoinPoint point, long time, String ip,String name) throws JsonProcessingException;
}
