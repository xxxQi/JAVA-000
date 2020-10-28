import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Administrator
 * @description: 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容
 * 是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 */
public class MyClassLoader extends ClassLoader {

    private static final String PATH = "/Hello.xlass";
    private static final String NAME = "Hello";
    private static final String METHOD = "hello";
    private Class<?> clzz;

    public static void main(String[] args) throws IOException {
        new MyClassLoader().findClassByPath(PATH, NAME).invoke();
    }

    private MyClassLoader findClassByPath(String path, String name) throws IOException {
        File f = new File(getClass().getResource(path).getPath());
        int len = (int) f.length();
        byte[] bytes = new byte[len];
        try {
            new FileInputStream(f).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = decode(bytes);
        this.clzz = defineClass(name, bytes, 0, len);
        return this;
    }

    private void invoke() {
        try {
            Object o = clzz.newInstance();
            Method method = o.getClass().getDeclaredMethod(METHOD);
            method.invoke(o);
        } catch (NullPointerException e) {
            throw new RuntimeException(" no found this file .");
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private byte[] decode(byte[] bytes) {
        byte[] newBytes = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i ++) {
            newBytes[i] = (byte) ~bytes[i];
        }
        return newBytes;
    }


}
