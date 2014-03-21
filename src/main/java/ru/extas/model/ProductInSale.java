package ru.extas.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Связь продукта с продажей (продажа продукта)
 *
 * @author Valery Orlov
 *         Date: 21.01.14
 *         Time: 12:16
 * @version $Id: $Id
 * @since 0.3
 */
@Entity
@Table(name = "PRODUCT_IN_SALE")
public class ProductInSale extends AbstractExtaObject {

	// Продажа
	@ManyToOne(optional = false)
	private Sale sale;

	// Продукт
	@OneToOne
	private Product product;

	// Сумма
	@Column(name = "SUMM", precision = 32, scale = 4)
	private BigDecimal summ;

	// Срок
	@Column(name = "PERIOD")
	private int period;

	// первый взнос
	@Column(name = "DOWNPAYMENT", precision = 32, scale = 4)
	private BigDecimal downpayment;

	/**
	 * <p>Constructor for ProductInSale.</p>
	 */
	public ProductInSale() {
	}

	/**
	 * <p>Constructor for ProductInSale.</p>
	 *
	 * @param sale a {@link ru.extas.model.Sale} object.
	 */
	public ProductInSale(final Sale sale) {
		this.sale = sale;
	}

	/**
	 * <p>Getter for the field <code>downpayment</code>.</p>
	 *
	 * @return a {@link java.math.BigDecimal} object.
	 */
	public BigDecimal getDownpayment() {
		return downpayment;
	}

	/**
	 * <p>Setter for the field <code>downpayment</code>.</p>
	 *
	 * @param downpayment a {@link java.math.BigDecimal} object.
	 */
	public void setDownpayment(final BigDecimal downpayment) {
		this.downpayment = downpayment;
	}

	/**
	 * <p>Getter for the field <code>sale</code>.</p>
	 *
	 * @return a {@link ru.extas.model.Sale} object.
	 */
	public Sale getSale() {
		return sale;
	}

	/**
	 * <p>Setter for the field <code>sale</code>.</p>
	 *
	 * @param sale a {@link ru.extas.model.Sale} object.
	 */
	public void setSale(final Sale sale) {
		this.sale = sale;
	}

	/**
	 * <p>Getter for the field <code>product</code>.</p>
	 *
	 * @return a {@link ru.extas.model.Product} object.
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * <p>Setter for the field <code>product</code>.</p>
	 *
	 * @param product a {@link ru.extas.model.Product} object.
	 */
	public void setProduct(final Product product) {
		this.product = product;
	}

	/**
	 * <p>Getter for the field <code>summ</code>.</p>
	 *
	 * @return a {@link java.math.BigDecimal} object.
	 */
	public BigDecimal getSumm() {
		return summ;
	}

	/**
	 * <p>Setter for the field <code>summ</code>.</p>
	 *
	 * @param summ a {@link java.math.BigDecimal} object.
	 */
	public void setSumm(final BigDecimal summ) {
		this.summ = summ;
	}

	/**
	 * <p>Getter for the field <code>period</code>.</p>
	 *
	 * @return a int.
	 */
	public int getPeriod() {
		return period;
	}

	/**
	 * <p>Setter for the field <code>period</code>.</p>
	 *
	 * @param period a int.
	 */
	public void setPeriod(final int period) {
		this.period = period;
	}

}
