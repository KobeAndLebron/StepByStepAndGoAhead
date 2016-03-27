package com.cjs.单例模式;

/**
 * 懒汉式单例模式
 *
 * @author 陈景帅
 *
 * 每天进步一点——2016年2月26日
 *
 */
public class Singeton3 {
	private Singeton3(){     
	    
    }     
    
    private static Singeton3 instance;
    
    // 非线程安全
    public static Singeton3 getInstance(){     
        if(instance == null){     
            return instance = new Singeton3();     
        }else{     
            return instance;     
        }  
    }
    
    // 线程安全但是效率低下
    public static synchronized Singeton3 getInstance1(){     
        if(instance == null){     
            return instance = new Singeton3();     
        }else{     
            return instance;     
        }  
    }
    
    // 通过双检索机制来提高效率
    public static Singeton3 getInstance2(){     
        if(instance == null){ 
        	synchronized(Singeton3.class){
        		if(instance == null){ 
        			return instance = new Singeton3();
        		}
        	}
        }
        
        return instance;     
    } 
    public String toString(){
    	return this.hashCode() + this.getClass().getName();
    }
    public static void main(String[] args) {
    	Runnable run1 = new Runnable(){
			public void run() {
				System.out.println(Singeton3.getInstance2());
			}
    		
    	};
    	
    	Runnable run2 = new Runnable(){
			public void run() {
				System.out.println(Singeton3.getInstance2());
			}
    		
    	};
    	
    	Thread thread1 = new Thread(run1);
    	Thread thread2 = new Thread(run2);
    	thread1.start();
    	thread2.start();
	}
}

//select 学生表.id , 学生表.name , avg(成绩表.grade)
//from 学生表
//left join 成绩表 ob 学生表.id = 成绩表.学生id
//where 成绩表.grade < 60
//group by 学生表.id 
//having count(*) >= 2	
