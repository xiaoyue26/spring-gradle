package com.xiaoyue26.practice.rpcPort;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaoyue26 on 17/12/11.
 * 监听9000端口,提供服务,同机器通信(单机).
 */
public class RpcServer {
    public void test1() {
        System.out.println("here");
    }

    public static void main(String[] args) throws TTransportException {
        Logger logger = LoggerFactory.getLogger(RpcServer.class);
        int port= 9000 ;
        // *) 传输层(Transport), 设置监听端口为9000
        TServerSocket serverTransport = new TServerSocket(port);

        // *) 协议层
        TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory(true, true);
        // *) 处理层(Processor)
        HelloServiceImpl handler = new HelloServiceImpl();
        HelloService.Processor<HelloServiceImpl> processor = new HelloService.Processor<HelloServiceImpl>(handler);

        // *) 服务层(Server)
        TServer server = new TThreadPoolServer(
                new TThreadPoolServer.Args(serverTransport)
                        .protocolFactory(protocolFactory)
                        .processor(processor));

        // *) 启动监听服务
        server.serve();
        logger.info("服务已经启动，端口为："+port);
    }
}
