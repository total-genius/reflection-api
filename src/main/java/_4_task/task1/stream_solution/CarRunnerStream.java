package _4_task.task1.stream_solution;

import _4_task.task1.Car;
import _4_task.task1.Column;
import _4_task.task1.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/***
 * Решение через Stream API
 *
 * 1. Создать класс Car с полями String brand и String model.
 * Создать аннотации @Table (принимает название схемы и таблицы
 * в базе данных) и @Column (принимает название колонки в таблице
 * базы данных). Пометить класс аннотацией @Table и поля
 * аннотацией @Column. Написать программу, принимающую
 * объект класс Car c проинициализированными полями и
 * составляющую запрос "INSERT" в виде строки на основании
 * данных объекта.
 *
 *     Пример: дан объект Car car = new Car("Toyota", "Corolla");
 *     Программа, принимающая этот объект, должна вывести в консоль строку:
 *     "INSERT INTO garage.car (model, brand) VALUES ('Toyota', 'Corolla');
 */
public class CarRunnerStream {
    public static void main(String[] args) {
        Car car = new Car("Toyota", "Corolla");
        System.out.println(generateInsert(car));
    }

    private static String generateInsert(Car car) {
        //Шаблон SQL запроса
        String sqlInsertTemplate = """
                insert into %s.%s (%s) values (%s);
                """;

        /*
        Получим аннотацию Table для того, чтобы извлечь оттуда
        сзему и название таблицы
         */
        Table table = car.getClass().getAnnotation(Table.class);

        /*
        Получим все поля класса для того чтобы:
        1. Получить аннотацию Column и оттуда достать имена столбцов
        2. Получить значения полей
         */
        Field[] fields = car.getClass().getDeclaredFields();

        /*
        Далее с помощью Stream API:
        1. Отфильтруем поля. Получим только те поля, которые имеют аннотацию Column
        2. Получим из полей аннотации Column
        3. Из аннотаций  Column получим имена столбцов
        4. преобразуем поток с именами столбцов в одну строку
         */
        String columnNames = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .map(column -> column.name())
                .collect(Collectors.joining(", ", "", ""));

        /*
        Теперь нам нужно получить значения полей объекта car класса Car
        1. Получим stream из массива fields
        2. Проверим поля на наличие у них аннотации Column
        3. Получим значения полей
        4. Преобразуем поток со значениями полей в одну строку
         */
        String values = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        return (String) field.get(car);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(", ", "", ""));
        /*
        Вставляем наш шаблон вместо '%s' полученные данные и
        возвращаем полученный SQL запрос
         */
        return String.format(sqlInsertTemplate,table.schema(), table.name(), columnNames, values);
    }
}
