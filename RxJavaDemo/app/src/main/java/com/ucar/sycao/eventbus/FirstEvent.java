package com.ucar.sycao.eventbus;

/**
 * Created by sycao on 2017/4/21.
 */

public class FirstEvent {

    String msg;

    public FirstEvent(String msg){
        this.msg=msg;
    }

    public String getMsg() {
        return msg;
    }
}
