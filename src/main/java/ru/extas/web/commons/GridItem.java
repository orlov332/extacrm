package ru.extas.web.commons;

import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;

/**
 * Позволяет создать элемент редактирования по EntityItem или BeanItem
 *
 * @author Valery Orlov
 *         Date: 19.02.14
 *         Time: 21:52
 * @version $Id: $Id
 * @since 0.3
 */
public class GridItem<BT> extends BeanItem<BT> {

	/**
	 * <p>Constructor for GridItem.</p>
	 *
	 * @param item a {@link com.vaadin.data.Item} object.
	 */
	public GridItem(Item item) {
		super(GridItem.<BT>extractBean(item));
	}

	/**
	 * <p>extractBean.</p>
	 *
	 * @param item a {@link com.vaadin.data.Item} object.
	 * @param <BT> a BT object.
	 * @return a BT object.
	 */
	public static <BT> BT extractBean(final Item item) {
		if (item instanceof EntityItem) {
			return ((EntityItem<BT>) item).getEntity();
		} else {
			return ((BeanItem<BT>) item).getBean();
		}
	}

}
