package com.xiaoyue26.practice;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by xiaoyue26 on 17/12/11.
 */
public class RpcClient {

    public static void main(String[] args) throws TException {
        // *) 传输层
        TTransport transport = new TSocket("localhost", 9000);
        transport.open();
        // *) 协议层, 与服务端对应
        TProtocol protocol = new TBinaryProtocol(transport);
        // *) 创建RPC客户端
        HelloService.Client client = new HelloService.Client(protocol);
        // *) 调用服务
        System.out.println(client.hello("world"));
        // *) 关闭句柄
        transport.close();
    }
}
