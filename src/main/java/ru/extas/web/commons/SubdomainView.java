package ru.extas.web.commons;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.extas.server.security.UserManagementService;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static ru.extas.server.ServiceLocator.lookup;

/**
 * <p>Abstract AbstractTabView class.</p>
 *
 * @author Valery Orlov
 *         Date: 15.10.13
 *         Time: 12:00
 *
 * @since 0.3
 */
public abstract class SubdomainView extends ExtaAbstractView {
    private final static Logger logger = LoggerFactory.getLogger(SubdomainView.class);

    private ContentContainer contentContainer;
    private TabSheet tabsheet;
    private final List<TabSheet.Tab> tabs = newArrayList();
    private Label title;

    private class ContentContainer extends VerticalLayout {
        private ExtaEditForm form;

        private ContentContainer() {
            setSizeFull();
            addStyleName(ExtaTheme.V_SCROLLABLE);
            addComponent(tabsheet);
        }

        public void switchToTabsheet() {
            if(form != null) {
                removeComponent(form);
                form = null;
            }
            tabsheet.setVisible(true);
            title.setValue(titleCaption);
        }

        public void switchToForm(final ExtaEditForm form) {
            this.form = form;
            tabsheet.setVisible(false);
            form.setWidth(100, Unit.PERCENTAGE);
            addComponent(form);
            title.setValue(form.getCaption());
        }
    }

    /**
     * <p>Constructor for AbstractTabView.</p>
     *
     * @param titleCaption a {@link java.lang.String} object.
     */
    protected SubdomainView(final String titleCaption) {
        this.titleCaption = titleCaption;
    }

    private void initTabsheet() {
        if (tabsheet == null) {
            tabsheet = new TabSheet();
            tabsheet.setSizeFull();
            contentContainer = new ContentContainer();

            final List<SubdomainInfo> subdomainInfo = getSubdomainInfo();
            final ExtaUri uri = new ExtaUri();
            if(uri.getDomain() == null){
                uri.setDomain(subdomainInfo.get(0).getDomain());
                NavigationUtils.setUriFragment(uri);
            }

            // Создаем закладки в соответствии с описанием
            final UserManagementService userService = lookup(UserManagementService.class);
            subdomainInfo.forEach(info -> {
                if (userService.isPermittedDomain(info.getDomain())) {
                    tabs.add(tabsheet.addTab(new SubdomainUI(info), info.getCaption()));
            }});
            // Create tab content dynamically when tab is selected
            tabsheet.addSelectedTabChangeListener(event -> {
                final SubdomainUI subdomainUI = (SubdomainUI) tabsheet.getSelectedTab();
                subdomainUI.show();
            });

        }
    }

    private final String titleCaption;

    private class SubdomainUI extends VerticalLayout {

        private final SubdomainInfo info;
        private ExtaGrid grid;

        public SubdomainUI(final SubdomainInfo info) {
            this.info = info;
            setSizeFull();
        }

        public void show() {
            final ExtaUri uri = new ExtaUri();

            if (grid == null) initGrid();
            else grid.refreshContainer();

            if(uri.getDomain() == info.getDomain()) {
                if(uri.getMode() == ExtaUri.Mode.GRID) {
                    if (!isNullOrEmpty(uri.getId())) {
                        grid.selectObject(uri.getId());
                    }
                } else if(uri.getMode() == ExtaUri.Mode.NEW) {
                    grid.doEditNewObject(null);
                } else if(uri.getMode() == ExtaUri.Mode.EDIT) {
                    if (!isNullOrEmpty(uri.getId())) {
                        final EntityManager em = lookup(EntityManager.class);
                        final Object entity = em.find(grid.getEntityClass(), uri.getId());
                        if (entity != null) {
                            grid.doEditObject(entity);
                        } else {
                            NotificationUtil.showWarning("Немогу открыть форму редактирования",
                                    "Ненайден объект редактирования. Возможно, неверный URL, или объект был удален.");
                        }
                    } else
                        NotificationUtil.showWarning("Немогу открыть форму редактирования",
                                "Ненайден объект редактирования. Возможно, неверный URL.");
                }
            } else
                NavigationUtils.setUriFragment(new ExtaUri(info.getDomain(), ExtaUri.Mode.GRID, null));
        }

