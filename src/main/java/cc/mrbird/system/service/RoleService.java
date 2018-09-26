package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Role;
import cc.mrbird.system.domain.RoleWithMenu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface RoleService extends IService<Role> {
    @Cacheable(key = "'RoleService-findUserRole-'+#p0")
    List<Role> findUserRole(String userName);

    @Cacheable(key = "'RoleService-findAllRole-'+#p0.toString()")
    List<Role> findAllRole(Role role);

    @Cacheable(key = "'RoleService-findRoleWithMenus-'+#p0")
    RoleWithMenu findRoleWithMenus(Long roleId);
    @Cacheable(key = "'RoleService-findByName-'+#p0")
    Role findByName(String roleName);
    @CacheEvict(allEntries = true)
    void addRole(Role role, Long[] menuIds);
    @CacheEvict(allEntries = true)
    void updateRole(Role role, Long[] menuIds);
    @CacheEvict(allEntries = true)
    void deleteRoles(String roleIds);
}
