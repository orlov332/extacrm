package ru.extas.server.lead;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.extas.model.contacts.Person;
import ru.extas.model.lead.Lead;
import ru.extas.security.AbstractSecuredRepository;
import ru.extas.server.contacts.PersonRepository;
import ru.extas.server.contacts.SalePointRepository;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Sets.newHashSet;

/**
 * JPA имплементация службы управления лидами
 *
 * @author Valery Orlov
 *         Date: 23.10.13
 *         Time: 22:55
 * @version $Id: $Id
 * @since 0.3
 */
@Component
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class LeadRepositoryImpl extends AbstractSecuredRepository<Lead> implements LeadService {

    private final static Logger logger = LoggerFactory.getLogger(LeadRepositoryImpl.class);

    @Inject private LeadRepository leadRepository;
    @Inject private RuntimeService runtimeService;
    @Inject private PersonRepository personRepository;
    @Inject private SalePointRepository salePointRepository;

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void qualify(Lead obj) {
        checkNotNull(obj);
        checkState(obj.getClient() != null, "Can't qualify lead without client link!");
        checkState(obj.getVendor() != null, "Can't qualify lead without vendor link!");
        checkState(obj.getStatus() == Lead.Status.NEW, "Can't qualify not new lead!");

        // запуск БП
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("saleCreditProcess");
        // Привязать процесс к лиду
        obj.setProcessId(processInstance.getId());
        // Статус
        obj.setStatus(Lead.Status.QUALIFIED);
        // Сохранить изменения
        leadRepository.secureSave(obj);
        // Привязать лид к процессу
        runtimeService.setVariable(processInstance.getId(), "lead", obj);

        logger.debug("Started \"saleCreditProcess\" business process instance (id = {})", processInstance.getId());
    }

    /** {@inheritDoc} */
    @Override
    public JpaRepository<Lead, ?> getEntityRepository() {
        return leadRepository;
    }

    /** {@inheritDoc} */
    @Override
    protected Collection<String> getObjectBrands(Lead lead) {
        if(!isNullOrEmpty(lead.getMotorBrand()))
            newHashSet(lead.getMotorBrand());

        return null;
    }

    /** {@inheritDoc} */
    @Override
    protected Collection<String> getObjectRegions(Lead lead) {
        Set<String> regions = newHashSet();
        if(lead.getClient() != null
                && lead.getClient().getActualAddress() != null
                && !isNullOrEmpty(lead.getClient().getActualAddress().getRegion()))
            regions.add(lead.getClient().getActualAddress().getRegion());
        if(lead.getVendor() != null
                && lead.getVendor().getActualAddress() != null
                && !isNullOrEmpty(lead.getVendor().getActualAddress().getRegion()))
            regions.add(lead.getVendor().getActualAddress().getRegion());
        return regions;
    }

    /** {@inheritDoc} */
    @Transactional
    @Override
    public void permitObject(Lead lead, Person userContact, Collection<String> regions, Collection<String> brands) {
        if (lead != null) {
            super.permitObject(lead, userContact, regions, brands);
            // При этом необходимо сделать “видимыми” все связанные объекты лида:
            // Клиент
//            if(lead.getClient() instanceof Person)
                personRepository.permitAndSave(lead.getClient(), userContact, regions, brands);
//            else
//                companyRepository.permitAndSave((Company) lead.getClient(), userContact, regions, brands);
            // Продавец (торговая точка или компания)
            salePointRepository.permitAndSave(lead.getVendor(), userContact, regions, brands);
        }
    }
}
