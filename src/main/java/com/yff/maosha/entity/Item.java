package com.yff.maosha.entity;

public class Item {
    private Long id;

    private String name;

    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    /**
     * 减库存，如果库存不足，则扣减失败
     *
     * @return
     */
    public boolean decreaseAmount() {

        if (!hasRemaining()) {
            return false;
        }
        amount--;
        return true;

    }

    /**
     * 是否还有库存
     *
     * @return
     */
    public boolean hasRemaining() {
        return amount > 0;
    }

}