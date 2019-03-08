package com.chasel.passbook.merchants.service;

import com.chasel.passbook.merchants.vo.CreateMerchantsRequest;
import com.chasel.passbook.merchants.vo.PassTemplate;
import com.chasel.passbook.merchants.vo.Response;

/**
 * <h1>对商户服务接口定义</h1>
 *
 * @author XieLongzhen
 * @date 2019/3/5 9:56
 */
public interface IMerchantsService {
    /**
     * <h2>创建商户服务</h2>
     * @param request {@link CreateMerchantsRequest} 创建商户请求
     * @return {@link Response}
     * */
    Response createMerchants(CreateMerchantsRequest request);

    /**
     * <h2>根据 id 构造商户信息</h2>
     * @param id 商户 id
     * @return {@link Response}
     * */
    Response buildMerchantsInfoById(Integer id);

    /**
     * <h2>投放优惠券</h2>
     * @param template {@link PassTemplate} 优惠券对象
     * @return {@link Response}
     * */
    Response dropPassTemplate(PassTemplate template);
}
