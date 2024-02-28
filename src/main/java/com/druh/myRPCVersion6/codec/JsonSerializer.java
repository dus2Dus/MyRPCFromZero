package com.druh.myRPCVersion6.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.druh.myRPCVersion6.common.RPCRequest;
import com.druh.myRPCVersion6.common.RPCResponse;

/**
 * Json序列化器
 * 由于json序列化的方式是通过把对象转化成字符串，丢失了Data对象的类信息，所以deserialize需要
 * 了解对象的类信息，根据类信息把JsonObject -> 对应的对象
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = JSONObject.toJSONBytes(object);
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object object = null;
        // 传输的消息分为request与response
        switch (messageType) {
            // 0 代表 RPCRequest
            case 0:
                // 使用JSON.parseObject将字节数组转换为RPCRequest对象
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                // 修bug 参数为空 直接返回
                if (request.getParams() == null) return request;

                // 创建一个与RPC请求对象的参数数组长度相同的新对象数组 objects,这个数组用于存放参数对象
                Object[] objects = new Object[request.getParams().length];
                // 把json字符串转化成对应的对象，fastjson可以读出基本数据类型，不用转化
                for (int i = 0; i < objects.length; i++) {
                    // 获取第 i 个参数的类型
                    Class<?> paramsType = request.getParamsTypes()[i];
                    // 检查 paramsType 表示的类是否与 request.getParamsTypes()[i] 数组中第 i 个参数的类相同，或者是其父类或接口
                    if (!paramsType.isAssignableFrom(request.getParams()[i].getClass())) {
                        objects[i] = JSONObject.toJavaObject((JSONObject) request.getParams()[i], request.getParamsTypes()[i]);
                    }else {
                        objects[i] = request.getParams()[i];
                    }
                }
                request.setParams(objects);
                object = request;
                break;
            case 1:
                // 1 代表 RPCResponse
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if(!dataType.isAssignableFrom(response.getData().getClass())){
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(),dataType));
                }
                object = response;
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return object;
    }

    // 1 代表着json序列化方式
    @Override
    public int getType() {
        return 1;
    }
}
