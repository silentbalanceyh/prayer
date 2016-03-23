package com.prayer.demo;

public aspect HelloWorldCheat {
	
    pointcut HelloWorldPointCut():execution(void com.hpe.training.basic.HelloWorldMain.sayHello(..));
    
    before(): HelloWorldPointCut(){
        System.out.println("How are you ?");
    }
    
    after(): HelloWorldPointCut(){
        System.out.println("I'm fine, thank you!");
    }
}
