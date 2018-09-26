package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.RoleMenu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface RoleMenuServie extends IService<RoleMenu> {
	@CacheEvict(allEntries = true)
	void deleteRoleMenusByRoleId(String roleIds);
	@CacheEvict(allEntries = true)
	void deleteRoleMenusByMenuId(String menuIds);
}
