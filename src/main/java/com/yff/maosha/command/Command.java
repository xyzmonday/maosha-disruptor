package com.yff.maosha.command;

import java.io.Serializable;
import java.util.UUID;

/**
 * 当接受到秒杀请求之后，生成一个sql命令，然后发布CommandEvent到Disruptor队列里面。整体的数据流如下：
 *
 *                                                                       -------- CommandEventProducer
 *                                                                       -
 *                                                                       -------- CommandEventProducer
 * -----------         ---------------          ------- Processor  ----- -
 * -         -         -             -          -                        -------- CommandEventProducer
 * -  Comand - ------  -  Dispatcher -  ---------                        -
 * -         -         -             -          -                        -------- CommandEventProducer
 * -----------         ---------------          - ------ Processor
 *
 *
 * --------- CommandEventHandler ----- 分组缓存 CommandBuffer -----分组执行 CommandExecutor
 */
public class Command implements Serializable {
    private static final long serialVersionUID = -2463630580877588711L;
    /**
     * 命令的id
     */
    protected final String id;

    protected final String requestId;

    public Command(String requestId) {
        this(UUID.randomUUID().toString().replace("-",""),requestId);
    }


    public Command(String id, String requestId) {
        this.id = id;
        this.requestId = requestId;
    }

    public String getId() {
        return id;
    }

    public String getRequestId() {
        return requestId;
    }
}
