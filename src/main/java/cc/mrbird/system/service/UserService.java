package cc.mrbird.system.service;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.domain.UserWithRole;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig(cacheNames = "User-Dept-Role-Memnu")
public interface UserService extends IService<User> {
    @Cacheable(key = "'UserService-PageList-'+#request.toString() + #user.toString()")
    PageInfo<User> PageList(QueryRequest request, User user);
    @Cacheable(key = "'UserService-findById-'+#p0")
    UserWithRole findById(Long userId);
    @Cacheable(key = "'UserService-findByName-'+#p0")
    User findByName(String userName);

    @Cacheable(key = "'UserService-findUserWithDept-'+#p0.toString()")
    List<User> findUserWithDept(User user);

    @CacheEvict(key = "'UserService-registUser-'+#p0.toString()", allEntries = true)
    void registUser(User user);
    @CacheEvict(allEntries = true)
    void updateTheme(String theme, String userName);

    @CacheEvict(allEntries = true)
    void addUser(User user, Long[] roles);

    @CacheEvict(allEntries = true)
    void updateUser(User user, Long[] roles);

    @CacheEvict(key = "#p0", allEntries = true)
    void deleteUsers(String userIds);
    @CacheEvict(allEntries = true)
    void updateLoginTime(String userName);
    @CacheEvict(allEntries = true)
    void updatePassword(String password, User user);
    @Cacheable(key = "'UserService-findUserProfile-'+#p0.toString()")
    User findUserProfile(User user);
    @CacheEvict(allEntries = true)
    void updateUserProfile(User user);
}
