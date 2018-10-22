package cc.mrbird.job.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.JobLog;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "JobLogService-")
public interface JobLogService extends IService<JobLog>{
	@Cacheable(key = "'findAllJobLogs-'+#p0.toString()")
	List<JobLog> findAllJobLogs(JobLog jobLog);
	@CacheEvict(allEntries = true)
	void saveJobLog(JobLog log);
	@CacheEvict(allEntries = true)
	void deleteBatch(String jobLogIds);
}
