package ru.extas.web.insurance;

import java.util.EnumSet;

import ru.extas.web.commons.DataDeclMapping.PresentFlag;
import ru.extas.web.commons.GridDataDecl;

/**
 * Опции отрбражения страховок
 * 
 * @author Valery Orlov
 * 
 */
public class InsuranceDataDecl extends GridDataDecl {

	public InsuranceDataDecl() {
		super();
		addMapping("regNum", "Номер полиса");
		addMapping("date", "Дата договора");
		addMapping("client.name", "Клиент");
		addMapping("client.birthday", "Дата рождения", EnumSet.of(PresentFlag.COLLAPSED));
		addMapping("client.cellPhone", "Телефон", EnumSet.of(PresentFlag.COLLAPSED));
		addMapping("motorType", "Тип техники");
		addMapping("motorBrand", "Марка техники");
		addMapping("motorModel", "Модель техники");
		addMapping("riskSum", "Страховая сумма");
		addMapping("premium", "Страховая премия");
		addMapping("paymentDate", "Дата оплаты страховой премии", EnumSet.of(PresentFlag.COLLAPSED));
		addMapping("startDate", "Дата начала срока действия договора", EnumSet.of(PresentFlag.COLLAPSED));
		addMapping("endDate", "Дата окончания срока действия договора", EnumSet.of(PresentFlag.COLLAPSED));
		addMapping("pointOfSale", "Точка продаж");
		super.addCreateModifyMarkers();
	}

}
