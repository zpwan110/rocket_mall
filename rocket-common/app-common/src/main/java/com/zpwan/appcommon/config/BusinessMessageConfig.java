package com.zpwan.appcommon.config;

import com.google.common.collect.Maps;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 统一消息管理
 *
 * @author 吴爱军
 */
@Component
@ConfigurationProperties(prefix = "message")
public class BusinessMessageConfig {

    private Map<Integer, String> global = Maps.newHashMap();

    private final Map<Integer, String> serviceAsset = Maps.newHashMap();

    private final Map<Integer, String> serviceFundflow = Maps.newHashMap();

    private final Map<Integer, String> serviceParty = Maps.newHashMap();

    private final Map<Integer, String> serviceProduct = Maps.newHashMap();

    private final Map<Integer, String> serviceWorkflow = Maps.newHashMap();

    private final Map<Integer, String> appAdmin = Maps.newHashMap();

    private final Map<Integer, String> appCommon = Maps.newHashMap();

    private final Map<Integer, String> appMijin = Maps.newHashMap();

    public Map<Integer, String> getGlobal() {
        return global;
    }

    public void setGlobal(Map<Integer, String> global) {
        this.global = global;
    }

    public Map<Integer, String> getServiceAsset() {
        return serviceAsset;
    }

    public Map<Integer, String> getServiceFundflow() {
        return serviceFundflow;
    }

    public Map<Integer, String> getServiceParty() {
        return serviceParty;
    }

    public Map<Integer, String> getServiceProduct() {
        return serviceProduct;
    }

    public Map<Integer, String> getServiceWorkflow() {
        return serviceWorkflow;
    }

    public Map<Integer, String> getAppAdmin() {
        return appAdmin;
    }

    public Map<Integer, String> getAppCommon() {
        return appCommon;
    }

    public Map<Integer, String> getAppMijin() {
        return appMijin;
    }
}
