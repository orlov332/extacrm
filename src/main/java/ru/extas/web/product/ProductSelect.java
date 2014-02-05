/**
 *
 */
package ru.extas.web.product;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.ComboBox;
import ru.extas.model.Policy;
import ru.extas.model.Product;
import ru.extas.server.ProductRepository;

import java.util.List;

import static ru.extas.server.ServiceLocator.lookup;

/**
 * Компонент выбора продукта
 *
 * @author Valery Orlov
 */
public class ProductSelect extends ComboBox {

	private static final long serialVersionUID = 6004206917183679455L;

	@Override
	public Class<Policy> getType() {
		return Policy.class;
	}

	public ProductSelect(final String caption, final String description, final Product product) {
		super(caption);

		// Преконфигурация
		setDescription(description);
		setInputPrompt("Выберите продукт");
		setWidth(20, Unit.EM);
		setImmediate(true);
		setNullSelectionAllowed(false);

		// Инициализация контейнера
		final ProductRepository productRepository = lookup(ProductRepository.class);
		final List<Product> products = productRepository.findByActive(true);
		final BeanItemContainer<Product> clientsCont = new BeanItemContainer<>(Product.class);
		clientsCont.addAll(products);
		if (product != null) {
			final Product forceProduct = productRepository.findOne(product.getId());
			clientsCont.addBean(forceProduct);
		}

		// Устанавливаем контент выбора
		setFilteringMode(FilteringMode.CONTAINS);
		setContainerDataSource(clientsCont);
		setItemCaptionMode(ItemCaptionMode.PROPERTY);
		setItemCaptionPropertyId("name");
	}

}