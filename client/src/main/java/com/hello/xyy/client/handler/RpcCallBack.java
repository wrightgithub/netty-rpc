package com.hello.xyy.client.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hello.xyy.model.Request;
import com.hello.xyy.model.Response;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/13
 */
public class RpcCallBack {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Object object = new Object();
    private Response response;
    private Request request;

    public RpcCallBack(Request request) {
        this.request = request;
    }

    //public Object waitResponse() throws InterruptedException {
    //
    //    synchronized (object){
    //        while (response == null) {
    //            object.wait();
    //        }
    //    }
    //    return this.response.getResult();
    //
    //}
    //
    //public void notifyWait(Response response) {
    //    this.response = response;
    //    synchronized (object){
    //        object.notifyAll();
    //    }
    //
    //}

    public Object waitResponse() throws Throwable {
        lock.lock();
        try {
            while (response == null) {
                condition.await();
            }
            if (response.getThrowable() != null) {
                throw response.getThrowable();
            }
            return this.response.getResult();
        } finally {
            lock.unlock();
        }

    }

    public void notifyWait(Response response) {
        this.response = response;
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    public Request getRequest() {
        return request;
    }
}
