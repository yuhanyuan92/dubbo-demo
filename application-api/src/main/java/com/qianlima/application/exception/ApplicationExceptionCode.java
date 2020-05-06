package com.qianlima.application.exception;

/**
 * @author ZHangYJ
 */
public enum ApplicationExceptionCode {

    /**
     * 没有项目专耵权限
     */
    PROJECT_ATTENTION_NO_AUTH_ERROR(300010, "您没有申请权限！请升级为高级会员或高级以上级别会员。"),

    /**
     * 专盯条数已用尽
     */
    PROJECT_ATTENTION_DEPLETE_ERROR(300011, "您的专盯条数已用尽，请联系您的专属客服！"),

    /**
     * 项目不存在
     */
    PROJECT_NOT_EXIST_ERROR(300012, "该项目不存在或已删除。"),

    /**
     * 专耵失败！
     */
    PROJECT_ATTENTION_ERROR(300013, "项目专耵失败，请重试"),

    /**
     * 专耵失败！
     */
    PROJECT_ATTENTION_ALREADY_CANCEL_ERROR(300014, "项目专耵失败，请重试"),

    /**
     * 取消专耵失败！
     */
    PROJECT_ATTENTION_CANCEL_ERROR(300020, "取消专耵失败，请重试"),


    /**
     * 没有项目匹配权限
     */
    PROJECT_MATCH_NO_AUTH_ERROR(300021, "您当前没有此功能权限，请升级成VIP会员，详情请咨询客服"),

    /**
     * 没有找到项目匹配规则记录
     */
    PROJECT_MATCH_REGULATION_NOT_FOUND_ERROR(300022, "您当前暂未设置项目匹配规则"),

    /**
     * 未找到项目匹配记录
     */
    PROJECT_MATCH_RECORD_NOT_FOUND_ERROR(300023, "您查的项目暂时没有数据"),

    /**
     * 项目不匹配当前用户
     */
    PROJECT_NOT_MATCH_FEEDBACK_ERROR(300024, "项目不匹配当前用户");

    ApplicationExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
