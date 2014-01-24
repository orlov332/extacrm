package ru.extas.web.sale;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import ru.extas.model.ProductInSale;
import ru.extas.web.commons.component.EditField;
import ru.extas.web.commons.window.AbstractEditForm;
import ru.extas.web.product.ProductSelect;

/**
 * Панель ввода/редактирования кредитного продукта
 *
 * @author Valery Orlov
 *         Date: 20.01.14
 *         Time: 18:27
 */
public class ProdInSaleEditForm extends AbstractEditForm<ProductInSale> {

	// Компоненты редактирования
	@PropertyId("product")
	private ProductSelect productField;
	@PropertyId("summ")
	private EditField summField;
	@PropertyId("period")
	private EditField periodField;
	@PropertyId("mainProduct")
	private CheckBox mainProductField;

	public ProdInSaleEditForm(final String caption, final BeanItem<ProductInSale> obj) {
		super(caption, obj);
	}

	@Override
	protected void initObject(final ProductInSale obj) {
		if (obj.getId() == null) {
		}
	}

	@Override
	protected void saveObject(ProductInSale obj) {
	}

	@Override
	protected void checkBeforeSave(final ProductInSale obj) {

	}

	@Override
	protected ComponentContainer createEditFields(final ProductInSale obj) {
		final FormLayout form = new FormLayout();

		productField = new ProductSelect("Название продукта", "Введите название продукта", obj.getProduct());
		productField.setWidth(30, Unit.EM);
		productField.setRequired(true);
		form.addComponent(productField);

		summField = new EditField("Сумма", "Введите сумму по продукту");
		summField.setRequired(true);
		form.addComponent(summField);

		periodField = new EditField("Срок", "Введите период действия продукта");
		periodField.setRequired(true);
		form.addComponent(periodField);

		mainProductField = new CheckBox("Основной продукт");
		mainProductField.setDescription("Основной продукт по программе");
		form.addComponent(mainProductField);

		return form;
	}

}
