/**
 * DISCLAIMER
 *
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 *
 * @author jouni@vaadin.com
 *
 */

package ru.extas.web.insurance;

import ru.extas.model.security.ExtaDomain;
import ru.extas.server.security.UserManagementService;
import ru.extas.web.commons.ExtaGrid;
import ru.extas.web.commons.SubdomainInfo;
import ru.extas.web.commons.SubdomainInfoImpl;
import ru.extas.web.commons.SubdomainView;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static ru.extas.server.ServiceLocator.lookup;

/**
 * Раздел страхование
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
public class InsuranceView extends SubdomainView {

    private static final long serialVersionUID = -2524035728558575428L;

    /**
     * <p>Constructor for InsuranceView.</p>
     */
    public InsuranceView() {
        super("Страхование техники");
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected List<SubdomainInfo> getSubdomainInfo() {
        final UserManagementService userService = lookup(UserManagementService.class);
        final ArrayList<SubdomainInfo> ret = newArrayList();
        ret.add(new SubdomainInfoImpl("Имущ. страховки", ExtaDomain.INSURANCE_PROP) {

            @Override
            public ExtaGrid createGrid() {
                return new InsuranceGrid();
            }
        });
        ret.add(new SubdomainInfoImpl("Бланки (БСО)", ExtaDomain.INSURANCE_BSO) {

            @Override
            public ExtaGrid createGrid() {
                return new PolicyGrid();
            }
        });
        ret.add(new SubdomainInfoImpl("Акты Приема/Передачи", ExtaDomain.INSURANCE_TRANSFER) {

            @Override
            public ExtaGrid createGrid() {
                return new FormTransferGrid();
            }
        });
        ret.add(new SubdomainInfoImpl("Квитанции А-7", ExtaDomain.INSURANCE_A_7) {

            @Override
            public ExtaGrid createGrid() {
                return new A7FormGrid();
            }
        });
        return ret;
    }

}
