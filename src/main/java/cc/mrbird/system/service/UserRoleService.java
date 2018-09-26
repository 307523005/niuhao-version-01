package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.UserRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface UserRoleService extends IService<UserRole> {
	@CacheEvict(allEntries = true)
	void deleteUserRolesByRoleId(String roleIds);
	@CacheEvict(allEntries = true)
	void deleteUserRolesByUserId(String userIds);
}
