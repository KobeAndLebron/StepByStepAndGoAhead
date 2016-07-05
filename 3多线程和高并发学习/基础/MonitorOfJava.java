/**
 * 	One of strengths of the Java program language is its support for multithreading at the language level.Much of this
 * support centers on synchronization:coordinating activities and data access among multiple threads.The mechanism that 
 * Java uses to support synchronization is the monitor.
 *  Java's monitor supports two kinds of synchronization:mutual exclusion and cooperation.Mutual exclusion,which is supp-
 * orted in the JVM via object locks,enables multiple threads to independently work on shared data without interfering
 * with each other.A monitor can have several monitor regions.
 * 	Cooperation,which is supported in the JVM via the wait and notify(all) methods of class Object,enables
 * multiple threads to work together toward a common goal.(Notice:a notify must often be considered by waiting threads 
 * merely as a hint that the desired state may exist. Each time a waiting thread is resurrected, it may need to check
 * the state again to determine whether it can move forward and do useful work. If it finds the data still isn't in 
 * the desired state, the thread could execute another wait or give up and exit the monitor.
 * For example:ExamleOfCheckAgainState.txt).
 * 	A monitor is like a building that contains one special room that can be occupied by only one thread at a time. 
 * The room usually contains some data. From the time a thread enters this room to the time it leaves, it has 
 * exclusive access to any data in the room. Entering the monitor building is called "entering the monitor." 
 * Entering the special room inside the building is called "acquiring the monitor." Occupying the room is called 
 * "owning the monitor," and leaving the room is called "releasing the monitor." Leaving the entire building is 
 * called "exiting the monitor."
 *  The graphical depiction of monitor is in Figure GraphicalDepictionOfMonitor-1.jpg and 
 * GraphicalDepictionOfMonitor-2.jpg  
 *
 * 	 在Java中，所有的对象都自动含有单一的锁-也成为监视器，当在对象上调用其任意Synchronized方法的时候，此对象都会被枷锁；其他的synchronized方法只有等到
 * 前一个方法调用完毕的时候才能被调用.
 * 	Thus, there is a single lock that is shared by all the synchronized methods of a particular object, 
 * and this lock can be used to prevent object memory from being written by more than one task at a time.
 * 	
 *  针对每一个类，也有一个锁-作为类的class对象的一部分，所以Synchronized static方法可以是获取class对象的锁；
 * 
 * A thread becomes the owner of the object's monitor in one of three ways:
 * <ul>
 * <li>By executing a synchronized instance method of that object.
 * <li>By executing the body of a {@code synchronized} statement
 *     that synchronizes on the object.
 * <li>For objects of type {@code Class,} by executing a
 *     synchronized static method of that class.
 *     
 *  其余见<a>http://www.artima.com/insidejvm/ed2/threadsynch.html</a>包含两种同步的细节
 * 	 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月11日-下午1:23:58
 */
public class MonitorOfJava {

}
