package com.gohead.shared.annotation;

import java.lang.annotation.Annotation;

public class AnnotationUtils {
	/**
	 * Returns the annotation on the given class or the package of the class. This searchs up the
     * class hierarchy and the package hierarchy for the closest match.
     * 
	 * @param clazz The class to search for the annotation
	 * @param annotationClass The class of the annotation 
	 * @return	The annotation or null
	 */
	public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass){
		T ann = null;
		while (ann == null && clazz != null){
			ann = clazz.getAnnotation(annotationClass);
			if(ann == null){
				ann = clazz.getPackage().getAnnotation(annotationClass);
			}
			if(ann == null){
				clazz = clazz.getSuperclass();
			}
		}
		
		return ann;
	}
}
