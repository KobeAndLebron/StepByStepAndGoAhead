package com.cjs.annotation.second;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbName {
	public abstract String name() default "";
	// The value for annotation attribute DbName.value must be a constant expression
	// public abstract String value() default null;
}
