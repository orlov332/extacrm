package ru.extas.web.commons.converters;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.vaadin.data.util.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * <p>PhoneConverter class.</p>
 *
 * @author Valery Orlov
 *         Date: 17.04.2014
 *         Time: 19:56
 * @version $Id: $Id
 * @since 0.4.2
 */
@Component
public class PhoneConverter implements Converter<String, String> {

    /** {@inheritDoc} */
    @Override
    public String convertToModel(String value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String country = locale == null ? "RU" : locale.getCountry();
        Phonenumber.PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(value, country);
        } catch (NumberParseException e) {
            throw new ConversionException("Неправильный формат телефона", e);
        }

        return phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
    }

    /** {@inheritDoc} */
    @Override
    public String convertToPresentation(String value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        if (value == null)
            return null;

        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        String country = locale == null ? "RU" : locale.getCountry();
        Phonenumber.PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(value, country);
        } catch (NumberParseException e) {
            throw new ConversionException("Неправильный формат телефона", e);
        }

        return phoneUtil.format(phone, PhoneNumberUtil.PhoneNumberFormat.NATIONAL);
    }

    /** {@inheritDoc} */
    @Override
    public Class<String> getModelType() {
        return String.class;
    }

    /** {@inheritDoc} */
    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}