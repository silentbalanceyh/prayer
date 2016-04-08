package com.prayer.ag.util.linklist;

/**
 * 单链循环表
 * 
 * @author Lang
 *
 */
public class SingleLinkList<T> {
    // ~ Static Fields =======================================
    // ~ Instance Fields =====================================
    /** 首节点 **/
    private SingleNode<T> head;
    /** 当前节点 **/
    private SingleNode<T> current;
    /** 尾节点 **/
    private SingleNode<T> tail;

    // ~ Static Block ========================================
    // ~ Static Methods ======================================
    // ~ Constructors ========================================
    /**
     * 构造函数，初始化动作
     */
    public SingleLinkList() {
        /** 1.初始化head **/
        this.head = new SingleNode<>();
        /** 2.单个节点，tail和current指向head **/
        this.tail = this.head;
        this.current = this.head;
    }

    // ~ Abstract Methods ====================================
    // ~ Override Methods ====================================
    // ~ Methods =============================================
    /**
     * 将Current设置到Head
     */
    public void setBegin() {
        this.current = this.head;
    }
    /**
     * 设置当前值
     * @param value
     */
    public void setCurrent(final T value){
        this.current.setValue(value);
    }

    /**
     * 
     */
    public void moveNext() {
        this.current = this.current.getNext();
    }

    /**
     * 将Current移动到Tail前一个
     */
    public T removeCurrent() {
        T value = null;
        if (this.head != this.tail) {
            final SingleNode<T> cur = this.current;
            // Header移开，防止空指针
            if(this.head == cur){
                this.head = this.head.getNext();
            }
            this.current = this.head;
            while (cur != this.current.getNext()) {
                this.current = this.current.getNext();
            }
            this.current.setNext(cur.getNext());
            cur.setNext(null);
            // 需要转移Current
            this.current = this.current.getNext();
            value = cur.getValue();
        }
        return value;
    }

    /**
     * 在表尾添加新节点
     * 
     * @param value
     * @return
     */
    public void addNode(final T value) {
        /**
         * 1.head中无值，这个时候head和tail指向同一个节点
         */
        if (null == head.getValue()) {
            head.setValue(value);
        } else {
            /**
             * 2.tail不可能为null了这个时候
             */
            final SingleNode<T> add = new SingleNode<>(value);
            this.tail.setNext(add);
            this.tail = this.tail.getNext();
            this.tail.setNext(this.head);
            /**
             * 3.current跟着tail
             */
            this.current = this.tail;
        }
    }

    /** **/
    public int size() {
        int number = 0;
        if (null == this.head.getValue()) {
            number = 0;
        } else {
            number = 1;
        }
        this.current = this.head.getNext();
        while (head != current) {
            current = current.getNext();
            number++;
        }
        return number;
    }

    /** 判断链表是否为空 **/
    public boolean isEmpty() {
        return null == head.getValue() && head == head.getNext();
    }

    // ~ Value Extract ========================================
    /**
     * 
     * @return
     */
    public T getTail() {
        return extract(this.tail);
    }

    /**
     * 
     * @return
     */
    public T getCurrent() {
        return extract(this.current);
    }

    /**
     * 
     * @return
     */
    public T getHead() {
        return extract(this.head);
    }

    /**
     * 打印List
     */
    public void print(final boolean appendLine) {
        SingleNode<T> tempCur = this.head;
        do {
            if(null == tempCur){
                break;
            }
            System.out.print(tempCur.getValue());
            if(String.valueOf(tempCur.getValue()).length() > 1){
                System.out.print(" ");
            }else{
                System.out.print("  ");
            }
            tempCur = tempCur.getNext();
        } while (this.head != tempCur);
        if(appendLine){
            System.out.println();
        }
    }
    /**
     * 
     * @return
     */
    public int getPosition(){
        SingleNode<T> tempCur = this.head;
        int i = 0;
        do{
            if(null == tempCur){
                break;
            }
            i++;
            tempCur = tempCur.getNext();
        }while(this.current != tempCur);
        return i;
    }
    // ~ Private Methods =====================================

    private T extract(final SingleNode<T> node) {
        T value = null;
        if (null != node) {
            value = node.getValue();
        }
        return value;
    }
    // ~ Get/Set =============================================
    // ~ hashCode,equals,toString ============================
    /** **/
    @Override
    public String toString(){
        final StringBuilder ret = new StringBuilder();
        SingleNode<T> tempCur = this.head;
        do {
            if(null == tempCur){
                break;
            }
            ret.append(tempCur.getValue()).append(' ');
            tempCur = tempCur.getNext();
        } while (this.head != tempCur);
        return ret.toString();
    }
}
