package com.xiaoyue.nov.rpc;

import com.xiaoyue26.practice.rpcPort.HelloService;
import com.xiaoyue26.practice.rpcPort.HelloServiceImpl;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoyue26 on 17/12/12.
 */
@Service
@ConfigurationProperties("rpcServer.helloService")
public class HelloThriftService  extends BaseService implements IService{
    private Integer port;
    private String host;
    private String zkNode;
    private String connectString;

    private void startThread() {
        log.info("启动Thrift线程");
        // 创建启动线程：
        ServerThread studenThread = new ServerThread(port
                , new HelloService.Processor<HelloService.Iface>(new HelloServiceImpl()));
        // TODO 优化线程组织.
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.submit(studenThread);
        pool.shutdown(); //关闭线程池：线程仍然在运行
    }

    private void connectZk() throws Exception {
        // 连接到zk服务器集群，添加默认的watcher监听
        // connectedSignal.await();
        String data = host + ":" + port;
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .build();//.namespace("")
        client.start();
        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(zkNode, data.getBytes());
        log.info("===================zkNode注册完成===================");
    }


    @Override
    public void serve() {
        startThread();
        try {
            connectZk();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getZkNode() {
        return zkNode;
    }

    public void setZkNode(String zkNode) {
        this.zkNode = zkNode;
    }


    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }
}
