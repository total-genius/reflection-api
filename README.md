# Reflection API

### Что такое Reflection API?
Reflection API позволяет программе анализировать или модифицировать своё собственное поведение во 
время выполнения. Это мощный инструмент, с помощью которого можно исследовать классы, методы, 
конструкторы, поля и вызывать методы объектов, даже если они недоступны напрямую через код.


### Когда используется Reflection?
- Фреймворки: многие популярные фреймворки, такие как Spring, Hibernate, используют рефлексию для работы с 
аннотациями, создания объектов, внедрения зависимостей и вызова методов.
- Тестирование: можно получить доступ к приватным полям или методам для тестирования.
- Инструменты: разработка таких инструментов, как IDE или средства анализа кода, также требует использования рефлексии.

### Основные классы и методы Reflection API

- Class<T>: Этот класс позволяет работать с метаданными классов. Вы можете получить объект Class 
через метод getClass() или `Class.forName()`. 
```java

Class<?> clazz = MyClass.class;

```
   - `getMethods()`: получает все публичные методы.
   - `getDeclaredMethods()`: получает все методы, включая приватные.
   - `getFields()`, `getDeclaredFields()`: доступ к полям.
   - `getConstructors()`, `getDeclaredConstructors()`: доступ к конструкторам.
- Method: представляет методы класса.
   - `invoke(Object obj, Object... args)`: вызывает метод объекта.
- Field: представляет поля класса.
  - `set(Object obj, Object value)`: устанавливает значение поля.
  - `get(Object obj)`: получает значение поля.
- Constructor: представляет конструкторы класса.
  - `newInstance(Object... initargs)`: создаёт объект с помощью конструктора.

### Пример использования Reflection API
Простой пример, как с помощью рефлексии вызвать приватный метод:
```java

import java.lang.reflect.Method;

class MyClass {
    private void sayHello() {
        System.out.println("Hello, Reflection!");
    }
}

public class ReflectionExample {
    public static void main(String[] args) throws Exception {
        MyClass myObject = new MyClass();

        // Получаем класс
        Class<?> clazz = myObject.getClass();

        // Получаем приватный метод
        Method method = clazz.getDeclaredMethod("sayHello");

        // Делаем метод доступным
        method.setAccessible(true);

        // Вызываем метод
        method.invoke(myObject);
    }
}

```

#### Доступ к приватным полям и методам
Обычно, приватные поля и методы недоступны, но с помощью рефлексии можно изменить их видимость:
```java

Field privateField = clazz.getDeclaredField("fieldName");
privateField.setAccessible(true);
Object value = privateField.get(obj);

```

### Ограничения и недостатки
- Производительность: Рефлексия медленнее, чем обычные вызовы методов, так как JVM должна выполнять больше операций.
- Безопасность: Доступ к приватным членам класса может нарушать инкапсуляцию.
- Проблемы с типизацией: Рефлексия работает с объектами и типами на низком уровне, что может привести к ошибкам на 
этапе выполнения, которые сложно отследить на этапе компиляции.


### Аннотации и рефлексия
С помощью рефлексии можно анализировать аннотации, что является важной частью фреймворков, таких как Spring. Пример:
```java

if (clazz.isAnnotationPresent(MyAnnotation.class)) {
    // Логика для работы с аннотацией
}

```


# Annotation

**Аннотации** - это специальные метки или метаданные, которые мы можем добавлять к элементам нашего кода для предоставления
дополнительной информации компилятору, инструментам разработки или во время выполнения программы. **Аннотации** помогают
добавить дополнительную функциональность или поведение без изменения самого кода.

Где можно использовать аннотации:
1. классы и интрефейсы
2. методы и конструкторы
3. поля
4. параметры методов и конструкторов
5. локальные переменные
6. Пакеты (аннотации можно применять к декларации пакетов)
7. Использование типов (с Java 8 можно аннотировать любое использование типов, например, приведение типов, 
объявление переменных)

### Как создать аннотацию?

*Пример - аннотация `@Override`:*

```java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Ovveride {
    
}

```

## Структура аннотаций

**Аннотация** состоит из двух основных частей, которые представлены так же в виде аннотации:
1. `@Target(ElementType.METHOD)` - это мета-аннотация. Указывает, где можно использовать нашу аннотацию (в нашем случае, только над методом)
    
    `@Target` может принимать слудующие значения:
    - `TYPE`: Классы, интерфейсы, перечисления, аннотации
    - `FIELD`: Поля (переменные экземпляра и статические переменные)
    - `METHOD`: Методы
    - `PARAMETER`: Параметры методов и конструкторов
    - `CONSTRUCTOR`: Конструкторы
    - `LOCAL_VARIABLE`: Локальные переменные
    - `ANNOTATION_TYPE`: Аннотации
    - `PACKAGE`: Пакеты
    - `TYPE_PARAMETER`: Параметры обобщений (Generics) — с Java 8
    - `TYPE_USE`: Любое использование типов — с Java 8
   

2. `@Retention(RetentionPolicy.SOURCE)` - мета-аннотация, которая определяет, как долго аннотация сохраняется в коде. 
(В нашем случае, аннотация `@Override` доступна только в исходном коде. После компиляции эта аннотация 
пропадет)

   `@Retention()` может принимать следующие значения:
    - `SOURCE`: Аннотация доступна только в исходном коде и отбрасывается компилятором
    - `CLASS`: Аннотация сохраняется в байт-коде, но недоступна во время выполнения. Это значение по умолчанию
    - `RUNTIME`: Аннотация сохраняется в байт-коде и доступна во время выполнения через рефлексию

