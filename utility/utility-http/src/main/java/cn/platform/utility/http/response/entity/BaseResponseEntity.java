package cn.platform.utility.http.response.entity;

//import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 基础返回实体，所有返回返回数据均需继承自该实体
 * @author meng.meng
 * @date 2018/3/23 下午3:14
 */
public class BaseResponseEntity implements Serializable {

    private static final long serialVersionUID = -5100207444328622809L;
    //private int code = HttpStatus.OK.value();
    private String message = "成功！";

    protected BaseResponseEntity() {}

    protected BaseResponseEntity(int code, String message) {
        //this.code = code;
        this.message = message;
    }

    /*protected void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }*/

    protected void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
