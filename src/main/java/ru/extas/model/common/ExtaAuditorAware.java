package ru.extas.model.common;

import org.springframework.data.domain.AuditorAware;
import ru.extas.server.security.UserManagementService;

import javax.inject.Inject;

/**
 * Поставляет текущего пользователя для аудита Spring Data
 *
 * @author Valery Orlov
 *         Date: 03.04.2014
 *         Time: 9:47
 *
 * @since 0.3.0
 */
public class ExtaAuditorAware implements AuditorAware<String> {

    @Inject
    private UserManagementService userService;

    /** {@inheritDoc} */
    @Override
    public String getCurrentAuditor() {
        if (userService.isUserAuthenticated())
            return userService.getCurrentUserLogin();
        else
            return "admin";
    }
}
