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

