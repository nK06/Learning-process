package com.panther.test;


import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SockerTest {


    public static void main(String[] args) throws UnknownHostException, UnsupportedEncodingException {

        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address.getHostName());

        System.out.println(address.getHostAddress());
        System.out.println(new String(address.getAddress(),"UTF-8"));

    }

}
