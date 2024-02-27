package com.druh.myRPCVersion4.codec;

import java.io.*;

/*
    Java 原生序列化
 */
public class ObjectSerializer implements Serializer {

    // 利用java IO 对象 -> 字节数组
    // 利用ObjectOutputStream将对象写入ByteArrayOutputStream，然后将流的内容转换为字节数组
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = null;
        // 创建一个ByteArrayOutputStream，它是一个将数据写入字节数组的输出流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 创建一个ObjectOutputStream，用于将对象写入底层流（在这里是ByteArrayOutputStream）
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            // 刷新ObjectOutputStream，确保任何缓冲的数据都被写入底层流
            oos.flush();
            // 从ByteArrayOutputStream获取字节数组
            bytes = bos.toByteArray();

            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 字节数组 -> 对象
    // 使用ByteArrayInputStream和ObjectInputStream来从字节数组中还原对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object object = null;
        // 创建一个ByteArrayInputStream，它是一个从字节数组中读取数据的输入流
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            // 创建一个ObjectInputStream，用于从底层流（在这里是ByteArrayInputStream）读取对象
            ObjectInputStream ois = new ObjectInputStream(bis);
            // 从ObjectInputStream读取对象
            object = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    // 0 代表java原生序列化器
    @Override
    public int getType() {
        return 0;
    }
}
