package operation.system.netty;

import com.framework.web.utils.ObjectCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 服务端处理器
 */
public class MarketNettyServerHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
        // 基于定长的方式解决粘包/拆包问题
        cp.addLast(new LengthFieldPrepender(2));
        cp.addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 2, 0, 2));
        // 序列化方式 可使用 MsgPack 或 Protobuf 进行序列化扩展 具体可参考study-netty项目下的相关使用例子
        cp.addLast(new ObjectCodec());
        // 心跳机制
        cp.addLast(new IdleStateHandler(3, 10, 0, TimeUnit.SECONDS));
        cp.addLast(new MarketNettyServerHandlerAdapter());
    }
}
