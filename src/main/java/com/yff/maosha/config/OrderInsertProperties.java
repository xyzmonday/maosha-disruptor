package com.yff.maosha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "order-insert.proc")
public class OrderInsertProperties {

    /**
     * 处理器数量
     */
    private int num;

    /**
     * 单次执行的SQL条数 (将多条SQL放到一起执行比分多次执行效率高)
     */
    private int sqlBufferSize;

    /**
     * disruptor队列长度, 值必须是2的次方
     */
    private int queueSize;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSqlBufferSize() {
        return sqlBufferSize;
    }

    public void setSqlBufferSize(int sqlBufferSize) {
        this.sqlBufferSize = sqlBufferSize;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

}
