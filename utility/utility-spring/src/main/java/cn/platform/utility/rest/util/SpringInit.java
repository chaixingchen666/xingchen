package cn.platform.utility.rest.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @comment 静态调用applicationContext, 初始化系统常量
 * @author wuyao
 * @date 2014年12月8日 下午6:40:06
 * @version 1.0.0
 * @see
 */
public class SpringInit extends ContextLoaderListener {

	private static WebApplicationContext context;

	public SpringInit() {
		super();
	}

	public static <T> T getBean(Class<T> cls) {
		return context.getBean(cls);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> cls) {
		return context.getBean(name, cls);
	}

	public static boolean containsBean(String name) {
		return context.containsBean(name);
	}

	public static ConfigurableWebApplicationContext getContext() {
		return (ConfigurableWebApplicationContext) context;
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
		// context = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		initServletContext(event.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * 初始化web全局的配置
	 * 
	 * @param sc
	 *            全局上下文
	 */
	public void initServletContext(final ServletContext sc) {
		if (sc == null) {
			return;
		}
		sc.setAttribute("contextPath", sc.getContextPath());
	}
}
