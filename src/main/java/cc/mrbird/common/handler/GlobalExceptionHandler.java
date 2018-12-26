package cc.mrbird.common.handler;

import cc.mrbird.common.exception.LimitAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.mrbird.common.domain.ResponseBo;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
	private static Log log = LogFactory.getLog(GlobalExceptionHandler.class);
	@ExceptionHandler(value = AuthorizationException.class)
	@ResponseBody
	public ResponseBo handleAuthorizationException() {
		return ResponseBo.error("暂无权限，请联系管理员！");
	}

	@ExceptionHandler(value = ExpiredSessionException.class)
	public String handleExpiredSessionException() {
		return "login";
	}
	@ExceptionHandler(value = LimitAccessException.class)
	@ResponseBody
	public ResponseBo handleLimitAccessException(LimitAccessException e) {
		return ResponseBo.error(e.getMessage());
	}
	@ExceptionHandler(value = Exception.class)
	public String allExceptionHandler(HttpServletRequest request,
									  Exception exception) throws Exception {
		exception.printStackTrace();
		log.error("输出本地化的描述信息：" + exception.getLocalizedMessage());
		log.error("返回此异常的原因：" + exception.getCause());//此 throwable 的 cause，如果 cause 不存在或是未知的，则返回 null。
		log.error("获得被屏蔽的异常：" + exception.getSuppressed());
		log.error("输出异常的描述信息：" + exception.getMessage());
		log.error("将异常栈打印到输出流中：" + exception.getStackTrace());
		log.error(exception.getMessage(),exception);
		return "/error/error";//返回404页面
	}
}
