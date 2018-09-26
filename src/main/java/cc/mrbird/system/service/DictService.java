package cc.mrbird.system.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Dict;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "DictService")
public interface DictService extends IService<Dict> {
    @Cacheable(key = "'PageList'+#request.toString() + #dict.toString()")
    PageInfo<Dict> PageList(QueryRequest request, Dict dict);

    @Cacheable(key = "'findAllDicts'+#p0.toString()+ #p1.toString()")
    List<Dict> findAllDicts(Dict dict, QueryRequest request);

    @Cacheable(key = "'findById'+#p0")
    Dict findById(Long dictId);

    @CacheEvict(allEntries = true)
    void addDict(Dict dict);

    @CacheEvict(allEntries = true)
    void deleteDicts(String dictIds);

    @CacheEvict(key = "#p0", allEntries = true)
    void updateDict(Dict dicdt);
}
