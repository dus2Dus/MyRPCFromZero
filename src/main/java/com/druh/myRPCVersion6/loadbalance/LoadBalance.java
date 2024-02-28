package com.druh.myRPCVersion6.loadbalance;

import java.util.List;

/**
 * 传进来一个服务器地址列表，算法可以根据不同的负载均衡策略选择一个
 */
public interface LoadBalance {
    String balance(List<String> addressList);
}
