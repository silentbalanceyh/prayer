package com.prayer.demo.subroute;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class SubRoute2Handler implements Handler<RoutingContext>{
    
    private transient String flag;
    
    public SubRoute2Handler(final String flag){
        this.flag = flag;
    }
    @Override
    public void handle(final RoutingContext context) {
        System.out.println("[SUB-ROUTE2] " + this.flag + ", ID: " + Thread.currentThread().getName() + ", Info: " + context.currentRoute());
        context.next();
    }

}
