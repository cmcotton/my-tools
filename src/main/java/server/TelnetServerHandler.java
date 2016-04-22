/*
 * 版權宣告: FDC all rights reserved.
 */
package server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import parser.CEFParser;
import db.DBConnection;
import db.DBConnectionNative;
import entity.Event;
import errorhandle.MyCEFParsingException;

/**
 * Handles a server-side channel.
 */
@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

    static int counter = 0;

    private Logger log = LoggerFactory.getLogger(getClass());
    private CEFParser parser = new CEFParser();
    private DBConnection db = new DBConnectionNative();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    public void messageReceived(ChannelHandlerContext ctx, String request) {
        // Generate and write a response.
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else {
            response = "Did you say '" + request + "'?\r\n";
        }

        // We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the
        // conversion.
        ChannelFuture future = ctx.write(response);

        // Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel
     * .ChannelHandlerContext, java.lang.Object)
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("---------------------------------{}", counter++);
  
        List<Event> evts = null; 
        
        try {
            
            evts = parser.parse(msg);
            
        } catch(MyCEFParsingException e) {
            
            parser.handleParsingError(msg);
            
        }
             
        db.connect();
        for (Event e : evts) {
            db.save(e);
        }
//        db.close();
        log.debug("---------------------------------------------------------");
        
//        messageReceived(ctx, msg);
    }
}
