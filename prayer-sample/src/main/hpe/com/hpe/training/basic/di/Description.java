package com.hpe.training.basic.di;

public class Description {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    // ~ Private Methods =====================================
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================

}

class A1{
    private B ref;
    
    public A1(B ref){
        this.ref = ref;
    }
}

class A2{
    private B ref;
    
    public A2(){
        this.ref = new B();
    }
}

class B{
    
}
