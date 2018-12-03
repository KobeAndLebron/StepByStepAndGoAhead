package com.cjs.JVM内存.dumpTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OOMHeapTest {
    public static void main(String[] args){
        oom();
    }
    
    private static void oom(){
        Map<String, Pilot> map = new HashMap<String, Pilot>();
        Object[] array = new Object[1000000];
        for(int i=0; i<1000000; i++){
            String d = new Date().toString();
            Pilot p = new Pilot(d, i);
            map.put(i+"rosen jiang", p);
            array[i]=p;
        }
    }
}