package io.github.m4x1m3.coffeeleaf.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to tell CoffeeLeaf to include class in the PlantUML class
 * diagram.
 * 
 * @author Maxime "M4x1m3" FRIESS
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GenUML {

}
