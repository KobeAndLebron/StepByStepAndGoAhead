1. notify方法只会将处于wait状态的thread唤醒, 并不会立即释放锁, 除非显示执行wait方法或者正常执行完synchronized临界区.

2. 一个对象要执行notify或者wait方法, 则当前线程必须拥有此对象的Monitor, 否则抛出IllegalMonitorStateException.
   一个线程获取一个对象的Monitor的方法：
        1. 调用此对象的synchronized方法
        2. 使用synchronized statement.
        3. 对于class对象, 通过调用这个class的同步static方法.

3. 在执行同步代码块的过程中, 遇到异常而导致代码执行跳出临界区, 锁也会被释放. TestReleaseLockWhenExceptionOccurs.java demonstrates it.