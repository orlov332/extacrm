package ru.extas.web.sale;

import ru.extas.web.commons.GridDataDecl;

/**
 * @author Valery Orlov
 *         Date: 15.10.13
 *         Time: 12:52
 */
class SaleDataDecl extends GridDataDecl {
    public SaleDataDecl() {
        addMapping("client.name", "Клиент");
        addMapping("type", "Тип продажи");
        addMapping("vendor.name", "Поставщик");
        addMapping("motorType", "Тип техники");
        addMapping("motorBrand", "Марка техники");
        addMapping("motorModel", "Модель техники");
        addMapping("motorPrice", "Стоимость техники");
        addMapping("dealer.name", "Мотосалон");
        addMapping("region", "Регион");
        super.addCreateModifyMarkers();
    }
}
