package ru.extas.web.contacts.salepoint;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import ru.extas.model.contacts.SalePoint;
import ru.extas.web.commons.Fontello;
import ru.extas.web.commons.ItemAction;
import ru.extas.web.commons.NotificationUtil;
import ru.extas.web.commons.UIAction;
import ru.extas.web.commons.container.ExtaBeanContainer;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Редактирует торговые точки доступные пользователю.
 *
 * @author Valery Orlov
 *         Date: 26.10.2014
 *         Time: 18:21
 */
public class SalePointsSelectField extends CustomField<Set> {

    private ExtaBeanContainer<SalePoint> beanContainer;

    public SalePointsSelectField() {
        setRequiredError("Необходимо указать хотябы одну торговую точку!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component initContent() {
        final SalePointsGrid grid = new SalePointsGrid() {

            @Override
            protected Container createContainer() {
                final Set<SalePoint> list = getValue() != null ? getValue() : newHashSet();
                beanContainer = new ExtaBeanContainer<>(SalePoint.class);
                beanContainer.addNestedContainerProperty("posAddress.regionWithType");
                beanContainer.addNestedContainerProperty("posAddress.cityWithType");
                beanContainer.addNestedContainerProperty("posAddress.value");
                beanContainer.addNestedContainerProperty("company.name");
                beanContainer.addAll(list);

                return container = beanContainer;
            }

            @Override
            protected List<UIAction> createActions() {
                final List<UIAction> actions = newArrayList();

                actions.add(new UIAction("Добавить", "Выбрать Торговую точку", Fontello.DOC_NEW) {
                    @Override
                    public void fire(final Set itemIds) {
                        final SalePointSelectWindow selectWindow = new SalePointSelectWindow("Выберите торговую точку", null);
                        selectWindow.addCloseListener(e -> {
                            if (selectWindow.isSelectPressed()) {
                                beanContainer.addAll(selectWindow.getSelected());
                                setValue(newHashSet(beanContainer.getItemIds()));
                                NotificationUtil.showSuccess("Торговая точка добавлена");
                            }
                        });
                        selectWindow.showModal();
                    }
                });

                actions.add(new EditObjectAction("Изменить", "Редактирование данных торговой точки"));
                actions.add(new ItemAction("Удалить", "Убрать торговую точку из списка", Fontello.TRASH) {
                    @Override
                    public void fire(final Set itemIds) {
                        itemIds.forEach(id -> beanContainer.removeItem(id));
                        setValue(newHashSet(beanContainer.getItemIds()));
                    }
                });
                return actions;
            }
        };
        grid.setSizeFull();
        grid.setReadOnly(isReadOnly());

        return grid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends Set> getType() {
        return Set.class;
    }

}
