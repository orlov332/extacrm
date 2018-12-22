
/**
 * <p>TabInfo interface.</p>
 *
 * @author Valery_2
 *
 * @since 0.3
 */
package ru.extas.web.commons;

import ru.extas.model.security.ExtaDomain;

import java.io.Serializable;
public interface SubdomainInfo extends Serializable {

    /**
     * <p>getCaption.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getCaption();

    /**
     * <p>createGrid.</p>
     *
     * @return a {@link com.vaadin.ui.Component} object.
     */
    ExtaGrid createGrid();

	/**
	 * <p>getDomain.</p>
	 *
	 * @return a {@link ru.extas.model.security.ExtaDomain} object.
	 */
	ExtaDomain getDomain();

    boolean isEditInPage();
}
