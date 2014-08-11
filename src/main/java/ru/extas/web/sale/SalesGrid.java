package ru.extas.web.sale;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Window;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import ru.extas.model.sale.Sale;
import ru.extas.model.security.ExtaDomain;
import ru.extas.web.bpm.BPStatusForm;
import ru.extas.web.commons.*;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static ru.extas.server.ServiceLocator.lookup;
import static ru.extas.web.commons.GridItem.extractBean;

/**
 * <p>SalesGrid class.</p>
 *
 * @author Valery Orlov
 *         Date: 15.10.13
 *         Time: 12:24
 * @version $Id: $Id
 * @since 0.3
 */
public class SalesGrid extends ExtaGrid {
	private static final long serialVersionUID = 4876073256421755574L;
	private final ExtaDomain domain;

	/**
	 * <p>Constructor for SalesGrid.</p>
	 *
	 * @param domain a {@link ru.extas.model.security.ExtaDomain} object.
	 */
	public SalesGrid(ExtaDomain domain) {
		super(false);
		this.domain = domain;
		initialize();
	}

	/** {@inheritDoc} */
	@Override
	protected GridDataDecl createDataDecl() {
		return new SaleDataDecl();
	}

	/** {@inheritDoc} */
	@Override
	protected void initTable(Mode mode) {
		super.initTable(mode);
		if (domain == ExtaDomain.SALES_CANCELED)
			table.setColumnCollapsed("result", false);
	}

	/** {@inheritDoc} */
	@Override
	protected Container createContainer() {
		// Запрос данных
		final ExtaDataContainer<Sale> container = new SecuredDataContainer<>(Sale.class, domain);
		container.addNestedContainerProperty("client.name");
		container.addNestedContainerProperty("client.phone");
		container.addNestedContainerProperty("dealer.name");
		container.addContainerFilter(new Compare.Equal("status",
                domain == ExtaDomain.SALES_CANCELED ? Sale.Status.CANCELED :
                domain == ExtaDomain.SALES_OPENED ? Sale.Status.NEW : Sale.Status.FINISHED));
		return container;
	}

	/** {@inheritDoc} */
	@Override
	protected List<UIAction> createActions() {
		List<UIAction> actions = newArrayList();

		actions.add(new UIAction("Новый", "Ввод новой продажи", Fontello.DOC_NEW) {
			@Override
			public void fire(Object itemId) {
				final BeanItem<Sale> newObj = new BeanItem<>(new Sale());

				final SaleEditForm editWin = new SaleEditForm("Ввод новой продажи в систему", newObj);
				editWin.addCloseListener(new Window.CloseListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(final Window.CloseEvent e) {
						if (editWin.isSaved()) {
							refreshContainer();
						}
					}
				});
				editWin.showModal();
			}
		});

		actions.add(new DefaultAction("Изменить", "Редактировать выделенную в списке продажу", Fontello.EDIT_3) {
			@Override
			public void fire(final Object itemId) {
				final BeanItem<Sale> curObj = new GridItem<>(table.getItem(itemId));

				final SaleEditForm editWin = new SaleEditForm("Редактирование продажи", curObj);
				editWin.addCloseListener(new Window.CloseListener() {

					private static final long serialVersionUID = 1L;

					@Override
					public void windowClose(final Window.CloseEvent e) {
						if (editWin.isSaved()) {
							refreshContainerItem(itemId);
						}
					}
				});
				editWin.showModal();
			}
		});

		actions.add(new ItemAction("Статус БП", "Показать панель статуса бизнес процесса к которому привязана текущая продажа", Fontello.SITEMAP) {
			@Override
			public void fire(Object itemId) {
				final Sale curObj = extractBean(table.getItem(itemId));

				// Ищем процесс к которому привязана текущая продажа
				RuntimeService runtimeService = lookup(RuntimeService.class);
				ProcessInstance process =
						runtimeService.createProcessInstanceQuery()
								.includeProcessVariables()
								.variableValueEquals("sale", curObj)
								.singleResult();

				if (process != null) {
					// Показать статус выполнения процесса
					BPStatusForm statusForm = new BPStatusForm(process.getProcessInstanceId());
					statusForm.showModal();
				} else {
                    NotificationUtil.showWarning("Нет бизнес процесса с которым связана текущая продажа.");
				}
			}
		});

		return actions;
	}

}
