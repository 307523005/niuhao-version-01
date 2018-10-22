package cc.mrbird.job.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.Cron;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;


@CacheConfig(cacheNames = "CronService")
public interface CronService extends IService<Cron> {

    PageInfo<Cron> PageList(QueryRequest request, Cron cron);
}
