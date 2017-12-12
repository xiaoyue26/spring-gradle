package com.xiaoyue.nov.rpc;

import com.xiaoyue26.practice.zk.ServiceStart;
import com.xiaoyue26.practice.zk.StartServerEventHander;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by xiaoyue26 on 17/12/12.
 */
public class ServerThread  implements Runnable {
    private Integer port;
    private TProcessor processor;

    public ServerThread(Integer port, TProcessor processor) {
        this.port = port;
        this.processor = processor;
    }

    @Override
    public void run() {
        //ServiceStart.logger.info("thrift服务正在准备启动");
        try {
            // 非阻塞式
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(port);
            // 为服务器设置对应的IO网络模型
            TNonblockingServer.Args tArgs = new TNonblockingServer.Args(serverSocket);
            // 设置控制器
            tArgs.processor(processor);
            // 设置消息封装格式
            tArgs.protocolFactory(new TBinaryProtocol.Factory());//Thrift特有的一种二进制描述格式
            // 启动Thrift服务
            TNonblockingServer server = new TNonblockingServer(tArgs);
            server.setServerEventHandler(new LogEventHandler());// 对于一些事件,仅打个log
            server.serve();//启动后,程序就停在这里了。
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