        public void initGrid() {
            grid = info.createGrid();
            grid.setSizeFull();
            addComponent(grid);
            if(info.isEditInPage())
                grid.setFormService(new URIWrapFormService(new PageFormService()));
            else
                grid.setFormService(new URIWrapFormService(grid.getFormService()));
        }

        public SubdomainInfo getInfo() {
            return info;
        }

        private class URIWrapFormService implements ExtaGrid.FormService {
            private final ExtaGrid.FormService wrappedService;

            public URIWrapFormService(final ExtaGrid.FormService wrappedService) {
                this.wrappedService = wrappedService;
            }

            @Override
            public void open4Edit(final ExtaEditForm form) {
                final ExtaUri uri = new ExtaUri(info.getDomain(), ExtaUri.Mode.EDIT, form.getEntityId().toString());
                NavigationUtils.setUriFragment(uri);
                form.addCloseFormListener(event -> {
                    uri.setMode(ExtaUri.Mode.GRID);
                    uri.setId(null);
                    NavigationUtils.setUriFragment(uri);
                });
                wrappedService.open4Edit(form);
            }

            @Override
            public void open4Insert(final ExtaEditForm form) {
                final ExtaUri uri = new ExtaUri(info.getDomain(), ExtaUri.Mode.NEW, null);
                NavigationUtils.setUriFragment(uri);
                form.addCloseFormListener(event -> {
                    uri.setMode(ExtaUri.Mode.GRID);
                    final Object entity = form.getEntity();
                    uri.setId(null);
                    if(entity != null) {
                        grid.selectEntity(entity);
                    }
                    NavigationUtils.setUriFragment(uri);
                });
                wrappedService.open4Insert(form);
            }
        }

        private class PageFormService implements ExtaGrid.FormService {
            @Override
            public void open4Edit(final ExtaEditForm form) {
                form.addCloseFormListener(event -> {
                    if (form.isSaved()) {
                        grid.refreshContainerEntity(form.getEntity());
                    }
                    grid.selectEntity(form.getEntity());
                    contentContainer.switchToTabsheet();
                });
                contentContainer.switchToForm(form);
            }

            @Override
            public void open4Insert(final ExtaEditForm form) {
                form.addCloseFormListener(event -> {
                    if (form.isSaved()) {
                        grid.refreshContainer();
                        grid.selectEntity(form.getEntity());
                    }
                    contentContainer.switchToTabsheet();
                });
                contentContainer.switchToForm(form);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createContent() {
        logger.debug("Creating view content...");

        initTabsheet();
        final ExtaUri uri = new ExtaUri();
        final TabSheet.Tab tabCandidat = getTab4Uri(uri).orElse(tabs.get(0));

        // Делаем текущей нужную закладку
        final SubdomainUI selectedTab = (SubdomainUI) tabsheet.getSelectedTab();
        if(selectedTab.equals(tabCandidat.getComponent()))
            selectedTab.show();
        else
            tabsheet.setSelectedTab(tabCandidat);

        return contentContainer;
    }

    private Optional<TabSheet.Tab> getTab4Uri(final ExtaUri uri) {
        return tabs.stream()
                .filter(tab -> ((SubdomainUI) tab.getComponent()).getInfo().getDomain() == uri.getDomain())
                .findFirst();
    }

    /**
     * <p>getSubdomainInfo.</p>
     *
     * @return a {@link java.util.List} object.
     */
    abstract protected List<SubdomainInfo> getSubdomainInfo();

    /**
     * {@inheritDoc}
     */
    @Override
    protected Component createTitle() {
        title = new Label(titleCaption);
        title.setSizeUndefined();
        title.addStyleName(ExtaTheme.VIEW_TITLE);
        return title;
    }
}
