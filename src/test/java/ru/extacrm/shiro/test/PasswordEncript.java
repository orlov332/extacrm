/**
 *
 */
package ru.extacrm.shiro.test;

import ru.extas.model.security.UserProfile;
import ru.extas.security.UserRealm;

import java.util.Date;

/**
 * <p>PasswordEncript class.</p>
 *
 * @author Valery Orlov
 *
 * @since 0.2.9
 */
public class PasswordEncript {

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(final String[] args) {

        final String plainTextPassword = "123qwe";
        final UserProfile user = new UserProfile();
        user.setPassword(plainTextPassword);
        UserRealm.securePassword(user);

        System.out.println(user.getPassword());
        System.out.println(user.getPasswordSalt());

        System.out.println(new Date());
    }

}
