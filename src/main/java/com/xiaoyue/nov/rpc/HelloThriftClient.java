package com.xiaoyue.nov.rpc;

import com.xiaoyue26.practice.rpcPort.HelloService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


/**
 * Created by xiaoyue26 on 17/12/12.
 */
public class HelloThriftClient extends BaseService {
    private String serverIp;
    private String serverPort;
    private String connectString = "127.0.0.1:2181";
    private String zkNodes = "/hello-service/rpc/v1";// 以后用配置注入.

    private void fetchZkInfo() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(3000)
                .retryPolicy(retryPolicy)
                .build();//.namespace("")
        client.start();

        NodeCache nodeCache = new NodeCache(client, zkNodes);
        nodeCache.getListenable().addListener(new NodeCacheListener() {

            @Override
            public void nodeChanged() throws Exception {
                updateServerInfo(nodeCache.getCurrentData().getData());

            }

        });
        nodeCache.start();
        updateServerInfo(client.getData().forPath(zkNodes));
    }


    private void updateServerInfo(byte[] bytes) {
        String data = new String(bytes);
        String[] dlist = data.split(":");
        serverIp = dlist[0];
        serverPort = dlist[1];
    }

    private void work() throws Exception {
        fetchZkInfo();
        TTransport tSocket = new TFramedTransport(
                new TSocket(serverIp, Integer.parseInt(serverPort), 30000));
        TBinaryProtocol protocol = new TBinaryProtocol(tSocket);//设置封装协议
        HelloService.Client client = new HelloService.Client(protocol);//建立调用client
        tSocket.open();
        System.out.println(client.hello("world"));//正式调用接口
        tSocket.close();//请求结束，断开连接
    }


    public static void main(String[] args) throws Exception {
        HelloThriftClient obj = new HelloThriftClient();
        obj.work();

    }


}
