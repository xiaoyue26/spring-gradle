package com.xiaoyue26.practice.zk;

import com.xiaoyue26.practice.rpcPort.HelloService;
import com.xiaoyue26.practice.rpcPort.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoyue26 on 17/12/11.
 */
public class ServiceStart implements Watcher {
    private static Logger logger = LoggerFactory.getLogger(ServiceStart.class);
    private static final Integer[] PORTS = {8081, 8082};
    public static final String serviceNames[] = {"helloService", "peopleService"};
    private static final String SERVICE_IP = "127.0.0.1";
    private CountDownLatch connectedSignal = new CountDownLatch(1);//用于建立连接
    private ZooKeeper zk;
    private Integer isThriftStart = 0;

    private void startServer() {
        ServiceStart.logger.info("启动Thrift线程");
        // 创建启动线程：
        StartServerThread studenThread = new StartServerThread(PORTS[0],
                new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl()));

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(studenThread);
        //关闭线程池：线程仍然在运行
        pool.shutdown();
    }

    private class StartServerThread implements Runnable {
        private Integer port;
        private TProcessor processor;

        public StartServerThread(Integer port, TProcessor processor) {
            this.port = port;
            this.processor = processor;
        }

        @Override
        public void run() {
            ServiceStart.logger.info("thrift服务正在准备启动");
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
                server.setServerEventHandler(new StartServerEventHander());
                server.serve();//启动后,程序就停在这里了。
            } catch (TTransportException e) {
                e.printStackTrace();
            }

        }

    }

    private class StartServerEventHander implements TServerEventHandler {
        @Override
        public void preServe() {
            synchronized (isThriftStart) {
                isThriftStart++;//当全部服务启动成功才连接zk
                if (isThriftStart == 1) {
                    synchronized (ServiceStart.this) {
                        ServiceStart.logger.info("thrift服务启动完成");
                        ServiceStart.this.notify();
                    }
                }
            }

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

    private void connectZk() throws KeeperException, InterruptedException, IOException, JSONException {
        // 连接到zk服务器集群，添加默认的watcher监听
        zk = new ZooKeeper("127.0.0.1:2181", 120000, this);
        connectedSignal.await();
        // 创建一个父级节点Service
        Stat pathStat = null;
        try {
            pathStat = zk.exists("/Service", false);
            // 如果条件成立，说明节点不存在（只需要判断一个节点的存在性即可）
            // 创建的这个节点是一个“永久状态”的节点
            if (pathStat == null) {
                zk.create("/Service", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            System.exit(-1);
        }
        // 开始添加子级节点，每一个子级节点都表示一个这个服务提供者提供的业务服务
        for (int i = 0; i < 1; i++) {
            JSONObject nodeData = new JSONObject();
            nodeData.put("ip", SERVICE_IP);
            nodeData.put("port", PORTS[i]);
            zk.create("/Service/" + serviceNames[i], nodeData.toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL);
        }
        // 执行到这里，说明所有的service都启动完成了
        ServiceStart.logger.info("===================所有service都启动完成了，主线程开始启动===================");
    }

    @Override
    public void process(WatchedEvent event) {
        //建立连接用
        if (event.getState() == Event.KeeperState.SyncConnected) {
            connectedSignal.countDown();
            return;
        }
        //暂在这里不做处理，正常情况下需要处理。
    }

    public static void main(String[] args) {
        //启动服务
        ServiceStart s = new ServiceStart();
        s.startServer();
        //等待服务启动完成
        synchronized (s) {
            try {
                while (s.isThriftStart < 1) {
                    s.wait();
                }
            } catch (Exception e) {
                ServiceStart.logger.error(e.getMessage());
                System.out.println(-1);
            }
        }
        //启动连接
        try {
            s.connectZk();
        } catch (Exception e) {
            ServiceStart.logger.error(e.getMessage());
            System.out.println(-1);
        }
    }
}
