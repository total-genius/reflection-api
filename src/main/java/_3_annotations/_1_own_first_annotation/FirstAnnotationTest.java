package _3_annotations._1_own_first_annotation;

import _3_annotations._1_own_first_annotation.validator.AgeValidator;
import _3_annotations.model.User;

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
