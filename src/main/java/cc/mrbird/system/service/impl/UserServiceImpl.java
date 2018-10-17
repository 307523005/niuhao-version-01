package cc.mrbird.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.shiro.RequestUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.MD5Utils;
import cc.mrbird.system.dao.UserMapper;
import cc.mrbird.system.dao.UserRoleMapper;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.domain.UserRole;
import cc.mrbird.system.domain.UserWithRole;
import cc.mrbird.system.service.UserRoleService;
import cc.mrbird.system.service.UserService;
import tk.mybatis.mapper.entity.Example;

@Service("userService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService<User> implements UserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public User findByName(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        List<User> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }
    @Override
    public List<User> findUserWithDept(User user) {
        try {
            List<User> userWithDept = userMapper.findUserWithDept(user);
            return userWithDept;
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }
    @Override
    public PageInfo<User> PageList(QueryRequest request, User user) {
        Page<Object> objects = PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<User> userWithDept = userMapper.findUserWithDept(user);
        PageInfo<User> pageInfo = new PageInfo<>(userWithDept);
        return pageInfo;
    }

    @Override
    @Transactional
    public void registUser(User user) {
        user.setCrateTime(new Date());
        user.setTheme(User.DEFAULT_THEME);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setSsex(User.SEX_UNKNOW);
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        this.save(user);
        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(3L);
        this.userRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", userName);
        User user = new User();
        user.setTheme(theme);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void addUser(User user, Long[] roles) {
        user.setCrateTime(new Date());
        user.setTheme(User.DEFAULT_THEME);
        user.setAvatar(User.DEFAULT_AVATAR);
        user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
        this.save(user);
        setUserRoles(user, roles);
    }

    private void setUserRoles(User user, Long[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            this.userRoleMapper.insert(ur);
        });
    }

    @Override
    @Transactional
    public void updateUser(User user, Long[] roles) {
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        System.out.println("--updateUser--"+user.getMerchantId());
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);

        example.createCriteria().andCondition("user_id=", user.getUserId());
        this.userRoleMapper.deleteByExample(example);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));
        this.batchDelete(list, "userId", User.class);

        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateLoginTime(String userName) {
        Example example = new Example(User.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        User user = new User();
        user.setLastLoginTime(new Date());
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void updatePassword(String password,User user) {
        //User user = (User) SecurityUtils.getSubject().getPrincipal();

        Example example = new Example(User.class);
        example.createCriteria().andCondition("username=", user.getUsername());
        String newPassword = MD5Utils.encrypt(user.getUsername().toLowerCase(), password);
        user.setPassword(newPassword);
        this.userMapper.updateByExampleSelective(user, example);
    }

    @Override
    public UserWithRole findById(Long userId) {
        List<UserWithRole> list = this.userMapper.findUserWithRole(userId);
        List<Long> roleList = new ArrayList<>();
        for (UserWithRole uwr : list) {
            roleList.add(uwr.getRoleId());
        }
        if (list.isEmpty()) {
            return null;
        }
        UserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleList);
        return userWithRole;
    }

    @Override
    public User findUserProfile(User user) {
        return this.userMapper.findUserProfile(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(User user) {
        user.setUsername(null);
        user.setPassword(null);
        if (user.getDeptId() == null)
            user.setDeptId(0L);
        this.updateNotNull(user);
    }

}
