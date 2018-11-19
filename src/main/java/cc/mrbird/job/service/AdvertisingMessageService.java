package cc.mrbird.job.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.AdvertisingMessage;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "AdvertisingMessageService")
public interface AdvertisingMessageService extends IService<AdvertisingMessage> {

    @CacheEvict(allEntries = true)
    void addAdvertisingMessage(AdvertisingMessage advertisingMessage);
    @Cacheable(key = "'getAdvertisingMessageByadvertisingId-'+#p0")
    List<AdvertisingMessage> getAdvertisingMessageByadvertisingId(AdvertisingMessage advertisingMessage);

/*
    @CacheEvict(allEntries = true)
    void deleteAdvertisingMessage(String advmessageId);

    //@Async(value = "asyncServiceExecutor")
    @CacheEvict(allEntries = true)
    void updateAdvertisingMessage(AdvertisingMessage advertisingMessage);

    @Cacheable(key = "'scappGetAdvertisingMessageByMerchant_id-'+#p0")
    List<AdvertisingMessage> scappGetAdvertisingMessageByMerchant_id(String merchant_id);
*/


}