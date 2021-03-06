package ru.extas.model.security;

/**
 * Действие разрешаемое пользователю
 *
 * @author Valery_2
 *
 * @since 0.3
 */
public enum SecureAction {
	/**
	 * Чтение/просмотр
	 */
	VIEW("view"),
	/**
	 * Редактирование
	 */
	EDIT("edit"),
	/**
	 * Ввод
	 */
	INSERT("insert"),
	/**
	 * Удаление
	 */
	DELETE("delete"),
	/**
	 * Полный доступ
	 */
	ALL("*");


	/**
	 * <p>Constructor for SecureAction.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	SecureAction(final String name) {
		this.name = name;
	}

	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p>getByName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link ru.extas.model.security.SecureAction} object.
	 */
	public static SecureAction getByName(final String name) {
		for (final SecureAction item : SecureAction.values())
			if (item.getName().equals(name))
				return item;
		throw new IllegalArgumentException("There's no enum for name: " + name);
	}

	private final String name;
}
