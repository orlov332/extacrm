/**
 *
 */
package ru.extas.server;

import ru.extas.model.FormTransfer;

import java.util.List;

/**
 * Управление актами приема передачи БСО
 *
 * @author Valery Orlov
 */
public interface FormTransferService {

    /**
     * Вставить/обновить акт приема передачи
     *
     * @param tf акт приема передачи
     */
    void persist(FormTransfer tf);

    /**
     * Найти акты приема передачи по номеру бланка
     *
     * @param formNum номер бланка
     * @return список найденных актов или null
     */
    List<FormTransfer> findByFormNum(String formNum);
}