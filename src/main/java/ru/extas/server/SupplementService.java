/**
 *
 */
package ru.extas.server;

import java.util.Collection;

/**
 * Поставщик простых справочных данных
 *
 * @author Valery Orlov
 */
public interface SupplementService {

    /**
     * Запрашивает список регионов
     *
     * @return список регионов
     */
    Collection<String> loadRegions();

    /**
     * Запрашивает города (только региональные центры)
     *
     * @return список городов
     */
    Collection<String> loadCities();

    /**
     * Ищет город (региональный центр) по названию региона
     *
     * @param region - название региона
     * @return найденный город или <code>null</code>
     */
    String findCityByRegion(String region);

    /**
     * Ищет региот по городу (региональному центру)
     *
     * @param city город (региональный центр)
     * @return найденный регион или <code>null</code>
     */
    String findRegionByCity(String city);

    /**
     * Запрашивает список видов техники
     *
     * @return список видов техники
     */
    Collection<String> loadMotorTypes();

    /**
     * Запрашивает производителей техники
     *
     * @return список производителей техники
     */
    Collection<String> loadMotorBrands();

}