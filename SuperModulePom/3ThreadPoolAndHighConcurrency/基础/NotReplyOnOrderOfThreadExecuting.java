
/**
 * 
 *  The manner in which a Java virtual machine implementation selects the next thread from the wait
 * or entry sets is a decision of individual implementation designers. For example, implementation 
 * designers can decide how to select: 
 * 		<ul>
 * 			<li>a thread from the wait set given a notify command</li>
 * 			<li>the order to resurrect threads from the wait set given a notify all command</li>
 * 	    	<li>the order to allow threads from the entry set to acquire the monitor</li>
 *  	</ul>
 *  It can use FIFO/LIFO and so on....
 *  
 *  In a word,JVM implementations are free to select the threads in an arbitrary manner
 * that defies/挑战 analysis and yields/生产 surprising orderings.
 *
 *  So as a programmer,you must not rely on any particular selection algorithm/算法  or 
 * treatment of priorities,as least if you are trying to write a java program that is 
 * platform independent. For example, because you don't know what order threads in the
 * wait set will be chosen for resurrection by the notify command, you should use notify
 * (as opposed to notify all) only when you are absolutely certain there will only be one
 * thread suspended({@linkplain java.lang.Thread$State.WAITING}/TIMED_WAITING) in the wait set.
 * 	If there is a chance more than one thread will be 
 * suspended in the wait set at any one time, you should probably use notify all. 
 * Otherwise, on some Java virtual machine implementations a particular thread may be stuck 
 * in the wait set for a very long time. If a notify always selects the most recent arrival 
 * from the wait set and the wait set always contains multiple threads, some threads that have
 *  been waiting the longest may never be resurrected. 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月7日-下午1:56:41
 */
public class NotReplyOnOrderOfThreadExecuting {

}
