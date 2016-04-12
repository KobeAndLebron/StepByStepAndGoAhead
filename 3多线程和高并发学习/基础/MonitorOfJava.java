/**
 * 	One of strengths of the Java program language is its support for multithreading at the language level.Much of this
 * support centers on synchronization:coordinating activities and data access among multiple threads.The mechanism that 
 * Java uses to support synchronization is the monitor.
 *  Java's monitor supports two kinds of synchronization:mutual exclusion and cooperation.Mutual exclusion,which is supp-
 * orted in the JVM via object locks,enables multiple threads to independently work on shared data without interfering
 * with each other.Cooperation,which is supported in the JVM via the wait and notify(all) methods of class Object,enables
 * multiple threads to work together toward a common goal. 
 * 
 *  见火狐标签页-Java多线程系列
 *  
 *  Notice:The kind of monitor used in the Java virtual machine is sometimes called a Signal and Continue monitor
 * because after a thread does a notify (the signal) it retains ownership of the monitor and continues executing
 * the monitor region (the continue). At some later time, the notifying thread releases the monitor and a waiting 
 * thread is resurrected. Presumably, the waiting thread suspended itself because the data protected by the monitor 
 * wasn't in a state that would allow the thread to continue doing useful work. Also, the notifying thread presumably 
 * executed the notify command after it had placed the data protected by the monitor into the state desired by the 
 * waiting thread. But because the notifying thread continued, it may have altered the state after the notify such 
 * that the waiting thread still can't do useful work. Alternatively, a third thread may have acquired the monitor 
 * after the notifying thread released it but before the waiting thread acquired it, and the third thread may have 
 * changed the state of the protected data. As a result, a notify must often be considered by waiting threads merely 
 * as a hint that the desired state may exist. Each time a waiting thread is resurrected, it may need to check the
 * state again to determine whether it can move forward and do useful work. If it finds the data still isn't in the 
 * desired state, the thread could execute another wait or give up and exit the monitor. 
 * 	so we should use while instead of if~~~
 * 
 * 	As an example, consider the scenario described above that involves a buffer once again , a read thread, and a write
 * thread. Assume the buffer is protected by a monitor. When a read thread enters the monitor that protects the buffer, 
 * it checks to see if the buffer is empty. If the buffer is not empty, the read thread reads (and removes) some data 
 * from the buffer. Satisfied, it exits the monitor. On the other hand, if the buffer is empty, the read thread executes
 * a wait command. As soon as it executes the wait, the read thread is suspended and placed into the monitor's wait set.
 * In the process, the read thread releases the monitor, which becomes available to other threads. At some later time, 
 * the write thread enters the monitor, writes some data into the buffer, executes a notify, and exits the monitor. 
 * When the write thread executes the notify, the read thread is marked for eventual resurrection. After the write 
 * thread has exited the monitor, the read thread is resurrected as the owner of the monitor. If there is any chance 
 * that some other thread has come along and consumed the data left by the write thread, the read thread must explicitly
 * check to make sure the buffer is not empty. If there is no chance that any other thread has consumed the data, 
 * then the read thread can just assume the data exists. The read thread reads some data from the buffer and exits 
 * the monitor. 
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月11日-下午1:23:58
 */
public class MonitorOfJava {

}
