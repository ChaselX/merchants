package com.chasel.passbook.merchants.service;

import com.alibaba.fastjson.JSON;
import com.chasel.passbook.merchants.dao.MerchantsDao;
import com.chasel.passbook.merchants.entity.Merchants;
import com.chasel.passbook.merchants.vo.CreateMerchantsRequest;
import com.chasel.passbook.merchants.vo.CreateMerchantsResponse;
import com.chasel.passbook.merchants.vo.PassTemplate;
import com.chasel.passbook.merchants.vo.Response;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author XieLongzhen
 * @date 2019/3/5 11:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantsServiceTests {

    @Autowired
    private IMerchantsService merchantsService;

    @Autowired
    private MerchantsDao merchantsDao;

    private Merchants testMerchants;

    @Before
    public void createMerchants() {
        Merchants merchants = new Merchants();
        merchants.setName("ChaselTest");
        merchants.setLogoUrl("www.chasel.com");
        merchants.setBusinessLicenseUrl("www.chasel.com");
        merchants.setPhone("12345678910");
        merchants.setAddress("GZ市");

        testMerchants = merchantsDao.save(merchants);
    }

    /**
     * {"data":{"id":17},"errorCode":0,"errorMsg":""}
     */
    @Test
    @Transactional
    public void testCreateMerchants() {
        try {
            CreateMerchantsRequest request = new CreateMerchantsRequest();
            request.setName("Chasel");
            request.setLogoUrl("www.chasel.com");
            request.setBusinessLicenseUrl("www.chasel.com");
            request.setPhone("12345678910");
            request.setAddress("GZ市");
            Response response = merchantsService.createMerchants(request);
            System.out.println(JSON.toJSONString(response));
            Assert.assertTrue(response.getErrorCode() == 0 && response.getErrorMsg().equals(""));
            CreateMerchantsResponse merchantsResponse = (CreateMerchantsResponse) response.getData();
            Assert.assertTrue(null != merchantsResponse && merchantsResponse.getId() > 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }

    /**
     * {"data":{"address":"北京市",
     * "businessLicenseUrl":"www.imooc.com","id":9,"isAudit":false,
     * "logoUrl":"www.imooc.com",
     * "name":"慕课","phone":"1234567890"},"errorCode":0,"errorMsg":""}
     */
    @Test
    public void testBuildMerchantsInfoById() {
        Merchants merchants = (Merchants) merchantsService.buildMerchantsInfoById(testMerchants.getId()).getData();
        Assert.assertNotNull(merchants);
    }

    @Test
    @Transactional
    public void testDropPassTemplate() {
        PassTemplate passTemplate = new PassTemplate();
        passTemplate.setId(testMerchants.getId());
        passTemplate.setTitle("test");
        passTemplate.setSummary("summary test");
        passTemplate.setDesc("desc test");
        passTemplate.setLimit(10000L);
        passTemplate.setHasToken(false);
        passTemplate.setBackground(2);
        passTemplate.setStart(new Date());
        passTemplate.setEnd(DateUtils.addDays(new Date(), 10));

        Response response = merchantsService.dropPassTemplate(passTemplate);
        Assert.assertTrue(response.getErrorCode() == 0 && response.getErrorMsg().equals(""));
    }

    @After
    public void deleteMerchants() {
        merchantsDao.delete(testMerchants);
    }
}
