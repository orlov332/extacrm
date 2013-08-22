/**
 *
 */
package ru.extas.server;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import ru.extas.guice.persist.jdo.JdoPersistModule;

/**
 * Модуль инъекций для интерфейсов бизнес логики
 *
 * @author Valery Orlov
 */
public class ExtasServicesModule extends AbstractModule {

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {

        // Guice JDO persistence
        install(new JdoPersistModule("transactions-optional"));

        // Служба управления пользователями
        bind(UserManagementService.class).to(UserManagementServiceJdo.class).in(Scopes.SINGLETON);
        // Служба управления страховками
        bind(InsuranceRepository.class).to(InsuranceRepositoryJdo.class).in(Scopes.SINGLETON);
        // Служба управления контактами
        bind(ContactService.class).to(ContactServiceJdo.class).in(Scopes.SINGLETON);
        // Служба - поставщик простых справочных данных
        bind(SupplementService.class).to(SupplementServiceImpl.class).in(Scopes.SINGLETON);
        // Полисы БСО
        bind(PolicyRegistry.class).to(PolicyRegistryJdo.class).in(Scopes.SINGLETON);
        // Страховой калькулятор
        bind(InsuranceCalculator.class).to(InsuranceCalculatorImpl.class).in(Scopes.SINGLETON);
        // Управление актами приема передачи БСО
        bind(FormTransferService.class).to(FormTransferServiceJdo.class).in(Scopes.SINGLETON);
        // Управление формами А-7
        bind(A7FormService.class).to(A7FormServiceJdo.class).in(Scopes.SINGLETON);

    }

}
