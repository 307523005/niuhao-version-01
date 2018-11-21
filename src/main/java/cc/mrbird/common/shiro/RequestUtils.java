package cc.mrbird.common.shiro;

import cc.mrbird.system.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class RequestUtils {

    /**
     * 获取当前登录的用户，若用户未登录，则返回未登录 json
     *
     * @return
     */
    public static User currentLoginUser() {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Serializable id = session.getId();
        return (User) subject.getPrincipal();
     /*   if (subject.isAuthenticated()) {
            Object principal = subject.getPrincipals().getPrimaryPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }*/
       // return null;
    }
}
