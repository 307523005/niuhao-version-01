package cc.mrbird.scapp.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.scapp.domain.Bannerimg;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


@CacheConfig(cacheNames = "BannerimgService")
public interface BannerimgService extends IService<Bannerimg> {
    @Cacheable(key = "'findBannerimg-'+#p0")
    Bannerimg findBannerimg(Long bannerimg_id);
    @Cacheable(key = "'findByName-'+#p0+#p1")
    Bannerimg findByName(String bannerimg_name,String merchant_id);
    List<Bannerimg> findAllBannerimg(QueryRequest request, Bannerimg bannerimg);
    @Cacheable(key = "'PageList-'+#request.toString() + #bannerimg.toString()")
    PageInfo<Bannerimg> PageList(QueryRequest request, Bannerimg bannerimg);
    @CacheEvict(allEntries = true)
    void addBannerimg(Bannerimg bannerimg);

    @CacheEvict(allEntries = true)
    void deleteBannerimg(String bannerimgid);

    @CacheEvict(allEntries = true)
    void updateBannerimg(Bannerimg bannerimg);
}
