package cc.mrbird.job.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.AdvertisingType;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


@CacheConfig(cacheNames = "AdvertisingService")
public interface AdvertisingTypeService extends IService<AdvertisingType> {
    @Cacheable(key = "'findAdvertisingType-'+#p0")
    AdvertisingType findAdvertisingType(Long advertisingtype_id);

    @Cacheable(key = "'findAllAdvertisingType-'+ #advertisingtype.toString()")
    List<AdvertisingType> findAllAdvertisingType(AdvertisingType advertisingtype);

    @Cacheable(key = "'PageList-type-'+#request.toString() + #advertisingtype.toString()")
    PageInfo<AdvertisingType> PageList(QueryRequest request, AdvertisingType advertisingtype);

    @CacheEvict(allEntries = true)
    void addAdvertisingType(AdvertisingType advertisingtype);

    @CacheEvict(allEntries = true)
    void deleteAdvertisingType(String advertisingtype_id);

    @Async(value = "asyncServiceExecutor")
    @CacheEvict(allEntries = true)
    void updateAdvertisingType(AdvertisingType advertisingtype);

    @Cacheable(key = "'scappGetAdvertisingTypeByMerchant_id-'+#p0")
    List<AdvertisingType> scappGetAdvertisingTypeByMerchant_id(String merchant_id);

    boolean findByName(String advertisingtype_name, String merchant_id);
}