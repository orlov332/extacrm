package ru.extas.model.common;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Базовый класс для всех сущностей.
 * Имплементирует макеры изменений.
 *
 * @author Valery Orlov
 *
 * @since 0.3
 */
//@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditedObject extends IdentifiedObject implements Auditable<String, String, ZonedDateTime> {

    /**
     * Constant <code>LOGIN_LENGTH=50</code>
     */
    protected static final int LOGIN_LENGTH = 50;

    @CreatedBy
    @Column(name = "CREATED_BY", length = LOGIN_LENGTH)
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private ZonedDateTime createdDate;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", length = LOGIN_LENGTH)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private ZonedDateTime lastModifiedDate;

    @Override
    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    @Override
    public void setCreatedBy(final String createdBy) {
        if (this.createdBy == null)
            this.createdBy = createdBy;
    }

    @Override
    public Optional<ZonedDateTime> getCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    @Override
    public void setCreatedDate(final ZonedDateTime creationDate) {
        if (this.createdDate == null)
            this.createdDate = creationDate;
    }

    @Override
    public Optional<String> getLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    @Override
    public void setLastModifiedBy(final String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Optional<ZonedDateTime> getLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    @Override
    public void setLastModifiedDate(final ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
