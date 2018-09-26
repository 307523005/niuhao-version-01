package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.domain.Tree;
import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Dept;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface DeptService extends IService<Dept> {
	Tree<Dept> getDeptTree();
	@Cacheable(key = "'DeptService-findAllDepts-'+#p0.toString()")
	List<Dept> findAllDepts(Dept dept);
	@Cacheable(key = "'DeptService-findTree'")
	List<Dept> findAllDeptsTree();

	Dept findByName(String deptName);

	Dept findById(Long deptId);
	@CacheEvict(allEntries = true)
	void addDept(Dept dept);
	@CacheEvict(allEntries = true)
	void updateDept(Dept dept);
	@CacheEvict(allEntries = true)
	void deleteDepts(String deptIds);
}
