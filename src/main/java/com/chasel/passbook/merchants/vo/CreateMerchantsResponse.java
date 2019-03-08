package com.chasel.passbook.merchants.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>创建商户响应对象</h1>
 *
 * @author XieLongzhen
 * @date 2019/3/5 9:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMerchantsResponse {
    /**
     * 商户 id: 创建失败则为 -1
     */
    private Integer id;
}
