/**
 *
 */
package ru.extas.web.contacts;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import ru.extas.model.Contact;
import ru.extas.vaadin.addon.jdocontainer.LazyJdoContainer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Таблица контактов (физ. лица)
 *
 * @author Valery Orlov
 */
public class ContactsGrid extends CustomComponent {

    private static final long serialVersionUID = 2299363623807745654L;
    private final Table table;

    public ContactsGrid() {
        super();
        // Запрос данных
        final LazyJdoContainer<Contact> container = new LazyJdoContainer<>(Contact.class, 50, null);

        final CssLayout panel = new CssLayout();
        panel.addStyleName("layout-panel");
        panel.setSizeFull();

        // Формируем тулбар
        final HorizontalLayout commandBar = new HorizontalLayout();
        commandBar.addStyleName("configure");
        commandBar.setSpacing(true);

        final Button newBtn = new Button("Новый");
        newBtn.addStyleName("icon-user-add-1");
        newBtn.setDescription("Ввод нового Контакта в систему");
        newBtn.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unchecked")
            @Override
            public void buttonClick(final ClickEvent event) {
                final Object newObjId = table.addItem();
                final BeanItem<Contact> newObj = (BeanItem<Contact>) table.getItem(newObjId);

                final ContactEditForm editWin = new ContactEditForm("Ввод нового контакта в систему", newObj);
                editWin.addCloseListener(new CloseListener() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void windowClose(final CloseEvent e) {
                        if (editWin.isSaved()) {
                            table.select(newObjId);
                            Notification.show("Пользователь сохранен", Type.TRAY_NOTIFICATION);
                        } else {
                            table.removeItem(newObjId);
                        }
                    }
                });
                editWin.showModal();
            }
        });
        commandBar.addComponent(newBtn);

        final Button editBtn = new Button("Изменить");
        editBtn.addStyleName("icon-user-2");
        editBtn.setDescription("Редактирование контактных данных");
        editBtn.setEnabled(false);
        editBtn.addClickListener(new ClickListener() {

            private static final long serialVersionUID = 1L;

            @SuppressWarnings("unchecked")
            @Override
            public void buttonClick(final ClickEvent event) {
                final Object curObjId = checkNotNull(table.getValue(), "No selected row");
                final BeanItem<Contact> curObj = (BeanItem<Contact>) table.getItem(curObjId);

                final ContactEditForm editWin = new ContactEditForm("Редактирование контактных данных", curObj);
                editWin.addCloseListener(new CloseListener() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void windowClose(final CloseEvent e) {
                        if (editWin.isSaved()) {
                            Notification.show("Контакт сохранен", Type.TRAY_NOTIFICATION);
                        }
                    }
                });
                editWin.showModal();
            }
        });
        commandBar.addComponent(editBtn);

        panel.addComponent(commandBar);

        // Создаем таблицу скроллинга
        table = new Table();
        table.setContainerDataSource(container);
        table.setSizeFull();

        // Обеспечиваем корректную работу кнопок зависящих от выбранной записи
        table.setImmediate(true);
        table.setSelectable(true);
        table.addValueChangeListener(new ValueChangeListener() {

            private static final long serialVersionUID = 1L;

            @Override
            public void valueChange(final ValueChangeEvent event) {
                final boolean enableBtb = event.getProperty().getValue() != null;
                editBtn.setEnabled(enableBtb);
            }
        });
        // table.select(container.firstItemId());

        final ContactDataDecl ds = new ContactDataDecl();
        ds.initTableColumns(table);

        panel.addComponent(table);

        setCompositionRoot(panel);
    }
}