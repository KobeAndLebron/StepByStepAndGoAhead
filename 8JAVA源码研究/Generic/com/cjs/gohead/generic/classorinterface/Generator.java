package com.cjs.gohead.generic.classorinterface;

/**
 * 生产器
 * @author ChenJingShuai
 *
 * @param <T> type parameter
 */
public interface Generator<T> {
	/**
	 * 产生下一个instance
	 * @return
	 */
	public abstract T next();
}
