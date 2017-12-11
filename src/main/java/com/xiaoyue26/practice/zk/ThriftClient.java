package com.xiaoyue26.practice.zk;

import com.xiaoyue26.practice.rpcPort.HelloService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by xiaoyue26 on 17/12/11.
 */
public class ThriftClient implements Watcher {
    private static Logger logger = LoggerFactory.getLogger(ThriftClient.class);
    private String serverIp;
    private String serverPort;
    private String servername;
    private CountDownLatch connectedSignal = new CountDownLatch(1);//用于建立连接
    private ZooKeeper zk;

    private void init(String servername) throws IOException, KeeperException, InterruptedException, JSONException {
        // 连接到zk服务器集群，添加默认的watcher监听
        this.zk = new ZooKeeper("127.0.0.1:2181", 120000, this);
        connectedSignal.await();
        this.servername = servername;
        updateServer();
        ThriftClient.logger.info("初始化完成");
    }

    private void updateServer() throws KeeperException, InterruptedException, JSONException {
        this.serverIp=null;
        this.serverPort=null;
        /*
         *
         * 判断服务根节点是否存在
         */
        Stat pathStat = null;
        try {
            pathStat = this.zk.exists("/Service", false);
            // 如果条件成立，说明节点不存在
            // 创建的这个节点是一个“永久状态”的节点
            if (pathStat == null) {
                ThriftClient.logger.info("客户端创立Service");
                this.zk.create("/Service", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                return;
            }
        } catch (Exception e) {
            ThriftClient.logger.error(e.getMessage());
            System.exit(-1);
        }
        // 获取服务列表
        List<String> serviceList = this.zk.getChildren("/Service", false);
        if (serviceList == null || serviceList.isEmpty()) {
            ThriftClient.logger.info("未发现相关服务，客户端退出");
            return;
        }
        // 查找所需的服务是否存在
        boolean isFound = false;
        byte[] data;// 获取节点数据
        for (String name : serviceList) {
            if (StringUtils.equals(name, this.servername)) {
                isFound = true;
                break;// 找到一个就退出
            }
        }
        // 获得数据
        if (isFound) {
            data = this.zk.getData("/Service/" + this.servername, false, null);
        } else {
            ThriftClient.logger.info("未发现相关服务，客户端退出");
            return;
        }
        if (data == null || data.length == 0) {
            ThriftClient.logger.info("没有发现有效数据，客户端退出");
            return;
        }

        JSONObject fromObject = new JSONObject(new String(data));
        this.serverIp = fromObject.getString("ip");
        this.serverPort = fromObject.getString("port");
    }

    @Override
    public void process(WatchedEvent event) {
//建立连接用
        if(event.getState()== Event.KeeperState.SyncConnected){
            connectedSignal.countDown();
            return;
        }
        //如果发生 Service下的节点变换，就更新ip和端口
        if (event.getType() == Event.EventType.NodeChildrenChanged
                && "/Service".equals(event.getPath())) {
            try {
                updateServer();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ThriftClient studentClinet=new ThriftClient();
        /**
         * studnetService 测试
         */
        try {
            studentClinet.init(ServiceStart.serviceNames[0]);
            if(studentClinet.serverIp==null||studentClinet.serverPort==null){
                ThriftClient.logger.info("没有发现有效数据，客户端退出");
            }
            //如果是非阻塞型  需要使用
            TTransport tSocket = new TFramedTransport(new TSocket(studentClinet.serverIp,
                    Integer.parseInt(studentClinet.serverPort),  30000));
            //设置封装协议
            TBinaryProtocol protocol = new TBinaryProtocol(tSocket);
            //建立调用client
            HelloService.Client client=new HelloService.Client(protocol);
            //准备传输
            tSocket.open();
            //正式调用接口
            System.out.println(client.hello("world"));
            //请求结束，断开连接
            tSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
            ThriftClient.logger.info("出现异常，客户端退出");
        }

    }
}
