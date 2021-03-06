package ru.extas.server.notification;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.extas.model.notification.Notice;

/**
 * Created by Valery on 04.06.2014.
 *
 * @author Valery_2
 *
 * @since 0.5.0
 */
@Repository
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public interface NoticeRepository extends JpaRepository<Notice, String> {

}
