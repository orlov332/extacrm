package ru.extas.model;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Extensions;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import java.util.Set;

/**
 * Совокупная информация о пользователе
 *
 * @author Valery Orlov
 */
@PersistenceCapable(detachable = "true")
public class UserProfile extends AbstractExtaObject {

    private static final long serialVersionUID = 6937423190833815234L;

    // Login/email
    @Persistent
    private String login;

    // Имя пользователя
    @Persistent
    private String name;

    // Ссылка на контакт (ID)
    @Persistent
    private String contactId;

    // Password (hash)
    @Persistent
    private String password;

    // Ключ шифрования пароля
    @Persistent
    private String passwordSalt;

    // Требование сменить пароль при следующем входе
    @Persistent
    private boolean changePassword;

    // Основная роль пользователя
    @Persistent
    @Extensions({@Extension(vendorName = "datanucleus", key = "enum-getter-by-value", value = "getRoleByName"),
            @Extension(vendorName = "datanucleus", key = "enum-value-getter", value = "getName")})
    private UserRole role;

    // Группы в которых состоит пользователь (ID)
    @Persistent
    private Set<String> groupList;

    // Пользователь заблокирован
    @Persistent
    private boolean blocked;

    /**
     * @return the groupList
     */
    public Set<String> getGroupList() {
        return groupList;
    }

    /**
     * @param groupList the groupList to set
     */
    public void setGroupList(Set<String> groupList) {
        this.groupList = groupList;
    }

    /**
     * @param role the role to set
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contactId
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * @param contactId the contactId to set
     */
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the changePassword
     */
    public boolean isChangePassword() {
        return changePassword;
    }

    /**
     * @param changePassword the changePassword to set
     */
    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    /**
     * @return the blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * @param blocked the blocked to set
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * @return the passwordSalt
     */
    public String getPasswordSalt() {
        return passwordSalt;
    }

    /**
     * @param passwordSalt the passwordSalt to set
     */
    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

}