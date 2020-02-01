package com.iec.interaction;

import com.iec.analysis.exception.*;
import com.iec.analysis.protocol101.Analysis;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @description
 * @author: mujave
 * @create: 2020-01-31 15:35
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        String msg_str = byteBuf.toString(Charset.forName("utf-8"));
        System.out.println(new Date() + ": 服务端读到数据 -> " + msg_str);

        // 回复数据到客户端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf out = getByteBuf(ctx, msg_str);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String msg) {
        String result = null;
        try {
            result = Analysis.analysis(msg);
        } catch (LengthException e) {
            result = e.toString();
        } catch (CustomException e) {
            result = e.toString();
        } catch (UnknownLinkCodeException e) {
            result = e.toString();
        } catch (UnknownTransferReasonException e) {
            result = e.toString();
        } catch (UnknownTypeIdentifierException e) {
            result = e.toString();
        } catch (IllegalFormatException e) {
            result = e.toString();
        }
        byte[] bytes = result.getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
