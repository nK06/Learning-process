package com.panther.thread;

/**
 * @author makai
 * @version 1.0
 * @ClassName Thread01
 * @Description TODO
 * @date 2019-05-23 9:28
 */
public class Thread01 {

    public static void main(String[] args){
        int[] res = new int[6];
        res[4] = 6;
        res[5] = 8;

        System.out.println(res[4]);
        System.out.println(res[0]);
        System.out.println(res[1]);
        System.out.println(res[5]);
    }
}
