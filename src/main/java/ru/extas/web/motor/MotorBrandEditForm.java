package ru.extas.web.motor;

import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import ru.extas.model.motor.MotorBrand;
import ru.extas.server.motor.MotorBrandRepository;
import ru.extas.web.commons.component.EditField;
import ru.extas.web.commons.window.AbstractEditForm;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * Created by Valery on 04.06.2014.
 *
 * @author Valery_2
 * @version $Id: $Id
 * @since 0.5.0
 */
public class MotorBrandEditForm extends AbstractEditForm<MotorBrand> {
    @PropertyId("name")
    private EditField nameField;

    @PropertyId("brandTypes")
    private MotorTypeObjMultiselect typesField;

    /**
     * <p>Constructor for MotorBrandEditForm.</p>
     *
     * @param caption a {@link java.lang.String} object.
     * @param newObj a {@link com.vaadin.data.util.BeanItem} object.
     */
    public MotorBrandEditForm(String caption, BeanItem<MotorBrand> newObj) {
        super(caption, newObj);
    }



    /** {@inheritDoc} */
    @Override
    protected void initObject(MotorBrand obj) {
    }

    /** {@inheritDoc} */
    @Override
    protected void saveObject(MotorBrand obj) {
        MotorBrand loc = lookup(MotorBrandRepository.class).save(obj);
        Notification.show("Марка сохранена", Notification.Type.TRAY_NOTIFICATION);
    }

    /** {@inheritDoc} */
    @Override
    protected void checkBeforeSave(MotorBrand obj) {

    }

    /** {@inheritDoc} */
    @Override
    protected ComponentContainer createEditFields(MotorBrand obj) {
        final FormLayout form = new FormLayout();

        nameField = new EditField("Название марки техники", "Введите название марки техники");
        nameField.setColumns(20);
        nameField.setRequired(true);
        form.addComponent(nameField);

        typesField = new MotorTypeObjMultiselect("Выпускаемая техника");
        form.addComponent(typesField);

        return form;
    }
}