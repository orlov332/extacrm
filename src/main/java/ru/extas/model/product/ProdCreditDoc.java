package ru.extas.model.product;

import ru.extas.model.common.AuditedObject;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Необходимый документ в кредитном продукте.
 * Определяет список необходимых документов.
 *
 * @author Valery Orlov
 *         Date: 07.02.14
 *         Time: 14:43
 *
 * @since 0.3
 */
@Entity
@Table(name = "PROD_CREDIT_DOC")
public class ProdCreditDoc extends AuditedObject {

	// Вид документа:
	// Паспорт
	// ПТС
	// СТС
	// загранпаспорт
	// полис ДМС
	// справка 2НДФЛ
	// водительское удостоверение
	// СНИЛС
	@Column(name = "NAME")
    @Size(max = 255)
	private String name;

	// Признак обязательного документа
	@Column(name = "IS_REQUIRED")
	private boolean required;

	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
	private ProdCredit product;

	/**
	 * <p>Constructor for ProdCreditDoc.</p>
	 */
	public ProdCreditDoc() {
	}

	/**
	 * <p>Constructor for ProdCreditDoc.</p>
	 *
	 * @param product a {@link ProdCredit} object.
	 */
	public ProdCreditDoc(final ProdCredit product) {
		this.product = product;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>Setter for the field <code>name</code>.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * <p>isRequired.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * <p>Setter for the field <code>required</code>.</p>
	 *
	 * @param required a boolean.
	 */
	public void setRequired(final boolean required) {
		this.required = required;
	}

	/**
	 * <p>Getter for the field <code>product</code>.</p>
	 *
	 * @return a {@link ProdCredit} object.
	 */
	public ProdCredit getProduct() {
		return product;
	}

	/**
	 * <p>Setter for the field <code>product</code>.</p>
	 *
	 * @param product a {@link ProdCredit} object.
	 */
	public void setProduct(final ProdCredit product) {
		this.product = product;
	}
}
