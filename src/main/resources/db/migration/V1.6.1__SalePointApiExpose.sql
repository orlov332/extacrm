#######################################################################################################################
# Заполняем новое поле "Выставлять через внешнее API"

UPDATE SALE_POINT s
SET
  IS_API_EXPOSE = 1;

# Убираем подчеркивание из названий торговых точек
UPDATE SALE_POINT
SET
  NAME = REPLACE(NAME, "_", " ");
