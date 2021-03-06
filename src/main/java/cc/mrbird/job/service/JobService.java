package cc.mrbird.job.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.Job;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "JobService")
public interface JobService extends IService<Job> {
    @Cacheable(key = "'findJob-'+#p0")
    Job findJob(Long jobId);

    @Cacheable(key = "'findJobByParams-'+#p0")
    Job findJobByParams(String params);

    @Cacheable(key = "'findAllJobs-'+#p0.toString()")
    List<Job> findAllJobs(Job job);

    @CacheEvict(allEntries = true)
    void addJob(Job job);

    @CacheEvict(allEntries = true)
    void updateJob(Job job);

    @CacheEvict(allEntries = true)
    void deleteBatch(String jobIds);

    @CacheEvict(allEntries = true)
    int updateBatch(String jobIds, String status);

    void run(String jobIds);

    @CacheEvict(allEntries = true)
    void pause(String jobIds);

    @CacheEvict(allEntries = true)
    void resume(String jobIds);

    @Cacheable(key = "'getSysCronClazz-'+#p0.toString()")
    List<Job> getSysCronClazz(Job job);
}
