/**
 *
 */
package ru.extas.web.insurance;

import com.vaadin.data.Container;
import ru.extas.model.contacts.Company;
import ru.extas.model.contacts.Employee;
import ru.extas.model.contacts.Employee_;
import ru.extas.model.contacts.SalePoint;
import ru.extas.model.insurance.A7Form;
import ru.extas.model.insurance.A7Form_;
import ru.extas.model.security.ExtaDomain;
import ru.extas.model.security.SecureTarget;
import ru.extas.server.insurance.A7FormRepository;
import ru.extas.server.security.UserManagementService;
import ru.extas.web.commons.*;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static ru.extas.server.ServiceLocator.lookup;
import static ru.extas.web.commons.GridItem.extractBean;

/**
 * <p>A7FormGrid class.</p>
 *
 * @author Valery Orlov
 * @version $Id: $Id
 * @since 0.3
 */
public class A7FormGrid extends ExtaGrid<A7Form> {

    private static final long serialVersionUID = 6290106109723378415L;

    /**
     * <p>Constructor for A7FormGrid.</p>
     */
    public A7FormGrid() {
        super(A7Form.class);
    }

    @Override
    public ExtaEditForm<A7Form> createEditForm(final A7Form a7Form, final boolean isInsert) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected GridDataDecl createDataDecl() {
        return new A7FormDataDecl();
    }

    private class A7SecuredContainer extends AbstractSecuredDataContainer<A7Form> {

        public A7SecuredContainer(final Class<A7Form> entityClass, final ExtaDomain domain) {
            super(entityClass, domain);
        }

        @Override
        protected Predicate createPredicate4Target(final CriteriaBuilder cb, final CriteriaQuery<?> cq, final SecureTarget target) {
            Predicate predicate = null;
            final Root<A7Form> objectRoot = (Root<A7Form>) getFirst(cq.getRoots(), null);
            final Employee curUserContact = lookup(UserManagementService.class).getCurrentUserEmployee();

            switch (target) {
                case OWNONLY:
                    predicate = cb.equal(objectRoot.get(A7Form_.owner), curUserContact);
                    break;
                case SALE_POINT: {
                    Set<SalePoint> workPlaces = null;
                    workPlaces = newHashSet();
                    workPlaces.add(curUserContact.getWorkPlace());
                    if (!isEmpty(workPlaces)) {
                        final Join<Employee, SalePoint> workPlaceRoot = objectRoot.join(A7Form_.owner, JoinType.LEFT).join(Employee_.workPlace, JoinType.LEFT);
                        predicate = workPlaceRoot.in(workPlaces);
                    }
                    break;
                }
                case CORPORATE:
                    final Set<SalePoint> workPlaces = null;
                    final Set<Company> companies = newHashSet();
                    companies.add(curUserContact.getCompany());
                    for (final Company company : companies) {
                        workPlaces.addAll(company.getSalePoints());
                    }
                    if (!isEmpty(workPlaces)) {
                        final Join<Employee, SalePoint> workPlaceRoot = objectRoot.join(A7Form_.owner, JoinType.LEFT).join(Employee_.workPlace, JoinType.LEFT);
                        workPlaceRoot.in(workPlaces);
                    }
                    break;
                case ALL:
                    break;
            }

            return predicate;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Container createContainer() {
        final ExtaDataContainer<A7Form> cnt = new A7SecuredContainer(A7Form.class, ExtaDomain.INSURANCE_A_7);
        cnt.addNestedContainerProperty("owner.name");
        return cnt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<UIAction> createActions() {
        final List<UIAction> actions = newArrayList();

        actions.add(new ItemAction("Утрачен", "Перевести выделенный в списке бланк в \"Утраченные\"", null) {
            @Override
            public void fire(final Object itemId) {
                final A7Form.Status status = A7Form.Status.LOST;
                changeStatus(itemId, status);
            }
        });

        actions.add(new ItemAction("Испорчен", "Перевести выделенный в списке бланк в \"Испорченные\"", null) {
            @Override
            public void fire(final Object itemId) {
                final A7Form.Status status = A7Form.Status.BROKEN;
                changeStatus(itemId, status);
            }
        });

        return actions;
    }

    private void changeStatus(final Object itemId, final A7Form.Status status) {
        final A7Form curObj = extractBean(table.getItem(itemId));

        final A7FormRepository formService = lookup(A7FormRepository.class);
        formService.changeStatus(curObj, status);
        refreshContainerItem(itemId);
    }
}
