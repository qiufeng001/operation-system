package operation.system.inspect;

import operation.system.netty.MarketNettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private MarketNettyServer server;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

       /* //在容器加载完毕后启动线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                server.start();
            }
        });

        thread.start();*/

        server.start();
    }
}