package com.xiaoyue.nov.rpc;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TTransport;

/**
 * Created by xiaoyue26 on 17/12/12.
 */
public class LogEventHandler implements TServerEventHandler {

    @Override
    public void preServe() {

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
