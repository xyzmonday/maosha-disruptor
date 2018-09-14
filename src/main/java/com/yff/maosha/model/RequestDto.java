package com.yff.maosha.model;

public class RequestDto extends MessageDto {
    /**
     * 秒杀商品id
     */
    private Long itemId;
    /**
     * 用户id
     */
    private String userId;

    public RequestDto() {
        super();
    }

    public RequestDto(Long itemId, String userId) {
        super();
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestDto that = (RequestDto) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "itemId=" + itemId +
                ", userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
