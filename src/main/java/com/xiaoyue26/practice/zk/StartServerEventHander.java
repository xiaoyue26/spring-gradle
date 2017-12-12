package com.xiaoyue26.practice.zk;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;

/**
 * Created by xiaoyue26 on 17/12/12.
 */
public class StartServerEventHander implements TServerEventHandler {
    @Override
    public void preServe() {//在serve方法前做什么. // 标记一下已经开始提供服务.
        /*synchronized (isThriftStart) {
            isThriftStart++;//当全部服务启动成功才连接zk
            if (isThriftStart == 1) {
                synchronized (ServiceStart.this) {
                    ServiceStart.logger.info("thrift服务启动完成");
                    ServiceStart.this.notify();
                }
            }
        }*/

    }

    @Override
    public ServerContext createContext(TProtocol input, TProtocol output) {
        return null;
    }

    @Override
    public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output) {

    }

    @Override
    public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport) {

    }
}