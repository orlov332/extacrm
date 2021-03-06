package ru.extas.model.motor;

import ru.extas.model.common.AuditedObject;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Определяет марку модели
 *
 * @author Valery Orlov
 *         Date: 27.05.2014
 *         Time: 9:36
 *
 * @since 0.5.0
 */
@Entity
@Table(name = "MOTOR_BRAND")
public class MotorBrand  extends AuditedObject {

    public static final int NAME_LENGTH = 50;

    @Column(nullable = false, length = NAME_LENGTH)
    @Size(max = NAME_LENGTH)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH}, targetEntity = MotorType.class)
    @JoinTable(
            name = "BRAND_TYPE",
            joinColumns = {@JoinColumn(name = "BRAND_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "TYPE_ID", referencedColumnName = "ID")})
    private Set<MotorType> brandTypes = newHashSet();

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
     * <p>Getter for the field <code>brandTypes</code>.</p>
     *
     * @return a {@link java.util.Set} object.
     */
    public Set<MotorType> getBrandTypes() {
        return brandTypes;
    }

    /**
     * <p>Setter for the field <code>brandTypes</code>.</p>
     *
     * @param brandTypes a {@link java.util.Set} object.
     */
    public void setBrandTypes(final Set<MotorType> brandTypes) {
        this.brandTypes = brandTypes;
    }
}
