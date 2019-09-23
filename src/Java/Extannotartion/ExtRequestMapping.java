package Extannotartion;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)



@Documented
public @interface ExtRequestMapping {

    String value() default  "";
}
