package ru.extas.web.motor;

import ru.extas.web.commons.GridDataDecl;

/**
 * Created by Valery on 03.06.2014.
 *
 * @author Valery_2
 *
 * @since 0.5.0
 */
public class MotorBrandDataDecl extends GridDataDecl {
    /**
     * <p>Constructor for MotorBrandDataDecl.</p>
     */
    public MotorBrandDataDecl() {
        addMapping("name", "Название");
        super.addDefaultMappings();
    }
}
