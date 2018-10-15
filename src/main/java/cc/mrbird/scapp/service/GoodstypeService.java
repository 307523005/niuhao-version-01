package cc.mrbird.scapp.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.scapp.domain.Goodstype;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "GoodstypeService-")
public interface GoodstypeService extends IService<Goodstype> {
    @Cacheable(key = "'findGoodstype-'+#p0")
    Goodstype findGoodstype(String goodstype_id);

    @Cacheable(key = "'findByName-'+#goodstype_name+#merchant_id")
    Goodstype findByName(String goodstype_name, String merchant_id);

    @Cacheable(key = "'findAllGoodstype-'+#request.toString()+#goodstype.toString()")
    List<Goodstype> findAllGoodstype(QueryRequest request, Goodstype goodstype);

    @Cacheable(key = "'getGoodstype-'+#p0")
    List<Goodstype> getGoodstype(String merchant_id);

    @Cacheable(key = "'PageList-'+#request.toString() + #goodstype.toString()")
    PageInfo<Goodstype> PageList(QueryRequest request, Goodstype goodstype);

    @CacheEvict(allEntries = true)
    void addGoodstype(Goodstype goodstype);

    @CacheEvict(allEntries = true)
    void deleteGoodstype(String goodstype_id);

    @CacheEvict(allEntries = true)
    void updateGoodstype(Goodstype goodstype);
}
