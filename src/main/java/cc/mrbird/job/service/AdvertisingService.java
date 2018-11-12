package cc.mrbird.job.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.Advertising;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


@CacheConfig(cacheNames = "AdvertisingService")
public interface AdvertisingService extends IService<Advertising> {
    @Cacheable(key = "'findAdvertising-'+#p0")
    Advertising findAdvertising(Long advertising_id);

    @Cacheable(key = "'findAllAdvertising-'+ #advertising.toString()")
    List<Advertising> findAllAdvertising(Advertising advertising);

    @Cacheable(key = "'PageList-'+#request.toString() + #advertising.toString()")
    PageInfo<Advertising> PageList(QueryRequest request, Advertising advertising);

    @CacheEvict(allEntries = true)
    void addAdvertising(Advertising advertising);

    @CacheEvict(allEntries = true)
    void deleteAdvertising(String advertising_id);

    @Async(value = "asyncServiceExecutor")
    @CacheEvict(allEntries = true)
    void updateAdvertising(Advertising advertising);

    @Cacheable(key = "'scappGetAdvertisingByMerchant_id-'+#p0+#p1+#p2+#p3")
    List<Advertising> scappGetAdvertisingByMerchant_id(String merchant_id, Long advertisingTypeId,String num, String query);

    @Cacheable(key = "'scappGetAdvertisingByMerchant_idTop10-'+#p0+#p1")
    List<Advertising> scappGetAdvertisingByMerchant_idTop10(String merchant_id);

    @Cacheable(key = "'findAllAdvertising'")
    List<Advertising> findAllAdvertising();

    boolean findByName(String advertising_name, String merchant_id);
}
