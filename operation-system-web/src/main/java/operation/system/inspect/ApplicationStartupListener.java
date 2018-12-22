package operation.system.inspect;

import operation.system.netty.MarketNettyServer;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

    /**
     * 注入NettyServer
     */
    @Autowired
    private MarketNettyServer server;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Thread thread = new Thread(new NettyServerThread());
        // 启动netty服务
        thread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    /**
     * netty服务启动线程 . <br>
     *
     * @author hkb
     */
    private class NettyServerThread implements Runnable {
        @Override
        public void run() {
            server.start();
        }
    }
}