**Внутри аннотаций** могут быть определены элементы (атрибуты), которые вглядят как методы без тела. Используются
для передачи параметров в аннотацию

```java

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Target {
    
    
    
    ElementType[] value();
}

```

### Ограничения элементов аннотации:
1. **Типы данных** - элементы могут иметь следующие типы:
    - Примитивные типы
    - `String`
    - `Class` или `Class<?>`
    - `enum`
    - Другие аннотации
    - Одномерные массивы указанных выше типов
2. **Нельзя использовать** объекты (кроме `String` и `Class`), `Generics`, коллекции и другие сложные типы

**Дополнительные пояснения**

1. Несмотря на то, что элементы аннотации выглядят как методы (они имеют имя и возвращаемый типа), они НЕ являются методам
в обычном понимании. Они не имеют тела и не выполняют никаких действий.
2. **Мета-аннотации** - `@Target & @Retention` называются мета-аннотациями, потому что они применяются к другим аннотациям
и определяют их поведение.

### Напишем свою собственную аннотацию

Пример - [CheckAge](src/main/java/_3_annotations/_1_own_first_annotation/CheckAge.java)

```java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки минимального возраста пользователя.
 */

@Target({
        ElementType.TYPE,
        ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckAge {


    /**
     * Минимально допустимый возраст.
     * Значение по умолчанию: 18.
     */
    int minAge() default 18;
}
```

Пример применения - [User](src/main/java/_3_annotations/model/User.java)

```java

public class User {
    @CheckAge(minAge = 21)
    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    //getters and setters
    
    
}
```

1. `int minAge() default 18` - мы установили для метода значение по умолчанию с помощью ключего слова `default 18`.
Таким образом, если мы не установим собственное значение, в `int minAge()` присвоится дефолтное значение 18. Это делает
необязательным указание значения для `int minAge()` при использовании аннотации. Если бы мы не указали дефолтное 
значение, то при использовании аннотации `@CheckAge`, мы должны были бы установить значение для `int minAge()`
2. Для того, чтобы установить собственное значение для `int minAge()` мы пишем следующее `@CheckAge(minAge = 21)`. 
Однако, если назвать метод внутри аннотации `int value()`, то при присвоении значения имя метода прописывать 
необязательно. Это бы выглядело следующим образом ` @CheckAge(21)`. 
    - Однако, если мы используем другие элементы вместе с `value()`, то имя `value` нужно указывать:
```java

public @interface CheckAge {
    int value() default 18;
    String message() default "Age is too low";
}
```
Применение аннотации:
```java

@CheckAge(value = 21, message = "Custom message")
private int age;
```

### Как реализовать проверку минимального возраста с помощью нашей аннотации?
Аннотации сами по себе не выполняют никакой логики; они служат метаданными. Чтобы наша аннотация действительно проверяла
возраст, нам нужно написать код, который будет считывать аннотацию и проводить соответствующую проверку.

Это делается с помощью **рефлексии**. Создадим валидатор, который будет проверять объекты класса `User` на
соответвие заданным ограничениям.

Пример - [AgeValidator](src/main/java/_3_annotations/_1_own_first_annotation/validator/AgeValidator.java)

```java

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
```

Тест аннотации - [FirstAnnotationTest](src/main/java/_3_annotations/_1_own_first_annotation/FirstAnnotationTest.java)

```java

public class FirstAnnotationTest {
    public static void main(String[] args) {
        User user1 = new User(25, "Alice");
        User user2 = new User(19, "Bob");
        User user3 = new User(17, "Charlie");

        try {
            System.out.println("User1 valid: " + AgeValidator.validate(user1));
            System.out.println("User2 valid: " + AgeValidator.validate(user2));
            System.out.println("User3 valid: " + AgeValidator.validate(user3));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
```

### Валидация при сооздании объекта
Если мы хотим, чтобы проверка возраста происходила автоматически, при создании объекта, можно использовать библиотеки
валидации, такие **Hibernate Validator** (реализация **Bean Validation API**)

# Задачи
1. Создать класс Car с полями String brand и String model.
   Создать аннотации @Table (принимает название схемы и таблицы
   в базе данных) и @Column (принимает название колонки в таблице
   базы данных). Пометить класс аннотацией @Table и поля
   аннотацией @Column. Написать программу, принимающую
   объект класс Car c проинициализированными полями и
   составляющую запрос "INSERT" в виде строки на основании
   данных объекта.

   Пример: дан объект Car car = new Car("Toyota", "Corolla");
   Программа, принимающая этот объект, должна вывести в консоль строку:
   "INSERT INTO garage.car (model, brand) VALUES ('Toyota', 'Corolla');

2. Для получения данных программа должна
   использовать только get-методы (нельзя использовать
   значения приватных полей).

**Пример решения:**
1. Аннотация [Table](src/main/java/_4_task/task1/Table.java)
2. Аннотация [Column](src/main/java/_4_task/task1/Column.java)
3. Модель [Car](src/main/java/_4_task/task1/Car.java)
4. Решение задачи [CarRunner](src/main/java/_4_task/task1/CarRunner.java)
5. Решение через Stream API [CarRunnerStream](src/main/java/_4_task/task1/stream_solution/CarRunnerStream.java)
