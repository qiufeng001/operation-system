package operation.system.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import operation.system.inspect.RequestDispatcher;
import operation.system.model.MethodInvokeMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 服务端处理器适配器
 */
public class MarketNettyServerHandlerAdapter extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(MarketNettyServerHandlerAdapter.class);

    /**
     * 注入请求分排器
     */
 /*   @Resource
    private RequestDispatcher dispatcher;*/
    private int lossConnectCount = 0;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    /**
     * 服务器接收到消息时进行进行的处理
     *
     * @param channelHandlerContext channelHandlerContext
     * @param msg                   msg
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) {
        if (msg instanceof String) {
            if ("ping-pong-ping-pong".equals(msg)) {
                log.info("{} -> [心跳监测] {}：通道活跃", this.getClass().getName(), channelHandlerContext.channel().id());
                // 心跳消息
                lossConnectCount = 0;
                return;
            }
        }
        // 转换为MethodInvokeMeta
        MethodInvokeMeta invokeMeta = (MethodInvokeMeta) msg;
        // 具体的处理类
        // this.dispatcher.dispatcher(channelHandlerContext, invokeMeta);
    }

    /**
     * 触发器
     *
     * @param channelHandlerContext channelHandlerContext
     * @param evt
     * @throws Exception exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object evt) throws Exception {
        log.info("{} -> [已经有5秒中没有接收到客户端的消息了]", this.getClass().getName());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    log.info("{} -> [释放不活跃通道] {}", this.getClass().getName(), channelHandlerContext.channel().id());
                    channelHandlerContext.channel().close();
                }
            }
        } else {
            super.userEventTriggered(channelHandlerContext, evt);
        }
    }
}
