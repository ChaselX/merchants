package com.chasel.passbook.merchants.service.impl;

import com.alibaba.fastjson.JSON;
import com.chasel.passbook.merchants.dao.MerchantsDao;
import com.chasel.passbook.merchants.constant.Constants;
import com.chasel.passbook.merchants.constant.ErrorCode;
import com.chasel.passbook.merchants.entity.Merchants;
import com.chasel.passbook.merchants.service.IMerchantsService;
import com.chasel.passbook.merchants.vo.CreateMerchantsRequest;
import com.chasel.passbook.merchants.vo.CreateMerchantsResponse;
import com.chasel.passbook.merchants.vo.PassTemplate;
import com.chasel.passbook.merchants.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author XieLongzhen
 * @date 2019/3/5 10:04
 */
@Slf4j
@Service
public class MerchantsServiceImpl implements IMerchantsService {

    private final MerchantsDao merchantsDao;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public MerchantsServiceImpl(MerchantsDao merchantsDao,
                                KafkaTemplate<String, String> kafkaTemplate) {
        this.merchantsDao = merchantsDao;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    @Transactional
    public Response createMerchants(CreateMerchantsRequest request) {
        Response response = new Response();
        CreateMerchantsResponse merchantsResponse = new CreateMerchantsResponse();

        ErrorCode errorCode = request.validate(merchantsDao);
        if (errorCode != ErrorCode.SUCCESS) {
            merchantsResponse.setId(-1);
            generateErrorResponse(errorCode, response);
        } else {
            merchantsResponse.setId(merchantsDao.save(request.toMerchants()).getId());
        }

        response.setData(merchantsResponse);
        return response;
    }

    @Override
    public Response buildMerchantsInfoById(Integer id) {
        Response response = new Response();

        Merchants merchants = merchantsDao.findById(id).orElse(null);
        if (null == merchants) {
            generateErrorResponse(ErrorCode.MERCHANTS_NOT_EXIST, response);
        }
        response.setData(merchants);

        return response;
    }

    @Override
    public Response dropPassTemplate(PassTemplate template) {
        Response response = new Response();
        ErrorCode errorCode = template.validate(merchantsDao);

        if (errorCode != ErrorCode.SUCCESS) {
            generateErrorResponse(errorCode, response);
        } else {
            String passTempalte = JSON.toJSONString(template);
            kafkaTemplate.send(
                    Constants.TEMPLATE_TOPIC,
                    Constants.TEMPLATE_TOPIC,
                    passTempalte);
            log.info("Drop PassTempalte: {}", passTempalte);
        }

        return response;
    }


    private Response generateErrorResponse(ErrorCode errorCode, Response response) {
        response.setErrorCode(errorCode.getCode());
        response.setErrorMsg(errorCode.getDesc());
        return response;
    }
}
