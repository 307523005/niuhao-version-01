package cc.mrbird.scapp.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.scapp.domain.Goods;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "GoodsService-")
public interface GoodsService extends IService<Goods> {
    @Cacheable(key = "'findGoods-'+#p0")
    Goods findGoods(String goods_id);

    @Cacheable(key = "'findByName-'+#goods_name+#merchant_id")
    Goods findByName(String goods_name, String merchant_id);

    @Cacheable(key = "'findAllGoods-'+#request.toString()+#goods.toString()")
    List<Goods> findAllGoods(QueryRequest request, Goods goods);

    @Cacheable(key = "'getGoods-'+#p0+#p1")
    List<Goods> getGoods(String merchant_id, String goodstype_id);

    @Cacheable(key = "'PageList-'+#request.toString() + #goods.toString()")
    PageInfo<Goods> PageList(QueryRequest request, Goods goods);

    @CacheEvict(allEntries = true)
    void addGoods(Goods goods);

    @CacheEvict(allEntries = true)
    void deleteGoods(String goods_id);

    @CacheEvict(allEntries = true)
    void updateGoods(Goods goods);
}
