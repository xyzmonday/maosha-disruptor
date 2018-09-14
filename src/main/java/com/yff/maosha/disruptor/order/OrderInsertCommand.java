package com.yff.maosha.disruptor.order;

import com.yff.maosha.command.Command;

/**
 * 创建订单命令
 */
public class OrderInsertCommand extends Command {

    private static final long serialVersionUID = -1844388054958673686L;
    private final Long itemId;
    private final String userId;

    /**
     * @param requestId Command来源的requestId
     * @param itemId    商品ID
     * @param userId    用户ID
     */
    public OrderInsertCommand(String requestId, Long itemId, String userId) {
        super(requestId);
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getUserId() {
        return userId;
    }

}
