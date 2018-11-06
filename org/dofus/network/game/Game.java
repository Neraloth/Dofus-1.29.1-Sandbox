package org.dofus.network.game;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class Game implements IoHandler {

	private final IoAcceptor acceptor;
	private boolean started;
	
	public Game() {
		this.acceptor = new NioSocketAcceptor();
        this.acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(
                        Charset.forName("UTF8"),
                        LineDelimiter.NUL,
                        new LineDelimiter("\n\0")
                )));
        this.acceptor.setHandler(this);
        this.acceptor.getSessionConfig().setReadBufferSize(1024);
        this.acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable object) throws Exception {
		System.out.println("[Game-" + session.getId() + "] exception " + object.getMessage());
	}

	@Override
	public void messageReceived(IoSession session, Object object) throws Exception {
		System.out.println("[Game-" + session.getId() + "] received " + (String) object);
	}

	@Override
	public void messageSent(IoSession session, Object object) throws Exception {
		System.out.println("[Game-" + session.getId() + "] sent " + (String) object);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("[Game-" + session.getId() + "] closed");
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("[Game-" + session.getId() + "] created");
		session.setAttribute("game");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus object) throws Exception {
		System.out.println("[Game-" + session.getId() + "] idle");
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("[Game-" + session.getId() + "] opened");
	}

	public void start(short port) throws IOException {
		if(started)
			return;
		
		acceptor.bind(new InetSocketAddress(port));
        started = true;
        
        System.out.println("Game listening on port " + port);
	}
	
	public void stop() {
		if(!started)
			return;

        acceptor.unbind();
        
        for(IoSession session : acceptor.getManagedSessions().values())
            if(session.isConnected() || !session.isClosing())
                session.close(false);
            
        acceptor.dispose();
        started = false;
        
        System.out.println("Game successfully stoped");
	}
}
