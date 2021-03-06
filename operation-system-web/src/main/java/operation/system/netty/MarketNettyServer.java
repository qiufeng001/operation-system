package operation.system.netty;

import com.framework.web.utils.ObjectCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import operation.system.configuration.NettyServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Netty服务器监听器
 * <p>
 * create by 叶云轩 at 2018/3/3-下午12:21
 * contact by tdg_yyx@foxmail.com
 *
 * @author 叶云轩 contact by tdg_yyx@foxmail.com
 * @date 2018/8/15 - 12:26
 */
@Component
public class MarketNettyServer {

    /**
     * NettyServerListener 日志控制器
     * Create by 叶云轩 at 2018/3/3 下午12:21
     * Concat at tdg_yyx@foxmail.com
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MarketNettyServer.class);

    /**
     * 创建bootstrap
     */
    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    /**
     * BOSS
     */
    private EventLoopGroup boss = new NioEventLoopGroup();

    /**
     * Worker
     */
    private EventLoopGroup work = new NioEventLoopGroup();

    @Value("${netty.port}")
    private int port;

    @Value("${netty.max_threads}")
    private int maxThreads;

    @Value("${netty.max_frame_length}")
    private int maxFrameLength;

    /**
     * 通道适配器
     */
   /* @Resource
    private MarketNettyServerHandlerAdapter channelHandlerAdapter;
*/

    /**
     * NETT服务器配置类
     */
 /*   @Resource
    private NettyServerConfig nettyConfig;*/

    /**
     * 开启服务线程
     */
    public void start() {
        // 从配置文件中(application.yml)获取服务端监听端口号
        // int port = nettyConfig.getPort();
        try {
            //设置事件处理
            serverBootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加心跳支持
                            pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                            // 基于定长的方式解决粘包/拆包问题
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(maxFrameLength, 0, 2, 0, 2));
                            pipeline.addLast(new LengthFieldPrepender(2));
                            // 序列化
                            pipeline.addLast(new ObjectCodec());
                            pipeline.addLast(new MarketNettyServerHandler());
                        }
                    });
            System.out.println("netty服务器在" + port + "端口启动监听");
            // LOGGER.info("netty服务器在[{}]端口启动监听", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    /**
     * 关闭服务器
     */
    @PreDestroy
    public void close() {
        LOGGER.info("关闭服务器....");
        //优雅退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
    }
}
