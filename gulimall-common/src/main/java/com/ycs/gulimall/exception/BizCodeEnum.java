package com.ycs.gulimall.exception;

/**
 * 错误状态码枚举
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 *  10: 通用
 *      001：参数格式校验
 *      002：短信验证码频率太高
 *  11: 商品
 *  12: 订单
 *  13: 购物车
 *  14: 库存
 *  15: 用户
 *  16: 物流
 **/
public enum BizCodeEnum {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    GATEWAY_LIMIT_REQUEST(10001,"网关检测到请求流量过大，请稍后再试"),
    VALID_EXCEPTION(10002,"参数格式校验失败"),
    TO_MANY_REQUEST(10003,"请求流量过大，请稍后再试"),
    SMS_CODE_EXCEPTION(10004,"验证码获取频率太高，请稍后再试"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常"),
    ORDER_TOKEN_EXPIRED(12001,"订单令牌信息过期，请刷新页面再次提交"),
    ORDER_PRODUCT_PRICE_CHANGED(12002,"订单商品价格发生变化，请确认后再次提交"),
    NO_STOCK_EXCEPTION(14001,"商品库存不足"),
    USER_EXIST_EXCEPTION(15001,"存在相同的用户"),
    PHONE_EXIST_EXCEPTION(15002,"存在相同的手机号"),
    LOGIN_ACCT_PASSWORD_EXCEPTION(15003,"账号或密码错误"),
    ;

    private Integer code;
    private String message;

    BizCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
