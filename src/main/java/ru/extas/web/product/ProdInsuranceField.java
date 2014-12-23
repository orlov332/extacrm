package ru.extas.web.product;

import ru.extas.model.sale.ProdInsurance;

/**
 * Селектор страховых продуктов
 *
 * @author Valery Orlov
 *         Date: 05.12.2014
 *         Time: 20:22
 */
public class ProdInsuranceField extends ProductField<ProdInsurance> {

    public ProdInsuranceField(String caption, String description) {
        super(caption, description, ProdInsurance.class);
    }

}
