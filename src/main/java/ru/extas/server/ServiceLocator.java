/**
 *
 */
package ru.extas.server;

//import com.vaadin.server.VaadinServlet;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Поставщик служб приложения
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
public final class ServiceLocator {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceLocator.class);

	/** Constant <code>EXTACRM_JPA_UNIT="extacrmJpaUnit"</code> */
	public static final String EXTACRM_JPA_UNIT = "extacrmJpaUnit";

	/**
	 * Ищет подходящий экземпляр для интерфейса службы
	 *
	 * @param srvType Тип службы
	 * @return экземпляр службы
	 * @param <TServiceType> a TServiceType object.
	 */
	public static <TServiceType> TServiceType lookup(final Class<TServiceType> srvType) {

//		final VaadinServlet vaadinServlet = VaadinServlet.getCurrent();
//		if (vaadinServlet == null) {
//			logger.error("Couldn't get current instance of VaadinServlet");
//			throw new IllegalStateException("Couldn't get current instance of VaadinServlet");
//		}
//		final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(vaadinServlet.getServletContext());

		final WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

		return context.getBean(srvType);
	}

	/**
	 * Ищет подходящий именованый экземпляр заданного типа
	 *
	 * @param name    Имя
	 * @param srvType Тип
	 * @return экземпляр
	 * @param <TServiceType> a TServiceType object.
	 */
	public static <TServiceType> TServiceType lookup(final String name, final Class<TServiceType> srvType) {

//		final VaadinServlet vaadinServlet = VaadinServlet.getCurrent();
//		if (vaadinServlet == null) {
//			logger.error("Couldn't get current instance of VaadinServlet");
//			throw new IllegalStateException("Couldn't get current instance of VaadinServlet");
//		}
//		final WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(vaadinServlet.getServletContext());

		final WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		return context.getBean(name, srvType);
	}
}
