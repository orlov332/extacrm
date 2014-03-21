/**
 *
 */
package ru.extas.web.contacts;

import ru.extas.web.commons.GridDataDecl;

/**
 * Опции отображения контактов в списке
 *
 * @author Valery Orlov
 * @version $Id: $Id
 * @since 0.3
 */
public class PersonDataDecl extends GridDataDecl {

	/**
	 * <p>Constructor for PersonDataDecl.</p>
	 */
	public PersonDataDecl() {
		super();
		addMapping("name", "Имя");
		addMapping("birthday", "Дата рождения"/* , StringToJodaLDConverter.class */);
		addMapping("sex", "Пол");
		addMapping("phone", "Мобильный телефон");
		addMapping("email", "E-Mail");
		addMapping("actualAddress.region", "Регион");
		super.addCreateModifyMarkers();
	}

}
