package ru.extas.server.contacts;

import ru.extas.model.contacts.LegalEntity;
import ru.extas.security.SecuredRepository;

/**
 * Сервис управления данными о юр.лицах
 *
 * @author Valery Orlov
 *         Date: 03.04.2014
 *         Time: 14:28
 *
 * @since 0.3.0
 */
public interface LegalEntityService extends SecuredRepository<LegalEntity> {
}
