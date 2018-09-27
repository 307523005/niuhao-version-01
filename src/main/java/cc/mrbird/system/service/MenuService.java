package cc.mrbird.system.service;

import java.util.List;
import java.util.Map;

import cc.mrbird.common.domain.Tree;
import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface MenuService extends IService<Menu> {
    @Cacheable(key = "'MenuService-findUserPermissions-'+ #p0")
    List<Menu> findUserPermissions(String userName);

    @Cacheable(key = "'MenuService-findUserMenus-'+ #p0")
    List<Menu> findUserMenus(String userName);

    @Cacheable(key = "'MenuService-findAllMenus-'+ #p0.toString()")
    List<Menu> findAllMenus(Menu menu);

    Tree<Menu> getMenuButtonTree();

    Tree<Menu> getMenuTree();

    Tree<Menu> getUserMenu(String userName);

    @Cacheable(key = "'MenuService-findById-'+ #p0")
    Menu findById(Long menuId);

    @Cacheable(key = "'MenuService-findByNameAndType-'+ #p0+#p1")
    List<Menu> findByNameAndType(String menuName, String type);

    @CacheEvict(allEntries = true)
    void addMenu(Menu menu);

    @CacheEvict(allEntries = true)
    void updateMenu(Menu menu);

    @CacheEvict(allEntries = true)
    void deleteMeuns(String menuIds);

    @Cacheable(key = "'MenuService-getAllUrl-'+ #p0")
    List<Map<String, String>> getAllUrl(String p1);
}
