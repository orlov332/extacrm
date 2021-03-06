package ru.extas.server.insurance;

import ru.extas.model.insurance.Policy;

import java.util.List;

/**
 * <p>PolicyService interface.</p>
 *
 * @author Valery Orlov
 *         Date: 19.12.13
 *         Time: 12:13
 *
 * @since 0.3
 */
public interface PolicyService {
    /**
     * Возвращает список всех доступных полисов
     *
     * @return список полисов
     */
    List<Policy> loadAvailable();

    /**
     * Забронировать полис
     *
     * @param policy бронируемый полис
     */
    void bookPolicy(Policy policy);

    /**
     * Реализовать полис
     *
     * @param policy реализуемый полис
     */
    void issuePolicy(Policy policy);

    /**
     * Забронировать полис
     *
     * @param regNum номер резервируемого полиса
     */
    void bookPolicy(String regNum);

    /**
     * Реализовать полис
     *
     * @param regNum номер реализуемого полиса
     */
    void issuePolicy(String regNum);
}
