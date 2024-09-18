package _3_annotations._1_own_first_annotation.validator;

import _3_annotations._1_own_first_annotation.CheckAge;

import java.lang.reflect.Field;

public class AgeValidator {
    public static boolean validate(Object obj) throws IllegalAccessException {
        //Получаем класс нашего объекта
        Class<?> objClass = obj.getClass();

        //проходим по всем полям класса
        for (Field field : objClass.getDeclaredFields()) {
            //Проверяем, есть ли аннотация @CheckAge над полем
            if (field.isAnnotationPresent(CheckAge.class)) {
                //Делаем поле доступным для чтения значения
                field.setAccessible(true);
                //Получим значение поля
                Object value = field.get(obj);

                //Получаем аннотацию и минимальный возраст
                CheckAge annotation = field.getAnnotation(CheckAge.class);
                int minAge = annotation.minAge();

                //Проверяем, что значение поля является числом
                if (value instanceof Integer) {
                    int age = (Integer) value;
                    if (age < minAge) {
                        // Возраст меньше мнимального, возвращаем false
                        return false;
                    }
                }
            }
        }
        //Если все проверки пройдены, возвращаем true
        return true;
    }
}
