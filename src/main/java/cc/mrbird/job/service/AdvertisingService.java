package cc.mrbird.job.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.Advertising;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "AdvertisingService")
public interface AdvertisingService extends IService<Advertising> {
    @Cacheable(key = "'findAdvertising'+#p0")
    Advertising findAdvertising(Long advertising_id);
    List<Advertising> findAllAdvertising(QueryRequest request, Advertising advertising);
    @Cacheable(key = "'PageList'+#request.toString() + #advertising.toString()")
    PageInfo<Advertising> PageList(QueryRequest request, Advertising advertising);
    @CacheEvict(allEntries = true)
    void addAdvertising(Advertising advertising);

    @CacheEvict(allEntries = true)
    void deleteAdvertising(String advertisingid);

    @CacheEvict(allEntries = true)
    void updateAdvertising(Advertising advertising);

    List<Advertising> scappGetAdvertisingByMerchant_id(String merchant_id);
}
