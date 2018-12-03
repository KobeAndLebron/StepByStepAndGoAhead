package com.cjs.gohead.interceptor;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.cjs.gohead.interceptor.factory.ServiceProxyFactory;
import com.cjs.gohead.interceptor.inter.ServiceInvocation;
import com.cjs.gohead.interceptor.inter.ServiceProxy;
import com.gohead.shared.test.ParentTest;

public class Client extends ParentTest<String>{
	private Class<?> serviceClass;
	
	public Client(int caseId, String expectedResult, Class<?> serviceClass) {
		super(caseId, expectedResult);
		this.serviceClass = serviceClass;
	}

    @Parameters
    public static Collection<Object[]> generateParameters() {
        return Arrays.asList(new Object[][]{
                new Object[]{1, "Success", com.cjs.gohead.interceptor.SimpleServiceImpl.class},
                new Object[]{2, "Fail", com.cjs.gohead.interceptor.SimpleServiceImpl1.class},
        });
    }

    @Test
	public void test(){
		if(!isIgnored()){
			ServiceInvocation serviceInvocation = ServiceProxyFactory.getServiceInvocation();
			ServiceProxy serviceProxy = ServiceProxyFactory.generateService(serviceInvocation, 
					serviceClass.getName(), "service");
			this.generatedObj = serviceProxy.execute().toString();
			this.generatedObj = serviceProxy.execute().toString();
		}
	}
}
