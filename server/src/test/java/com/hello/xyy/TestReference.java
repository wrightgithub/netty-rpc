package com.hello.xyy;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/07
 */
class Car {
    private double price;
    private String colour;

    public Car(double price, String colour) {
        this.price = price;
        this.colour = colour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String toString() {
        return colour + "car costs $" + price;
    }

}

public class TestReference {

    @Test
    public void softCanNotFirstBeGC(){
        Car car = new Car(22000, "silver");
        // 软引用，只在内存快溢出的时候才被gc
        SoftReference<Car> softCar = new SoftReference<Car>(car);
        int i = 0;
        car=null;
        while (softCar.get() != null) {
            System.out.println(String.format("Get str from object of SoftReference: %s, count: %d", softCar.get().getColour(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("object a was cleared by JVM!");
    }

   @Test
    public   void firstCanBeGc(){
        Car car = new Car(22000, "silver");
        // 弱引用，不在有改对象的引用时，在下一次gc被释放。
        WeakReference<Car> weakCar = new WeakReference<Car>(car);
        int i = 0;
        car=null;
        while (weakCar.get() != null) {
            System.out.println(String.format("Get str from object of WeakReference: %s, count: %d", weakCar.get().getColour(), ++i));
            if (i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("object a was cleared by JVM!");
    }
    @Test
    public  void canBeCollected() {
        Car car = new Car(22000, "silver");
        WeakReference<Car> weakCar = new WeakReference<Car>(car);

        int i = 0;

        while (true) {
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar);
            } else {
                System.out.println("Object has been collected.");
                break;
            }
        }
    }

    @Test
    public  void canNotBeCollected() {
        Car car = new Car(22000, "silver");
        WeakReference<Car> weakCar = new WeakReference<Car>(car);

        int i = 0;

        while (true) {
            car.getColour();
            if (weakCar.get() != null) {
                i++;
                System.out.println("Object is alive for " + i + " loops - " + weakCar);
            } else {
                System.out.println("Object has been collected. i="+i);
                break;
            }
        }
    }


}