package hdu.dqj.RPCServer;

import hdu.dqj.RPCServer.Service.HelloService;
import hdu.dqj.RPCServer.Service.HelloServiceImpl;

/**
 * @Author dqj
 * @Date 2019/12/24
 * @Version 1.0
 * @Description 服务端的启动类
 */
public class RPCServer {
    public static void main(String[] args) throws Throwable {
        // 为每个客户端创建一个线程。
        new Thread(() -> {
            try {
                ServerStub server = new ServerStub();
                server.register(HelloService.class, HelloServiceImpl.class);
                server.run();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }).start();
    }
}
