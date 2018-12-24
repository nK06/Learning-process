package com.panther.security.core.properties;

/***
 * 图片验证码默认配置类
 */
public class ImageCodeProperties extends SmsCodeProperties{

    // 构造函数。将继承父类的长度配置修改成4。这样在new 一个ImageCodeProperties 长度都是4
    public ImageCodeProperties (){
        setLength(4);
    }

    private int width = 70;
    private int height = 25;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
