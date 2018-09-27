package cc.mrbird.system.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Merchant;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface MerchantService extends IService<Merchant> {
    @Cacheable(key = "'MerchantService-findMerchant-'+#p0")
    Merchant findMerchant(Long id);
    List<Merchant> findAllMerchant(QueryRequest request, Merchant merchant);
    @Cacheable(key = "'MerchantService-PageList-'+#request.toString() + #merchant.toString()")
    PageInfo<Merchant> PageList(QueryRequest request, Merchant merchant);
    @CacheEvict(allEntries = true)
    void addMerchant(Merchant merchant);

    @CacheEvict(allEntries = true)
    void deleteMerchant(String id);

    @CacheEvict(allEntries = true)
    void updateMerchant(Merchant merchant);
}
