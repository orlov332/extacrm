package ru.extas.web.contacts;

import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import ru.extas.model.contacts.Person;
import ru.extas.web.commons.ExtaDataContainer;
import ru.extas.web.commons.ExtaTheme;
import ru.extas.web.commons.Fontello;
import ru.extas.web.commons.FormUtils;
import ru.extas.web.commons.converters.PhoneConverter;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * Выбор контакта - физ. лица
 * с возможностью добавления нового
 * <p>
 * Date: 12.09.13
 * Time: 12:15
 *
 * @author Valery Orlov
 * @version $Id: $Id
 * @since 0.3
 */
public class PersonSelect extends CustomField<Person> {

    private Label emailField;
    private Label birthdayField;
    private Label phoneField;
    private Button viewBtn;

    /**
     * <p>Constructor for PersonSelect.</p>
     *
     * @param caption a {@link java.lang.String} object.
     */
    public PersonSelect(final String caption) {
        this(caption, "");
    }

    /**
     * <p>Constructor for PersonSelect.</p>
     *
     * @param caption     a {@link java.lang.String} object.
     * @param description a {@link java.lang.String} object.
     */
    public PersonSelect(final String caption, final String description) {
        setCaption(caption);
        setDescription(description);
        setBuffered(true);
        addStyleName(ExtaTheme.BORDERED_COMPONENT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component initContent() {

        final VerticalLayout container = new VerticalLayout();
        container.setSpacing(true);

        final CssLayout nameLay = new CssLayout();
        nameLay.addStyleName(ExtaTheme.LAYOUT_COMPONENT_GROUP);

        if (!isReadOnly()) {
            final PersonSelectField personSelectField = new PersonSelectField("", "Введите или выберите имя контакта");
            personSelectField.setInputPrompt("Фамилия Имя Отчество");
            personSelectField.setPropertyDataSource(getPropertyDataSource());
            personSelectField.setNewItemsAllowed(true);
            personSelectField.setNewItemHandler(new AbstractSelect.NewItemHandler() {
                private static final long serialVersionUID = 1L;

                @SuppressWarnings({"unchecked"})
                @Override
                public void addNewItem(final String newItemCaption) {
                    final Person newObj = new Person();
                    newObj.setName(newItemCaption);

                    final String edFormCaption = "Ввод нового контакта в систему";
                    final PersonEditForm editWin = new PersonEditForm(newObj);
                    editWin.setModified(true);

                    editWin.addCloseFormListener(event -> {
                        if (editWin.isSaved()) {
                            personSelectField.refreshContainer();
                            personSelectField.setValue(editWin.getObjectId());
                        }
                    });
                    FormUtils.showModalWin(editWin);
                }
            });
            personSelectField.addValueChangeListener(event -> refreshFields((Person) personSelectField.getConvertedValue()));
            nameLay.addComponent(personSelectField);

            final Button searchBtn = new Button("Поиск", event -> {

                final PersonSelectWindow selectWindow = new PersonSelectWindow("Выберите клиента или введите нового");
                selectWindow.addCloseListener(e -> {
                    if (selectWindow.isSelectPressed()) {
                        final Person selected = selectWindow.getSelected();
                        personSelectField.setConvertedValue(selected);
                    }
                });
                selectWindow.showModal();

            });
            searchBtn.setIcon(Fontello.SEARCH_OUTLINE);
            searchBtn.addStyleName(ExtaTheme.BUTTON_ICON_ONLY);
            nameLay.addComponent(searchBtn);

            viewBtn = new Button("Просмотр", event -> {
                final Person bean = (Person) getPropertyDataSource().getValue();

                final PersonEditForm editWin = new PersonEditForm(bean);
                editWin.setReadOnly(isReadOnly());

                editWin.addCloseFormListener(event1 -> {
                    if (editWin.isSaved()) {
                        refreshFields(bean);
                    }
                });
                FormUtils.showModalWin(editWin);
            });
            viewBtn.setIcon(Fontello.EDIT_3);
            viewBtn.addStyleName(ExtaTheme.BUTTON_ICON_ONLY);
            nameLay.addComponent(viewBtn);

        } else {
            final Label name = new Label();
            final Person person = (Person) getPropertyDataSource().getValue();
            if(person != null)
                name.setValue(person.getName());
            nameLay.addComponent(name);
        }

        container.addComponent(nameLay);

        final HorizontalLayout fieldsContainer = new HorizontalLayout();
        fieldsContainer.setSpacing(true);
        // Дата рождения
        birthdayField = new Label();
        birthdayField.setCaption("Дата рождения");
        fieldsContainer.addComponent(birthdayField);
        // Телефон
        phoneField = new Label();
        phoneField.setCaption("Телефон");
        phoneField.setConverter(lookup(PhoneConverter.class));
        fieldsContainer.addComponent(phoneField);
        // Мыло
        emailField = new Label();
        emailField.setCaption("E-Mail");
        fieldsContainer.addComponent(emailField);
        refreshFields((Person) getPropertyDataSource().getValue());

        container.addComponent(fieldsContainer);

        return container;
    }

    private void refreshFields(Person person) {
        setValue(person);

        if (viewBtn != null) {
            viewBtn.setEnabled(person != null);
        }
        if (person == null) {
            person = new Person();
        }

        final BeanItem<Person> personItem = new BeanItem<>(person);
        // Дата рождения
        birthdayField.setPropertyDataSource(personItem.getItemProperty("birthday"));
        // Телефон
        phoneField.setPropertyDataSource(personItem.getItemProperty("phone"));
        // Мыло
        emailField.setPropertyDataSource(personItem.getItemProperty("email"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Person> getType() {
        return Person.class;
    }

    private class PersonSelectField extends ComboBox {

        private static final long serialVersionUID = -8005905898383483037L;
        protected ExtaDataContainer<Person> container;

        protected PersonSelectField(final String caption) {
            this(caption, "Выберите существующий контакт или введите новый");
        }

        protected PersonSelectField(final String caption, final String description) {
                super(caption);

                // Преконфигурация
                setDescription(description);
                setInputPrompt("контакт...");
                setWidth(25, Unit.EM);
                setImmediate(true);

                // Инициализация контейнера
                container = new ExtaDataContainer<>(Person.class);

                // Устанавливаем контент выбора
                setFilteringMode(FilteringMode.CONTAINS);
                setContainerDataSource(container);
                setItemCaptionMode(ItemCaptionMode.PROPERTY);
                setItemCaptionPropertyId("name");
                setConverter(new SingleSelectConverter<Person>(this));

                // Функционал добавления нового контакта
                setNullSelectionAllowed(false);
                setNewItemsAllowed(false);
            }

            /**
             * <p>refreshContainer.</p>
             */
        public void refreshContainer() {
            container.refresh();
        }
    }
}
