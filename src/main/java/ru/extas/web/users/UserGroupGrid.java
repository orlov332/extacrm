package ru.extas.web.users;

import com.vaadin.data.Container;
import ru.extas.model.security.UserGroup;
import ru.extas.web.commons.*;
import ru.extas.web.commons.container.ExtaDbContainer;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Грид управляет группами пользователей
 *
 * @author Valery Orlov
 *         Date: 20.06.2014
 *         Time: 10:48
 *
 * @since 0.5.0
 */
public class UserGroupGrid extends ExtaGrid<UserGroup> {
    public UserGroupGrid() {
        super(UserGroup.class);
    }

    @Override
    public ExtaEditForm<UserGroup> createEditForm(final UserGroup userGroup, final boolean isInsert) {
        return new UserGroupEditForm(userGroup);
    }

    /** {@inheritDoc} */
    @Override
    protected GridDataDecl createDataDecl() {
        return new UserGroupDataDecl();
    }

    /** {@inheritDoc} */
    @Override
    protected Container createContainer() {
        // Запрос данных
        final ExtaDbContainer<UserGroup> container = new ExtaDbContainer<>(UserGroup.class);
        return container;
    }

    /** {@inheritDoc} */
    @Override
    protected List<UIAction> createActions() {
        final List<UIAction> actions = newArrayList();

        actions.add(new NewObjectAction("Новая", "Ввод новой группы пользователей в систему", Fontello.USER_ADD));
        actions.add(new EditObjectAction("Изменить", "Редактирование группы", Fontello.USER_1));

        actions.add(new ItemAction("Копировать", "Копировать текущую группу в новую запись", Fontello.DOCS) {
            @Override
            public void fire(final Set itemIds) {
                final UserGroup curObj = getFirstEntity(itemIds);

                final UserGroup copy = curObj.createCopy();
                copy.setName("Копия - " + curObj.getName());
                doEditNewObject(copy);
            }
        });
        return actions;
    }
}
