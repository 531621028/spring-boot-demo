package com.hkk.demo.utils;

import java.util.Date;
import java.util.GregorianCalendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpELDemo
 *
 * @author hukangkang
 * @since 2021/6/30
 */
public class SpELDemo {

    public static String reverseString(String input) {
        StringBuilder backwards = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            backwards.append(input.charAt(input.length() - 1 - i));
        }
        return backwards.toString();
    }


    public static void main(String[] args) throws NoSuchMethodException {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Nikola Tesla", c.getTime(), "Serbian");

        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("name");
        Expression exp2 = parser.parseExpression("#root.name");

        StandardEvaluationContext context = new StandardEvaluationContext(tesla);
        String name = (String) exp.getValue(context);
        String name2 = (String) exp2.getValue(context);
        System.out.println(name);
        System.out.println(name2);

        context.registerFunction("reverseString", SpELDemo.class.getDeclaredMethod("reverseString", String.class));
        String helloWorldReversed = parser.parseExpression("#reverseString('hello')").getValue(context, String.class);
        System.out.println(helloWorldReversed);
    }

    @AllArgsConstructor
    @Getter
    public static class Inventor {

        private final String name;
        private final Date birthday;
        private final String nationality;
    }
}
