/**
 *
 */
package ru.extas.web.contacts.legalentity;

import ru.extas.web.commons.EmailLinkColumnGen;
import ru.extas.web.commons.GridDataDecl;
import ru.extas.web.commons.UrlLinkColumnGen;
import ru.extas.web.commons.converters.PhoneConverter;
import ru.extas.web.contacts.company.CompanyColumnGenerator;

/**
 * Опции отображения контактов в списке
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
public class LegalEntityDataDecl extends GridDataDecl {

	/**
	 * <p>Constructor for LegalEntityDataDecl.</p>
	 */
	public LegalEntityDataDecl() {
		super();
		addMapping("name", "Имя");
        addMapping("inn", "ИНН");
        addMapping("company.name", "Компания", new CompanyColumnGenerator());
		addMapping("phone", "Телефон", PhoneConverter.class);
		addMapping("email", "E-Mail", new EmailLinkColumnGen(), getPresentFlags(true));
		addMapping("www", "WWW", new UrlLinkColumnGen(), getPresentFlags(true));
		addMapping("legalAddress.regionWithType", "Регион");
		addMapping("legalAddress.cityWithType", "Город");
		super.addDefaultMappings();
	}

}
