package ru.extas.web.lead;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import ru.extas.model.common.Address;
import ru.extas.model.contacts.SalePoint;
import ru.extas.web.commons.ExtaTheme;
import ru.extas.web.commons.FormUtils;
import ru.extas.web.commons.GridDataDecl;
import ru.extas.web.contacts.salepoint.SalePointEditForm;

import static com.google.common.base.Strings.nullToEmpty;

/**
* @author Valery Orlov
*         Date: 06.08.2014
*         Time: 18:38
*/
public class SalePointColumnGenerator extends GridDataDecl.ComponentColumnGenerator {

    private final String salePointPropId;
    private final String salePointNamePropId;
    private final String regionPropId;

    public SalePointColumnGenerator(final String salePointPropId, final String salePointNamePropId, final String regionPropId) {
        this.salePointPropId = salePointPropId;
        this.salePointNamePropId = salePointNamePropId;
        this.regionPropId = regionPropId;
    }

    @Override
    public Object generateCell(final Object columnId, final Item item, final Object itemId) {
        String region = "";
        final Button link = new Button();
        link.addStyleName(ExtaTheme.BUTTON_LINK);
        final SalePoint salePoint = (SalePoint) item.getItemProperty(salePointPropId).getValue();
        if (salePoint != null) {
            link.setCaption(salePoint.getName());
            link.addClickListener(event -> {

                final SalePointEditForm editWin = new SalePointEditForm(salePoint);
                FormUtils.showModalWin(editWin);

            });
            final Address address = salePoint.getPosAddress();
            if(address != null)
                region = address.getRegionWithType();
        } else if (salePointNamePropId != null) {
            region = nullToEmpty((String) item.getItemProperty(regionPropId).getValue());
            link.setCaption(nullToEmpty((String) item.getItemProperty(salePointNamePropId).getValue()));
            link.setEnabled(false);
        }
        final VerticalLayout cell = new VerticalLayout();
        cell.addComponent(new Label(region));
        cell.addComponent(link);
        return cell;
    }
}
