package com.cnorz.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
/*
 * @Author zfx1509
 * @Description 处理消息的handler，TextWebSocketFrame是为websocket专门处理文本的载体
 * @Date 10:39 上午 2020/7/30
 * @Param
 * @return
 **/

public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    // 用于记录和管理所有客户端的channel
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传输过来的消息
        String content = msg.text();
        System.out.println("接受到的数据：" + content);
        for(Channel channel: clients) {
            channel.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]" + LocalDateTime.now()
                    + ",消息为" +content));
        }
        // clients.writeAndFlush(new TextWebSocketFrame("[服务器接收到消息：]" + LocalDateTime.now()
        //        + ",消息为" +content));
    }

    // 当客户端联接服务端后，获取客户端channel，并放到ChannelGroup中进行管理
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        // clients.remove(ctx.channel());
        System.out.println("longId" + ctx.channel().id().asLongText());
        System.out.println("sortId" + ctx.channel().id().asShortText());
    }
}
