package ru.extas.model.insurance;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.extas.model.common.Comment;
import ru.extas.model.common.OwnedFileContainer;
import ru.extas.model.contacts.Client;
import ru.extas.model.contacts.Contact;
import ru.extas.model.contacts.SalePoint;
import ru.extas.model.motor.MotorBrand;
import ru.extas.model.motor.MotorModel;
import ru.extas.model.motor.MotorType;
import ru.extas.model.security.SecuredObject;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Полис страхования
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
@Entity
@Table(name = "INSURANCE",
        indexes = {
                @Index(columnList = "REG_NUM"),
                @Index(columnList = "A7_NUM"),
                @Index(columnList = "\"DATE\"")
        })
@Getter
@Setter
@NoArgsConstructor
public class Insurance extends SecuredObject {

    private static final long serialVersionUID = -1289533183659860816L;

    // признак пролонгации договора
    @Column(name = "IS_USED_MOTOR")
    private boolean usedMotor;

    // Номер полиса
    @Column(name = "REG_NUM", length = Policy.REG_NUM_LENGTH, unique = true)
    @Size(max = Policy.REG_NUM_LENGTH)
    private String regNum;

    // Номер квитанции А-7
    @Column(name = "A7_NUM", length = A7Form.REG_NUM_LENGTH, unique = true)
    @Size(max = A7Form.REG_NUM_LENGTH)
    private String a7Num;

    // Дата заключения полиса
    @Column(name = "\"DATE\"")
    private LocalDate date;

    // Клиент может быть физ. или юр. лицом
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "ID")
    private Client client;

    @Column(name = "BENEFICIARY", length = Contact.NAME_LENGTH)
    @Size(max = Contact.NAME_LENGTH)
    private String beneficiary;

    // Предмет страхования - тип
    @Column(name = "MOTOR_TYPE", length = MotorType.NAME_LENGTH)
    @Size(max = MotorType.NAME_LENGTH)
    private String motorType;

    // Предмет страхования - марка
    @Column(name = "MOTOR_BRAND", length = MotorBrand.NAME_LENGTH)
    @Size(max = MotorBrand.NAME_LENGTH)
    private String motorBrand;

    // Предмет страхования - модель
    @Column(name = "MOTOR_MODEL", length = MotorModel.NAME_LENGTH)
    @Size(max = MotorModel.NAME_LENGTH)
    private String motorModel;

    // Серийный номер
    @Column(name = "MOTOR_VIN", length = 50)
    @Size(max = 50)
    private String motorVin;

    // Номер договора купли-продажи
    @Column(name = "SALE_NUM", length = 50)
    @Size(max = 50)
    private String saleNum;

    // Дата договора купли-продажи
    @Column(name = "SALE_DATE")
    private LocalDate saleDate;

    // Страховая сумма, руб.
    @Column(name = "RISK_SUM", precision = 32, scale = 4)
    private BigDecimal riskSum;

    // Период покрытия
    @Column(name = "COVER_TIME")
    private PeriodOfCover coverTime;

    // Страховая премия, руб.
    @Column(precision = 32, scale = 4)
    private BigDecimal premium;

    // Дата оплаты страховой премии
    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    // Дата начала срока действия договора
    @Column(name = "START_DATE")
    private LocalDate startDate;

    // Дата окончания срока действия договора
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    private SalePoint dealer;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = OwnedFileContainer.OWNER_ID_COLUMN)
    private List<InsuranceFileContainer> files = newArrayList();

    @Column(name = "IS_DOC_COMPLETE")
    private boolean docComplete;

    // Комментарии к договору страхования
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = Comment.OWNER_ID_COLUMN)
    @OrderBy("createdDate")
    private List<InsuranceComment> comments = newArrayList();

    public boolean isCredit() {
        return !isNullOrEmpty(getBeneficiary()) && !getBeneficiary().equals(getClient().getName());
    }

    public enum PeriodOfCover {
        YEAR,
        HALF_A_YEAR
    }

    /**
     * <p>Constructor for Insurance.</p>
     *
     * @param motorBrand  a {@link java.lang.String} object.
     * @param riskSum     a {@link java.math.BigDecimal} object.
     * @param coverPeriod a {@link ru.extas.model.insurance.Insurance.PeriodOfCover} object.
     * @param usedMotor   a boolean.
     */
    public Insurance(final String motorBrand, final BigDecimal riskSum, final PeriodOfCover coverPeriod, final boolean usedMotor) {
        this.motorBrand = motorBrand;
        this.riskSum = riskSum;
        this.coverTime = coverPeriod;
        this.usedMotor = usedMotor;
    }

}
