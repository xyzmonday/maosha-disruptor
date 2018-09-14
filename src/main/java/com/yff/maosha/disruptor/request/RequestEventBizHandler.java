package com.yff.maosha.disruptor.request;

import com.lmax.disruptor.EventHandler;
import com.yff.maosha.disruptor.item.ItemAmountUpdateCommand;
import com.yff.maosha.disruptor.order.OrderInsertCommand;
import com.yff.maosha.entity.Item;
import com.yff.maosha.model.RequestDto;
import com.yff.maosha.model.ResponseDto;
import com.yff.maosha.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求事件业务消费者
 */
public class RequestEventBizHandler implements EventHandler<RequestEvent> {

    private final static Logger logger = LoggerFactory.getLogger(RequestEventBizHandler.class);

    private final ItemService itemService;

    public RequestEventBizHandler(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 消费秒杀请求事件，生成库存更新和创建订单两个命令
     *
     * @param requestEvent
     * @param l
     * @param b
     * @throws Exception
     */
    @Override
    public void onEvent(RequestEvent requestEvent, long l, boolean b) throws Exception {
        logger.info("BizHandler" + Thread.currentThread().getName());
        RequestDto request = requestEvent.getRequest();
        //获取需要秒杀的商品
        Item item = itemService.get(request.getItemId());
        ResponseDto response = new ResponseDto(request.getId());
        if (item == null) {
            response.setSuccess(false);
            response.setErrorMessage("内存中还未缓存商品数据");
        } else if (item.decreaseAmount()) { //扣减库存成功
            ItemAmountUpdateCommand itemAmountUpdateCommand = new ItemAmountUpdateCommand(request.getId(), item.getId(), item.getAmount());
            OrderInsertCommand orderInsertCommand = new OrderInsertCommand(request.getId(), item.getId(), request.getUserId());
            requestEvent.addCommand(itemAmountUpdateCommand);
            requestEvent.addCommand(orderInsertCommand);
        } else {
            response.setSuccess(false);
            response.setErrorMessage("库存不足");
        }
        requestEvent.setResponse(response);
    }
}
