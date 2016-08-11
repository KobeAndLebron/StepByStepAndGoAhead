package com.gohead.shared.collection;

import java.lang.reflect.Array;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class CollectionUtil {
	/**
	 * Convert collection to array of specific type
	 * @param collection	Collection to convert
	 * @param componentType	The type of collection element
	 * @return
	 */
	public static <T> T[] convertCollectionToArray(Collection<T> collection, Class<T> componentType) {
		if(collection == null || componentType == null){
			return null;
		}
		T[] array = (T[]) Array.newInstance(componentType, collection.size());
		array = collection.toArray(array);
		return array;
	}
}
