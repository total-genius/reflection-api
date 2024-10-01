package _2_reflection_api;

import _1_reflection_api_intorduction.model.User;

import java.lang.reflect.*;

public class ReflectionApiEx2 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        testConstructor();

        User user = new User(20L, "Alex");
        testFields(user);
        testMethod(user);

    }

    //Получим конструктор класса через Reflection API и создадим с его помощью объект
    private static void testConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //Получаем конструктор класса User
        Constructor<User> constructor = User.class.getConstructor(Long.class, String.class);
        //Получили конструктор. Этот конструктор позволяет нам создавать экземляр нашего класса
        User user = constructor.newInstance(5L, "Petr");
        System.out.println(user);
    }

    //Получение полей класса и работа с ними
    private static void testFields(Object obj) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //Получили массив всех полей класса
        Field[] fields = obj.getClass().getDeclaredFields();
        //Проходим по каждому полю в цикле
        for (Field field : fields) {
            //Делаем приватные поля доступными
            field.setAccessible(true);
            //Получаем значение поля определенного объекта класса
            Object o = field.get(obj);
            //Меняем значение поля объекта
             field.set(obj, "Anya");
            //Выводим на консолько все модификаторы поля
            System.out.println(field.getModifiers());
            //Проверяем является ли поле приватным
            System.out.println(Modifier.isPrivate(field.getModifiers()));
            System.out.println(o);
        }

    }

    //получение методов класса и работа с ними
    private static void testMethod(User user) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //Получаем метод класса по его имени
        Method getName = user.getClass().getDeclaredMethod("getName");
        //Вызываем полученный метод у определенного объекта класса
        System.out.println(getName.invoke(user));
        //Получаем метод класса по его имени и типу параметра который он принимает
        Method setName = user.getClass().getDeclaredMethod("setName", String.class);
        //Вызываем полученный метод у определенного объекта класса и меняем с его помощью значение поля name
        System.out.println(setName.invoke(user, "Sweta"));
        System.out.println(user);
    }
}
