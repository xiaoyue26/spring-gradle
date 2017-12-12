package com.xiaoyue26.practice.zk;

import com.xiaoyue26.practice.rpcPort.HelloService;
import com.xiaoyue26.practice.rpcPort.HelloServiceImpl;
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
    public static Logger logger = LoggerFactory.getLogger(ServiceStart.class);
    private static final Integer[] PORTS = {8081, 8082};
    public static final String serviceNames[] = {"helloService", "peopleService"};
    private static final String SERVICE_IP = "127.0.0.1";
    private CountDownLatch connectedSignal = new CountDownLatch(1);//用于建立连接

    private void startServiceThread() {
        ServiceStart.logger.info("启动Thrift线程");
        // 创建启动线程：
        StartServerThread studenThread = new StartServerThread(PORTS[0]
                , new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl()));
        // TODO 优化线程组织.
        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(studenThread);
        //关闭线程池：线程仍然在运行
        pool.shutdown();
    }

    private void connectZk() throws KeeperException, InterruptedException, IOException, JSONException {
        // 连接到zk服务器集群，添加默认的watcher监听
        ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 120000, this);
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
            zk.create("/Service/" + serviceNames[i]
                    , nodeData.toString().getBytes()
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.EPHEMERAL);
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

    public static void main(String[] args) throws InterruptedException, JSONException, KeeperException, IOException {
        //启动服务
        ServiceStart s = new ServiceStart();
        s.startServiceThread();//在指定的ip:port上启动服务.
        //等待服务启动完成
        s.connectZk();// 注册公布服务使用的ip:port.
    }
}
