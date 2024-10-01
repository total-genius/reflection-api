package _1_reflection_api_intorduction.model;

public class ReflectionApiEx {
    public static void main(String[] args) {
        User user = new User(25L, "Vanya");

        /*
        Каждый объект знает свой класс (шаблон, на основании которого он был создан)
        Поэтому мы можем через объект класса добраться до самого класса и той
        информации, которая хранится в классе
         */

        Class<? extends User> userClass = user.getClass();//Получаем класс через объект вариант
        Class<User> userClass1 = User.class;//Получаем класс через его имя
        System.out.println(userClass1==userClass);//Сслыки будут равны, потому информация о классе хранится в единственном экземпляре

        System.out.println(userClass.getName());//Возвращает имя класса в формате, который JVM использует для внутреннего представления.
        System.out.println(userClass.getSimpleName());//Получение простого имени класса
        System.out.println(userClass.getCanonicalName());//Возвращает каноническое имя класса, которое соответствует его внешнему виду в исходном коде

        //Пример методов getName(), getCanonicalName() для внутреннего класса, где видна разница между данными методами
        System.out.println(Test1.class.getName());
        System.out.println(Test1.class.getCanonicalName());

        System.out.println(userClass.getDeclaredFields());//Получение всех полей класса
        System.out.println(userClass.getSuperclass().getDeclaredFields());//Получение всех полей класса родителя
        System.out.println(userClass.getMethods());//Получение публичных методов
        System.out.println(userClass.getDeclaredMethods());//Получение всех объявленных в классе методов
        System.out.println(userClass.getInterfaces());//Получение всех интерфейсов, которые реализует класс
        System.out.println(userClass.getGenericInterfaces());//Получение параметризированных интерфейсов
        System.out.println(userClass.getConstructors());//Получение конструкторов
        System.out.println(userClass.getConstructors()[0].getModifiers());//получение модификаторов конструктора
        System.out.println(userClass.getPackageName());//Получение имени пакета, где лежит класс





    }

    //Внутренний классы
    private class Test1 {
    }
    private enum Test2{
        ONE, TWO
    }
    //Вложенный класс
    private static class Test3 {}

}
