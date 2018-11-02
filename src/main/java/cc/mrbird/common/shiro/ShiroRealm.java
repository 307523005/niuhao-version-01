package cc.mrbird.common.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cc.mrbird.system.domain.Menu;
import cc.mrbird.system.domain.Role;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.MenuService;
import cc.mrbird.system.service.RoleService;
import cc.mrbird.system.service.UserService;

/**
 * 自定义实现 ShiroRealm，包含认证和授权两大模块
 *
 * @author MrBird
 */
public class ShiroRealm extends AuthorizingRealm {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    /**
     * 授权模块，获取用户角色和权限
     *
     * @param
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)throws AuthenticationException  {
      /*  User user = (User) SecurityUtils.getSubject().getPrincipal();
        String userName = user.getUsername();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = this.roleService.findUserRole(userName);
        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(userName);
        Set<String> permissionSet = new HashSet<>();
        for (Menu m : permissionList) {
            // 处理用户多权限 用逗号分隔
            permissionSet.addAll(Arrays.asList(m.getPerms().split(",")));
        }
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;*/
        logger.info("---------------- 执行 Shiro 权限获取 ---------------------");


        Object principal = principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (principal instanceof User) {
            User user = (User)principal;
            String userName = user.getUsername();
            // 获取用户角色集
            List<Role> roleList = this.roleService.findUserRole(userName);
            Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
            simpleAuthorizationInfo.addRoles(roleSet);

            // 获取用户权限集
            List<Menu> permissionList = this.menuService.findUserPermissions(userName);
            Set<String> permissionSet = new HashSet<>();
            for (Menu m : permissionList) {
                // 处理用户多权限 用逗号分隔
                permissionSet.addAll(Arrays.asList(m.getPerms().split(",")));
            }
            simpleAuthorizationInfo.addStringPermissions(permissionSet);
        }

        logger.info("---- 获取到以下权限 ----");
        return simpleAuthorizationInfo;
        /*if (principal instanceof User) {
            User userLogin = (User) principal;
            Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
            authorizationInfo.addRoles(roles);

            Set<String> permissions = userService.findPermissionsByUserId(userLogin.getId());
            authorizationInfo.addStringPermissions(permissions);
        }*/

      /*  logger.info(authorizationInfo.getStringPermissions().toString());
        logger.info("---------------- Shiro 权限获取成功 ----------------------");
        return authorizationInfo;*/
    }

    /**
     * 用户认证
     *
     * @param   authcToken
     * @return AuthenticationInfo 身份认证信息
     * @throws AuthenticationException 认证相关异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {

      /*  // 获取用户输入的用户名和密码
        String userName = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        // 通过用户名到数据库查询用户信息
        User user = this.userService.findByName(userName);

        if (user == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (User.STATUS_LOCK.equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        return new SimpleAuthenticationInfo(user, password, getName());*/
        logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String name = token.getUsername();
        String password = String.valueOf(token.getPassword());
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        // 从数据库获取对应用户名密码的用户
        User userList = userService.findByName(name);
        if (userList != null) {
            // 用户为禁用状态
            if (User.STATUS_LOCK.equals(user.getStatus())) {
                throw new LockedAccountException("账号已被锁定,请联系管理员！");
            }
            logger.info("---------------- Shiro 凭证认证成功 ----------------------");
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    userList, //用户
                    userList.getPassword(), //密码
                    getName()  //realm name
            );
            return authenticationInfo;
        }
        throw new UnknownAccountException();
    }

}
