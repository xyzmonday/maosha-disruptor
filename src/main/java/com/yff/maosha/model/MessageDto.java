package com.yff.maosha.model;


import com.yff.maosha.utils.StrongUuidGenerator;

import java.io.Serializable;

public abstract class MessageDto implements Serializable {

    private static final long serialVersionUID = 9003442515985424079L;
    /**
     * 应该保证全局唯一, 用uuid
     */
    protected final String id;

    public MessageDto() {
        this.id = StrongUuidGenerator.getNextId();
    }

    public String getId() {
        return id;
    }

}
