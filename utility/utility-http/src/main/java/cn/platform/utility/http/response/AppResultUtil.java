package cn.platform.utility.http.response;

import cn.platform.utility.http.response.entity.BaseResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 返回工具类
 * @author meng.meng
 * @date 2018/3/23 下午3:25
 */
public enum AppResultUtil {

    //枚举常量，引用自身
    ME;

    /**
     * 本地化构造
     */
    AppResultUtil() {}

    /**
     * 返回成功结果
     * @param entity 返回结果
     * @return ResponseEntity
     */
    public ResponseEntity<BaseResponseEntity> success(BaseResponseEntity entity) {
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    /**
     * 返回异常结果
     * @param entity 返回结果
     * @return ResponseEntity
     */
    public ResponseEntity<BaseResponseEntity> exception(BaseResponseEntity entity) {
        return new ResponseEntity<>(entity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 返回错误结果
     * @param entity 返回结果
     * @return ResponseEntity
     */
    public ResponseEntity<BaseResponseEntity> fail(BaseResponseEntity entity) {
        return new ResponseEntity<>(entity, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * 返回参数／请求异常结果
     * @param entity 返回结果
     * @return ResponseEntity
     */
    public ResponseEntity invalidParam(BaseResponseEntity entity) {
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
