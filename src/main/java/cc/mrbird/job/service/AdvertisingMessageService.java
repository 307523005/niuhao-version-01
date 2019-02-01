package cc.mrbird.job.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.AdvertisingMessage;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "AdvertisingMessageService")
public interface AdvertisingMessageService extends IService<AdvertisingMessage> {
    @Cacheable(key = "'PageList-'+#request.toString() + #advertisingMessage.toString()")
    PageInfo<AdvertisingMessage> PageList(QueryRequest request, AdvertisingMessage advertisingMessage);

    @CacheEvict(allEntries = true)
    void addAdvertisingMessage(AdvertisingMessage advertisingMessage);

    @Cacheable(key = "'get-'+#advertisingMessage.toString()")
    List<AdvertisingMessage> getAdvertisingMessageByadvertisingId(AdvertisingMessage advertisingMessage);

    @CacheEvict(allEntries = true)
    void deleteAdvertisingMessage(String advmessageId);

    @CacheEvict(allEntries = true)
    void replyAdvertisingMessage(AdvertisingMessage advmessage);
    @Cacheable(key = "'findAdvertisingmessage-'+#p0")
    AdvertisingMessage findAdvertisingmessage(String advmessageId);
/*
     @Cacheable(key = "'scappGetAdvertisingMessageByMerchant_id-'+#p0")
    List<AdvertisingMessage> scappGetAdvertisingMessageByMerchant_id(String merchant_id);
*/


}