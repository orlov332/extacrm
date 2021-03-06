package ru.extas.web.contacts.salepoint;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.viritin.layouts.MVerticalLayout;
import ru.extas.model.contacts.Company;
import ru.extas.model.contacts.SalePoint;
import ru.extas.model.contacts.SalePointComment;
import ru.extas.model.security.CuratorsGroup;
import ru.extas.model.security.UserRole;
import ru.extas.server.contacts.SalePointRepository;
import ru.extas.server.security.UserManagementService;
import ru.extas.server.settings.UserSettingsService;
import ru.extas.utils.SupplierSer;
import ru.extas.web.commons.CommentsField;
import ru.extas.web.commons.ExtaEditForm;
import ru.extas.web.commons.ExtaTheme;
import ru.extas.web.commons.NotificationUtil;
import ru.extas.web.commons.component.*;
import ru.extas.web.commons.component.address.AddressSuggestingComboBox;
import ru.extas.web.contacts.company.CompanyField;
import ru.extas.web.contacts.employee.EmployeeFieldMulty;
import ru.extas.web.contacts.legalentity.LegalEntitiesSelectField;
import ru.extas.web.contacts.legalentity.LegalEntityEditForm;
import ru.extas.web.users.CuratorsGroupField;

import java.text.MessageFormat;
import java.util.Optional;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * <p>SalePointEditForm class.</p>
 *
 * @author Valery Orlov
 *         Date: 19.02.14
 *         Time: 13:08
 *
 * @since 0.3
 */
public class SalePointEditForm extends ExtaEditForm<SalePoint> {

    private static final long serialVersionUID = -7787385620289376599L;
    private final static Logger logger = LoggerFactory.getLogger(LegalEntityEditForm.class);
    private SupplierSer<Company> companySupplier;

    // Компоненты редактирования

    // Вкладка - "Общая информация"
    @PropertyId("company")
    private CompanyField companyField;
    @PropertyId("name")
    private EditField nameField;
    @PropertyId("phone")
    private PhoneField phoneField;
    @PropertyId("email")
    private EmailField emailField;
    @PropertyId("www")
    private WebSiteLinkField wwwField;

    @PropertyId("posAddress")
    private AddressSuggestingComboBox posAddressComboBox;

    @PropertyId("legalEntities")
    private LegalEntitiesSelectField legalsField;
    @PropertyId("employees")
    private EmployeeFieldMulty employeesField;

    @PropertyId("alphaCode")
    private EditField alphaCodeField;
    @PropertyId("homeCode")
    private EditField homeCodeField;
    @PropertyId("setelemCode")
    private EditField setelemCodeField;
    @PropertyId("extaCode")
    private EditField extaCodeField;

    @PropertyId("curatorsGroup")
    private CuratorsGroupField curatorField;

    @PropertyId("comments")
    private CommentsField<SalePointComment> commentsField;
    @PropertyId("apiExpose")
    private CheckBox apiExposeField;


