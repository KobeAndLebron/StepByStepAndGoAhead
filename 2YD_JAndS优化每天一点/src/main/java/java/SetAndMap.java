package java;
/**
 * 当要在HashSet或者HashMap中add/put之前判断是否存在key时，可以直接使用add/put方法然后根据返回值来判断，因为put/add方法会将以前节点的value返回
 * （对于Set来说会将Object对象返回，涉及到HashSet的实现方式：用HashMap实现，然后每一个节点的value放的都是同一个Object对象）
 * 如果不存在则返回null说明不包含这个key，否则返回非空说明包含这个key。这样可以避免contain的多余使用，当这样的操作(开头所说的操作)较多时会提高时间效率~~~
 * 
 * 可以查看ContainsDuplicate2HashMap.java和ContainsDuplicate2HashSet.java这两道题，然后参照leetcode对应的运行时间，就可以看到时间差距
 * <a>http://blog.csdn.net/github_34160016/article/details/51145451</a>
 * 
 * 
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月13日-下午5:36:16
 */
public class SetAndMap {

}
