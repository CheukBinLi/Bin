
package com.cheuks.bin.anythingtest.netty.privatestack;

import java.security.cert.CertificateException;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.SSLException;

import com.cheuks.bin.anythingtest.netty.BaseServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.AttributeKey;

public class server extends BaseServer {

	SslContext sslCtx = null;
	SelfSignedCertificate ssc = null;

	private final AtomicLong count = new AtomicLong();

	public server() throws CertificateException, SSLException {
		super();
		ssc = ssc = new SelfSignedCertificate();
		sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
	}

	static AttributeKey<AtomicLong> id = AttributeKey.newInstance("id");

	@Override
	public ServerBootstrap setting(ServerBootstrap server) {
		return server.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new MessageDecoder(1024, 4, 4));
				ch.pipeline().addLast(new MessageEncoder());
				//ssl
				//				ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
				//				ch.pipeline().addLast(new IdleStateHandler(5, 5, 10));
				ch.pipeline().addLast(new ServerHandler());
			}
		}).option(ChannelOption.SO_TIMEOUT, 20).childAttr(id, count);
	}

	public static void main(String[] args) throws InterruptedException, CertificateException, SSLException {
		new server().bind(1191);
	}
}
