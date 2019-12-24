package hdu.dqj.Client;

import hdu.dqj.RPCServer.Service.HelloService;

import java.lang.reflect.Proxy;

/**
 * @Author dqj
 * @Date 2019/12/24
 * @Version 1.0
 * @Description 客户端的启动类
 */
public class RPCClient {
    public static void main(String[] args){
        MyInvocationHandler invocationHandler = new MyInvocationHandler(HelloService.class);
        // 返回代理类实例。
        HelloService proxy = (HelloService) Proxy.newProxyInstance(HelloService.class.getClassLoader(),
                new Class<?>[]{HelloService.class}, invocationHandler);
        String result = proxy.sayHi("XD");
        System.out.println(result);
    }
}
