package hdu.dqj.RPCServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * @Author dqj
 * @Date 2019/12/24
 * @Version 1.0
 * @Description 服务端用来接收并处理消息的类
 */
public class ServerStub {
    // 用来存放Server端服务对象的缓存
    private HashMap<String, Class> remoteServices = new HashMap<>();

    // 把一个Server端对象放到缓存中
    public void register(Class className, Class remoteImpl) {
        remoteServices.put(className.getName(), remoteImpl);
    }

    public void run() throws Throwable {
        ServerSocket listener = new ServerSocket(8090);
        System.out.println("Server等待客户端的连接...");
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        Socket socket = null;
        try {
            while (true) {
                socket = listener.accept();
                // 将收到的请求信息反序列化
                input = new ObjectInputStream(socket.getInputStream());
                // 依次从客户端接收方法名、方法所属的类名或接口名、方法参数类型、方法参数值
                String methodName = input.readUTF();
                String className = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] params = (Object[]) input.readObject();
                System.out.println("Server收到：" + className + ", " + methodName);

                // 根据上述收到的信息，调用相应类中的方法并返回结果。
                Class serverClass = remoteServices.get(className);
                Method method = serverClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serverClass.newInstance(), params);

                // 返回结果
                output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(result);
                System.out.println("Server返回：" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
            listener.close();
        }
    }
}
