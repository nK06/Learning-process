package com.panther.web.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component                         // ContextRefreshedEvent 事件是spring整个初始化完毕会执行
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MockQueue mockQueue;
    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        // 因为存在一个while 循环。需要单独开一个线程，否则会阻塞整个应用启动。
        new Thread(() -> {
            while (true){
                if(StringUtils.isNotBlank(mockQueue.getCompleOrder())) {
                    String orderNumber = mockQueue.getCompleOrder();
                    logger.info("返回订单处理结果"+orderNumber);
                    deferredResultHolder.getMap().get(orderNumber).setResult("Place order success");
                    mockQueue.setCompleOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
