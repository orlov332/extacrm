package ru.extas.model.sale;

import ru.extas.model.common.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Комментарии в продаже
 *
 * @author Valery Orlov
 *         Date: 25.09.2014
 *         Time: 18:14
 */
@Entity
@Table(name = "SALE_PRIVATE_COMMENT")
public class SalePrivateComment extends Comment {

    public SalePrivateComment() {
    }

    public SalePrivateComment(final Comment comment) {
        this.setArchived(comment.isArchived());
        this.setCreatedBy(comment.getCreatedBy().orElse(null));
        this.setCreatedDate(comment.getCreatedDate().orElse(null));
        this.setLastModifiedBy(comment.getLastModifiedBy().orElse(null));
        this.setLastModifiedDate(comment.getLastModifiedDate().orElse(null));
        this.setOwnerId(comment.getOwnerId());
        this.setText(comment.getText());
    }

}
