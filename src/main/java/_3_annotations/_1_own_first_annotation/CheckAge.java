package _3_annotations._1_own_first_annotation;

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
