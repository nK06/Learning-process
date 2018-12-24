package com.panther.web.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockQueue {

    private Logger logger = LoggerFactory.getLogger(AsyncController.class);

    // 生成订单的消息
    private String placeOrder;

    // 订单完成消息
    private String compleOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws InterruptedException {
        new Thread(() -> {
            logger.info("接到下单消息" + placeOrder);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.compleOrder = placeOrder;
            logger.info("下单请求处理完毕" + placeOrder);
        }).start();
    }

    public String getCompleOrder() {
        return compleOrder;
    }

    public void setCompleOrder(String compleOrder) {
        this.compleOrder = compleOrder;
    }
}
