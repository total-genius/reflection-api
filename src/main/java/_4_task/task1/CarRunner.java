package _4_task.task1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/***
 * 1. Создать класс Car с полями String brand и String model.
 * Создать аннотации @Table (принимает название схемы и таблицы
 * в базе данных) и @Column (принимает название колонки в таблице
 * базы данных). Пометить класс аннотацией @Table и поля
 * аннотацией @Column. Написать программу, принимающую
 * объект класс Car c проинициализированными полями и
 * составляющую запрос "INSERT" в виде строки на основании
 * данных объекта.
 *     Пример: дан объект Car car = new Car("Toyota", "Corolla");
 *     Программа, принимающая этот объект, должна вывести в консоль строку:
 * "INSERT INTO garage.car (model, brand) VALUES ('Toyota', 'Corolla');
 */
public class CarRunner {
    public static void main(String[] args) throws IllegalAccessException {
        Car car = new Car("Toyota", "Corolla");
        System.out.println(generateInsert(car));
    }

    private static String generateInsert(Car car) throws IllegalAccessException {
        //Подготовим шаблон для insert запроса
        String template = """
                insert into %s.%s (%s) values (%s);
                """;

        //Получим аннотацию Table у класса для получения имени таблицы и вставкии ее в шаблон
        Table table = car.getClass().getAnnotation(Table.class);

        //Получим все поля класса для получения у этих полей аннотаций и значений полей
        Field[] declaredFields = car.getClass().getDeclaredFields();

        /*
        В цикле проверяем, содержит ли поле аннотацию Column. Если содержит,
        то мы получаем эту аннотацию из поля и кладем ее в лист.
        Эта аннотация хранит информацию об имени столбца в таблице
         */
        List<Column> columns = new ArrayList<Column>();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columns.add(column);
            }
        }
        /*
        Мы получили лист с аннотациями Column. Из этих аннотаций нам
        нужно вытащить имена столбцов в таблице БД. Эти имена столбцов
        мы соберем в массив стрингов и затем преобразуем в одну строку для
        вставки в шаблон
         */

        String[] columnNames = new String[columns.size()]; //Массив для хранения имен столбцов
        for (int i=0; i<columns.size() && i<columnNames.length; i++) {
            columnNames[i] = columns.get(i).name();
        }
        //Преобразуем массив в одну строку
        String names = String.join(", ", columnNames);

        //Получим значения полей для определенного объекта класса
        List<String> carFiledsValues = new ArrayList<>();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                String filedValue = (String)field.get(car);
                filedValue = "'" + filedValue + "'";
                carFiledsValues.add(filedValue);
            }

        }
        //Преобразуем коллекцию значений в одну строку
        String values = String.join(", ", carFiledsValues.toArray(new String[carFiledsValues.size()]));

        /*
        Теперь в наш шаблон вместо '%s' вставим полученные занчения:
        1. table.schema() - схема таблицы
        2. table.name() - имя таблицы
        3. names - названия полей
        4. values - значения полей

        затем вернем сформированный sql запрос
         */
        return String.format(template,table.schema(), table.name(), names, values);
    }


    //Фрагмент для решения задачи №2
    public static Method getMethodName(Car car, Field field) {
        String fieldName = field.getName();
        try {
            return car.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


}
