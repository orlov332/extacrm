package ru.extas.web.product;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import ru.extas.model.ProdInstallments;
import ru.extas.server.ProdInstallmentsRepository;
import ru.extas.web.commons.component.EditField;
import ru.extas.web.commons.converters.StringToPercentConverter;
import ru.extas.web.commons.window.AbstractEditForm;
import ru.extas.web.contacts.CompanySelect;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * Панель ввода/редактирования рассрочки
 *
 * @author Valery Orlov
 *         Date: 20.01.14
 *         Time: 18:27
 */
public class ProdInstallmentsEditForm extends AbstractEditForm<ProdInstallments> {

	// Компоненты редактирования
	@PropertyId("name")
	private EditField nameField;
	@PropertyId("vendor")
	private CompanySelect vendorField;
	@PropertyId("maxPeroid")
	private EditField maxPeroidField;
	@PropertyId("minDownpayment")
	private EditField minDownpaymentField;
	@PropertyId("active")
	private CheckBox activeField;
	@PropertyId("comment")
	private TextArea commentField;

	public ProdInstallmentsEditForm(final String caption, final BeanItem<ProdInstallments> obj) {
		super(caption, obj);
	}

	@Override
	protected void initObject(final ProdInstallments obj) {
		if (obj.getId() == null) {
			obj.setActive(true);
		}
	}

	@Override
	protected void saveObject(final ProdInstallments obj) {
		lookup(ProdInstallmentsRepository.class).save(obj);
	}

	@Override
	protected void checkBeforeSave(final ProdInstallments obj) {

	}

	@Override
	protected ComponentContainer createEditFields(final ProdInstallments obj) {
		final FormLayout form = new FormLayout();

		nameField = new EditField("Название продукта", "Введите название продукта");
		nameField.setColumns(30);
		nameField.setRequired(true);
		form.addComponent(nameField);

		vendorField = new CompanySelect("Поставщик");
		form.addComponent(vendorField);

		maxPeroidField = new EditField("Max период рассрочки", "Введите максимальный период рассрочки по продукту");
		maxPeroidField.setRequired(true);
		form.addComponent(maxPeroidField);

		minDownpaymentField = new EditField("Первоначальный взнос", "Введите минимальный первоначальный взнос");
		minDownpaymentField.setRequired(true);
		minDownpaymentField.setConverter(lookup(StringToPercentConverter.class));
		form.addComponent(minDownpaymentField);

		activeField = new CheckBox("Активный продукт");
		activeField.setDescription("Участвует ли продукт в продажах (учавствует если активен)");
		form.addComponent(activeField);

		commentField = new TextArea("Примечание");
		commentField.setDescription("Дополнительная информация о продукте");
		commentField.setNullRepresentation("");
		commentField.setInputPrompt("Дополнительная информация о продукте");
		commentField.setColumns(30);
		commentField.setRows(5);
		form.addComponent(commentField);

		return form;
	}

}