    public SalePointEditForm(final SalePoint salePoint) {
        super(salePoint.isNew() ? "Ввод новой торговой точки в систему" :
                MessageFormat.format("Редактирование данных торговой точки: {0}", salePoint.getName()), salePoint);

        setWinWidth(800, Unit.PIXELS);
        setWinHeight(600, Unit.PIXELS);

        addAttachListener(e -> {
            if (getEntity().getCompany() == null) {
                companyField.setReadOnly(false);
                companyField.setVisible(true);
                companyField.setRequired(true);
            } else {
                companyField.setReadOnly(true);
                companyField.getPropertyDataSource().setReadOnly(true);
                if (getEntity().getCompany().isNew()) {
                    companyField.setVisible(false);
                    companyField.setRequired(false);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initEntity(final SalePoint salePoint) {
        if (salePoint.isNew()) {
            // Инициализируем новый объект
            if (companySupplier != null)
                salePoint.setCompany(companySupplier.get());
        }
/*        if (salePoint.getRegAddress() == null)
            salePoint.setRegAddress(new AddressInfo());*/
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected SalePoint saveEntity(SalePoint salePoint) {
        if (!salePoint.getCompany().isNew()) {
            logger.debug("Saving contact data...");
            final SalePointRepository contactRepository = lookup(SalePointRepository.class);
            salePoint = contactRepository.secureSave(salePoint);
            NotificationUtil.showSuccess("Торговая точка сохранена");
        }
        return salePoint;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected ComponentContainer createEditFields() {
        final ConfirmTabSheet tabsheet = new ConfirmTabSheet(
                () -> !getEntity().isNew(),
                () -> save());
        tabsheet.setSizeFull();

        // Вкладка - "Общая информация"
        final FormLayout mainForm = createMainForm();
        tabsheet.addTab(mainForm).setCaption("Общие данные");

        // Вкладка - "Сотрудники"
        final Component employeesForm = createEmployeesForm();
        tabsheet.addConfirmTab(employeesForm, "Сотрудники");

        // Вкладка - "Юр.лица"
        final Component legalsForm = createLegalsForm();
        tabsheet.addConfirmTab(legalsForm, "Юридические лица");

        // Вкладка - "Идентификация"
        final Component identityForm = createIdentityForm();
        final TabSheet.Tab idTab = tabsheet.addTab(identityForm);
        idTab.setCaption("Идентификация");
        idTab.setVisible(lookup(UserSettingsService.class).isShowSalePointIds());

        // Вкладка - "Комментарии"
        if (isCommentAvailable()) {
            final Component commentsForm = createCommentsForm();
            tabsheet.addTab(commentsForm).setCaption("Комментарии");
        }

        return tabsheet;
    }

    private boolean isCommentAvailable() {
        final UserManagementService userManagementService = lookup(UserManagementService.class);
        final boolean isAdmin = userManagementService.isCurUserHasRole(UserRole.ADMIN);

        boolean isCurator = false;
        final SalePoint salePoint = getEntity();
        final CuratorsGroup curatorsGroup = salePoint.getCuratorsGroup();
        if(curatorsGroup != null) {
            isCurator = curatorsGroup.getCurators().contains(userManagementService.getCurrentUserEmployee());
        }

        return isAdmin || isCurator;
    }

    private Component createCommentsForm() {
        final Panel panel = new Panel();
        panel.setSizeFull();
        panel.addStyleName(ExtaTheme.PANEL_BORDERLESS);

        commentsField = new CommentsField<>(SalePointComment.class);
        commentsField.addValueChangeListener(forceModified);
        panel.setContent(commentsField);

        return new MVerticalLayout(panel).withMargin(true);
    }

    private Component createIdentityForm() {
        final FormLayout formLayout = new ExtaFormLayout();
        formLayout.setMargin(true);
        formLayout.setSizeFull();

        extaCodeField = new EditField("Код Экстрим Ассистанс", "Введите идентификационный Код Экстрим Ассистанс");
        extaCodeField.setColumns(20);
        formLayout.addComponent(extaCodeField);

        alphaCodeField = new EditField("Код Альфа Банка", "Введите идентификационный Код Альфа Банка");
        alphaCodeField.setColumns(20);
        formLayout.addComponent(alphaCodeField);

        homeCodeField = new EditField("Код HomeCredit Банка", "Введите идентификационный Код HomeCredit Банка");
        homeCodeField.setColumns(20);
        formLayout.addComponent(homeCodeField);

        setelemCodeField = new EditField("Код Банка СЕТЕЛЕМ", "Введите идентификационный Код Банка СЕТЕЛЕМ");
        setelemCodeField.setColumns(20);
        formLayout.addComponent(setelemCodeField);

        return formLayout;
    }

    private Component createLegalsForm() {
        legalsField = new LegalEntitiesSelectField();
        legalsField.addValueChangeListener(forceModified);
        legalsField.setCompanySupplier(Optional.ofNullable(companySupplier).orElse(() -> companyField.getValue()));
        legalsField.setSizeFull();
        return legalsField;
    }

    private Component createEmployeesForm() {
        employeesField = new EmployeeFieldMulty();
        employeesField.setCompanySupplier(Optional.ofNullable(companySupplier).orElse(() -> companyField.getValue()));
        employeesField.setSalePointSupplier(super::getEntity);
        employeesField.setSizeFull();
        return employeesField;
    }

    private FormLayout createMainForm() {
        final FormLayout formLayout = new ExtaFormLayout();
        formLayout.setMargin(true);
        formLayout.setSizeFull();

        nameField = new EditField("Название");
        nameField.setRequired(true);
        nameField.setImmediate(true);
        nameField.setColumns(30);
        nameField.setDescription("Введите Название торговой точки");
        nameField.setInputPrompt("Рога и Копыта - Север");
        nameField.setRequiredError("Название торговой точки не может быть пустым.");
        nameField.setNullRepresentation("");
        formLayout.addComponent(nameField);

        companyField = new CompanyField("Компания", "Введите или выберите компанию которой принадлежит торговая точка");
        companyField.setRequired(true);
        formLayout.addComponent(companyField);

        phoneField = new PhoneField("Телефон");
        formLayout.addComponent(phoneField);

        emailField = new EmailField("E-Mail");
        formLayout.addComponent(emailField);

        wwwField = new WebSiteLinkField("WWW", "Введите адрес сайта торговой точки");
        formLayout.addComponent(wwwField);

        posAddressComboBox = new AddressSuggestingComboBox();
        formLayout.addComponent(posAddressComboBox);

/*        regAddressField = new AddressInfoField();
        formLayout.addComponent(regAddressField);*/

        if(lookup(UserManagementService.class).isCurUserHasRole(UserRole.ADMIN)) {
            curatorField = new CuratorsGroupField("Группа кураторов ЭА", "Укажите группу кураторов торговой точки со стороны Экстрим Ассистанс");
            formLayout.addComponent(curatorField);

            apiExposeField = new CheckBox("Видна через внешнее API");
            formLayout.addComponent(apiExposeField);
        }

        return formLayout;
    }

    public SupplierSer<Company> getCompanySupplier() {
        return companySupplier;
    }

    public void setCompanySupplier(final SupplierSer<Company> companySupplier) {
        this.companySupplier = companySupplier;
    }
}
