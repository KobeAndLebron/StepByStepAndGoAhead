
/**
 * 造成线程安全的根本原因：由于线程的并发性和异步性，导致线程执行顺序的不确定性，进而导致线程对共享对象/数据的"不正确"操作和访问(线程间的读写交叉执行);
 * 
 * 解决线程安全的方法：使用序列化的方式来访问共享资源，这种机制叫做Mutual Exclusion，在Java中通过Synchronized关键字来实现
 * 
 * Some of JVM's run time data areas are shared by all threads,others are private to individual threads.Beacause the heap
 *and method area are shared by all threads,java programs need to coordinate multi-threaded access to two kinds of data:
 * 	instance variables,which are stored on the heap 
 *  class variables,which are stored in the method area
 *  Programs never need to coordinate access to local variables,which reside on the java stacks,because data on the java 
 * stack is private to the thread to which the java stacks belong. 
 * 
 * 	对于成员变量-基本数据类型-来说，主要看载体在heap area是否是单例，如果是单例，那么对此成员变量就必须进行同步控制在有线程安全的问题存在时。
 * 如果不是单例，则不需要同步；
 *  以上所说的单例非单例是真正被(eg:Thread对象/Runnable对象)使用的，有可能载体是非单例，但是只使用一个，那么载体还是单例;
 *  如果成员变量是引用类型的变量：
 *  	1、要么通过方法来访问这个引用类型代表的类的成员变量，这样又回到了基本数据类型的同步问题，即保证这个对象的这个方法线程安全即可
 *    *****************************暂时忽略这种情况
 *  	2、直接通过对象.fieldName来get和set，这样的话如果载体是单例，那么就需要考虑线程问题；如果载体不是单例，就要看被载体的引用是否指向一个对象
 *    是的话则需要考虑；不是则不需要考虑吧(与下文所说的--00相同)；但是不符合面向对象的封装特性
 *      2的补充:载体和引用类型所代表的类如果有一个可以确定在内存中为单例，那么就需要考虑线程安全问题；如果两者都为非单例，则不需要考虑~	--00
 * 	先看是否单例，然后看是否读写交叉执行~
 *    *****************************暂时忽略这种情况
 *  如果是类变量的话，是肯定需要考虑的(无论基本数据类型还是引用、通过方法读写还是.读写)，因为类变量存于方法区，在内存只有一份
 *  
 * 	可以File Search功能来搜索+++++++,里面都是些例子;
 * 
 *  eg: Servlet单例，需要考虑线程安全；Action非单例，不需要考虑
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-上午11:36:57
 */
public class WhenToThinkThreadSafe {

}
