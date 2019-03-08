package com.chasel.passbook.merchants.controller;

import com.alibaba.fastjson.JSON;
import com.chasel.passbook.merchants.service.IMerchantsService;
import com.chasel.passbook.merchants.vo.CreateMerchantsRequest;
import com.chasel.passbook.merchants.vo.PassTemplate;
import com.chasel.passbook.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h1>商户服务Controller</h1>
 *
 * @author XieLongzhen
 * @date 2019/3/6 9:55
 */
@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantsController {

    private final IMerchantsService merchantsService;

    @Autowired
    public MerchantsController(IMerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    @PostMapping("/create")
    public Response createMerchants(@RequestBody CreateMerchantsRequest request) {
        log.info("CreateMerchants: {}", JSON.toJSONString(request));
        return merchantsService.createMerchants(request);
    }

    @GetMapping("/{id}")
    public Response buildMerchantsInfo(@PathVariable Integer id) {
        log.info("BuildMerchantsInfo: {}", id);
        return merchantsService.buildMerchantsInfoById(id);
    }

    /**
     * DropPassTemplates: {"background":1,"desc":"详情: 慕课 second",
     * "end":1528202373202,"hasToken":false,"id":9,"limit":1000,
     * "start":1527338373202,"summary":"简介: 慕课","title":"title: 慕课"}
     */
    @PostMapping("/drop")
    public Response dropPassTemplate(@RequestBody PassTemplate passTemplate) {
        log.info("DropPassTemplate: {}", passTemplate);
        return merchantsService.dropPassTemplate(passTemplate);
    }


}
