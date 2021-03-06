package ru.extas.model.contacts;

import org.hibernate.validator.constraints.URL;
import ru.extas.model.common.Address;
import ru.extas.model.common.Comment;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Данные контакта - физ. лица
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
@Entity
@Table(name = "COMPANY")
public class Company extends Contact {

    private static final long serialVersionUID = -5681940552175752858L;

    private static final int CATEGORY_LENGTH = 50;

    // Сайт
    @Column(length = Contact.WWW_LENGTH)
    @Size(max = Contact.WWW_LENGTH)
    @URL
    private String facebook;

    // Сайт
    @Column(length = Contact.WWW_LENGTH)
    @Size(max = Contact.WWW_LENGTH)
    @URL
    private String bk;

    // Сайт
    @Column(length = Contact.WWW_LENGTH)
    @Size(max = Contact.WWW_LENGTH)
    @URL
    private String instagram;

    // Сайт
    @Column(length = Contact.WWW_LENGTH)
    @Size(max = Contact.WWW_LENGTH)
    @URL
    private String youtube;

    // Регион
    @Column(length = Address.REGION_WITH_TYPE_LEN)
    @Size(max = Address.REGION_WITH_TYPE_LEN)
    private String region;

    // Город
    @Column(length = Address.REGION_WITH_TYPE_LEN)
    @Size(max = Address.REGION_WITH_TYPE_LEN)
    private String city;

    // Собственник(и) Компании
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "COMPANY_OWNER",
            joinColumns = {@JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "OWNER_ID", referencedColumnName = "ID")})
    @OrderBy("name ASC")
    private Set<Employee> owners = newHashSet();

    // Сотрудники компании
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<Employee> employees = newHashSet();

    // Юридические лица компании
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<LegalEntity> legalEntities = newHashSet();

    // Торговые точки компании
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    private Set<SalePoint> salePoints = newHashSet();

    // Привязка обхекта к категориям
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "COMPANY_CATEGORY",
            joinColumns = {@JoinColumn(name = "COMPANY_ID")},
            indexes = {
                    @Index(columnList = "COMPANY_ID, CATEGORY")
            })
    @Column(name = "CATEGORY", length = CATEGORY_LENGTH)
    @OrderBy("CATEGORY ASC")
    private Set<String> categories = newHashSet();

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Comment.OWNER_ID_COLUMN)
    @OrderBy("createdDate")
    private List<CompanyPrivateComment> privateComments = newArrayList();


    /**
     * <p>Constructor for Company.</p>
     */
    public Company() {
    }

    public List<CompanyPrivateComment> getPrivateComments() {
        return privateComments;
    }

    public void setPrivateComments(final List<CompanyPrivateComment> privateComments) {
        this.privateComments = privateComments;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(final String facebook) {
        this.facebook = facebook;
    }

    public String getBk() {
        return bk;
    }

    public void setBk(final String bk) {
        this.bk = bk;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(final String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(final String youtube) {
        this.youtube = youtube;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(final Set<String> categories) {
        this.categories = categories;
    }

    /**
     * <p>Getter for the field <code>owners</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public Set<Employee> getOwners() {
        return owners;
    }

    /**
     * <p>Setter for the field <code>owners</code>.</p>
     *
     * @param ownerList a {@link java.util.List} object.
     */
    public void setOwners(final Set<Employee> ownerList) {
        this.owners = ownerList;
    }

    /**
     * <p>Getter for the field <code>salePoints</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public Set<SalePoint> getSalePoints() {
        return salePoints;
    }

    /**
     * <p>Setter for the field <code>salePoints</code>.</p>
     *
     * @param salePointList a {@link java.util.List} object.
     */
    public void setSalePoints(final Set<SalePoint> salePointList) {
        // Устанавливаем новую связь
        this.salePoints = salePointList;
//        if (this.salePoints != null)
//            this.salePoints.forEach(e -> e.setCompany(this));
    }

    /**
     * <p>Getter for the field <code>employeeList</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public Set<Employee> getEmployees() {
        return employees;
    }

    /**
     * <p>Setter for the field <code>employeeList</code>.</p>
     *
     * @param employeeList a {@link java.util.List} object.
     */
    public void setEmployees(final Set<Employee> employeeList) {
        this.employees = employeeList;
        // Устанавливаем новую связь
//        if (this.employees != null)
//            this.employees.forEach(e -> e.setCompany(this));
    }

    /**
     * <p>Getter for the field <code>legalEntities</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public Set<LegalEntity> getLegalEntities() {
        return legalEntities;
    }

    /**
     * <p>Setter for the field <code>legalEntities</code>.</p>
     *
     * @param legalEntities a {@link java.util.List} object.
     */
    public void setLegalEntities(final Set<LegalEntity> legalEntities) {
        // Устанавливаем новую связь
        this.legalEntities = legalEntities;
//        if (this.legalEntities != null)
//            this.legalEntities.forEach(e -> e.setCompany(this));
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }
}
