package ru.extas.web.sale;

import ru.extas.model.sale.ProductInSale;
import ru.extas.utils.SupplierSer;
import ru.extas.web.commons.InsuranceInSmthEditForm;

import java.math.BigDecimal;

/**
 * @author Valery Orlov
 * @author sandarkin
 */
public class InsuranceInSaleEditForm extends InsuranceInSmthEditForm<ProductInSale> {

    public InsuranceInSaleEditForm(final String caption, final ProductInSale targetObject,
                                   final SupplierSer<BigDecimal> priceSupplier,
                                   final SupplierSer<String> brandSupplier) {
        super(caption, targetObject, priceSupplier, brandSupplier);
    }

}
