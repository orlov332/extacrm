/**
 *
 */
package ru.extas.web.contacts;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.extas.model.contacts.AddressInfo;
import ru.extas.model.contacts.Company;
import ru.extas.model.contacts.Contact;
import ru.extas.server.contacts.CompanyRepository;
import ru.extas.server.references.SupplementService;
import ru.extas.web.commons.NotificationUtil;
import ru.extas.web.commons.component.EditField;
import ru.extas.web.commons.component.EmailField;
import ru.extas.web.commons.component.ExtaFormLayout;
import ru.extas.web.commons.component.PhoneField;
import ru.extas.web.commons.ExtaEditForm;
import ru.extas.web.reference.CitySelect;
import ru.extas.web.reference.RegionSelect;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * <p>CompanyEditForm class.</p>
 *
 * @author Valery Orlov
 * @version $Id: $Id
 * @since 0.3
 */
@SuppressWarnings("FieldCanBeLocal")
public class CompanyEditForm extends ExtaEditForm<Company> {

    private static final long serialVersionUID = -7787385620289376599L;
    private final static Logger logger = LoggerFactory.getLogger(CompanyEditForm.class);

    // Компоненты редактирования

    // Вкладка - "Общая информация"
    @PropertyId("name")
    private EditField nameField;
    @PropertyId("phone")
    private PhoneField phoneField;
    @PropertyId("email")
    private EditField emailField;
    @PropertyId("www")
    private EditField wwwField;
    @PropertyId("regAddress.region")
    private ComboBox regionField;
    @PropertyId("regAddress.city")
    private ComboBox cityField;
    @PropertyId("regAddress.postIndex")
    private EditField postIndexField;
    @PropertyId("regAddress.streetBld")
    private TextArea streetBldField;

    // Вкладка - "Владельцы"
    @PropertyId("owners")
    private CompanyOwnersField ownersField;

    // Вкладка - "Юр. лица"
    @PropertyId("legalEntities")
    private LegalEntitiesField legalsField;

    // Вкладка - "Торговые точки"
    @PropertyId("salePoints")
    private SalePointsField salePointsField;

    // Вкладка - "Сотрудники"
    @PropertyId("employees")
    private ContactEmployeeField employeeField;


    public CompanyEditForm(Company company) {
        super(company.isNew() ?
                "Ввод новой компании в систему" :
                String.format("Редактирование компании: %s", company.getName()));
        final BeanItem<Company> beanItem = new BeanItem<>(company);
        beanItem.expandProperty("regAddress");

        initForm(beanItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initObject(final Company obj) {
        if (obj.isNew()) {
            // Инициализируем новый объект
            // TODO: Инициализировать клиента в соответствии с локацией текущего
        }
        if (obj.getRegAddress() == null)
            obj.setRegAddress(new AddressInfo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Company saveObject(final Company obj) {
        logger.debug("Saving contact data...");
        final CompanyRepository contactRepository = lookup(CompanyRepository.class);
        contactRepository.secureSave(obj);
        NotificationUtil.showSuccess("Компания сохранена");
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ComponentContainer createEditFields(final Company obj) {
        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeUndefined();

        // Вкладка - "Общая информация"
        final FormLayout mainForm = createMainForm(obj);
        tabsheet.addTab(mainForm).setCaption("Общие данные");

        // Вкладка - "Владельцы"
        final Component ownerForm = createOwnerForm();
        tabsheet.addTab(ownerForm).setCaption("Владельцы");

        // Вкладка - "Юр. лица"
        final Component legalsForm = createLegalsForm(obj);
        tabsheet.addTab(legalsForm).setCaption("Юридические лица");


        // Вкладка - "Торговые точки"
        final Component salePointsForm = createSalePointsForm(obj);
        tabsheet.addTab(salePointsForm).setCaption("Торговые точки");
        tabsheet.addSelectedTabChangeListener(event -> {
            if (event.getTabSheet().getSelectedTab() == salePointsForm)
                legalsField.commit();
        });

        // Вкладка - "Сотрудники"
        final Component employesForm = createEmployesForm();
        tabsheet.addTab(employesForm).setCaption("Сотрудники");

        return tabsheet;
    }

    private Component createLegalsForm(final Company obj) {
        legalsField = new LegalEntitiesField(obj);
        return legalsField;
    }

    private Component createEmployesForm() {
        employeeField = new ContactEmployeeField();
        return employeeField;
    }

    private Component createSalePointsForm(final Company obj) {
        salePointsField = new SalePointsField(obj);
        return salePointsField;
    }

    private Component createOwnerForm() {
        ownersField = new CompanyOwnersField();
        return ownersField;
    }

    private FormLayout createMainForm(final Contact obj) {
        final FormLayout formLayout = new ExtaFormLayout();
        formLayout.setMargin(true);

        nameField = new EditField("Название");
        nameField.setRequired(true);
        nameField.setImmediate(true);
        nameField.setColumns(30);
        nameField.setDescription("Введите Название компании");
        nameField.setInputPrompt("Рога и Копыта");
        nameField.setRequiredError("Название компании не может быть пустым.");
        nameField.setNullRepresentation("");
        formLayout.addComponent(nameField);

        phoneField = new PhoneField("Телефон");
        formLayout.addComponent(phoneField);

        emailField = new EmailField("E-Mail");
        formLayout.addComponent(emailField);

        wwwField = new EditField("WWW", "Введите адрес сайта компании");
        wwwField.setColumns(20);
        formLayout.addComponent(wwwField);

        regionField = new RegionSelect();
        regionField.setDescription("Укажите регион регистрации");
        regionField.addValueChangeListener(event -> {
            final String newRegion = (String) event.getProperty().getValue();
            final String city = lookup(SupplementService.class).findCityByRegion(newRegion);
            if (city != null)
                cityField.setValue(city);
        });
        formLayout.addComponent(regionField);

        cityField = new CitySelect();
        cityField.setDescription("Введите город регистрации");
        if (obj.getRegAddress().getCity() != null) cityField.addItem(obj.getRegAddress().getCity());
        cityField.addValueChangeListener(event -> {
            final String newCity = (String) event.getProperty().getValue();
            final String region = lookup(SupplementService.class).findRegionByCity(newCity);
            if (region != null)
                regionField.setValue(region);
        });
        formLayout.addComponent(cityField);

        postIndexField = new EditField("Почтовый индекс");
        postIndexField.setColumns(8);
        postIndexField.setInputPrompt("Индекс");
        postIndexField.setNullRepresentation("");
        formLayout.addComponent(postIndexField);

        streetBldField = new TextArea("Адрес");
        streetBldField.setColumns(30);
        streetBldField.setRows(2);
        streetBldField.setDescription("Почтовый адрес (улица, дом, корпус, ...)");
        streetBldField.setInputPrompt("Улица, Дом, Корпус и т.д.");
        streetBldField.setNullRepresentation("");
        formLayout.addComponent(streetBldField);
        return formLayout;
    }
}
