package ru.extas.model.contacts;

import ru.extas.model.common.IdentifiedObject;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Автотранспорт во владении физ. лица
 *
 * @author Valery Orlov
 *         Date: 11.09.2014
 *         Time: 3:37
 */
@Entity
@Table(name = "PERSON_AUTO")
public class PersonAuto extends IdentifiedObject {

    // Марка, модель
    @Column(name = "BRAND_MODEL", length = 50)
    @Size(max = 50)
    private String brandModel;
    // год выпуска
    @Column(name = "YEAR_OF_MANUFACTURE")
    private int yearOfManufacture;
    // Гос. рег. №
    @Column(name = "REG_NUM", length = 12)
    @Size(max = 12)
    private String regNum;
    // Покупная стоимость
    @Column(precision = 32, scale = 4)
    private BigDecimal price;
    // Дата приобретения
    @Column(name = "PURCHASE_DATE")
    private LocalDate purchaseDate;
    // Способ приобретения (покупка, покупка с пробегом, автокредит, покупка по ген. довер.)
    @Column(name = "WAY_2_PURCHASE", length = 50)
    @Size(max = 50)
    private String way2purchase;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private Person owner;

    public PersonAuto() {
    }

    public PersonAuto(final Person owner) {
        this.owner = owner;
    }

    public String getBrandModel() {
        return brandModel;
    }

    public void setBrandModel(final String brandModel) {
        this.brandModel = brandModel;
    }

    public int getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(final int yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(final String regNum) {
        this.regNum = regNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(final LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWay2purchase() {
        return way2purchase;
    }

    public void setWay2purchase(final String way2purchase) {
        this.way2purchase = way2purchase;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(final Person owner) {
        this.owner = owner;
    }
}
