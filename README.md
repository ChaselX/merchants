需要启动的服务:
    hbase, mysql, kafka, redis
需要清空的数据:
    1. hbase 的四张表
    2. mysql 商户数据
    3. /tmp/token/ 下面的优惠券 token 数据
    4. redis 中的数据

1. 创建商户 -- 商户 53
    POST: 127.0.0.1:9527/merchants/create
    header: token/imooc-passbook-merchants
    {
        "name": "ChaselX",
        "logoUrl": "www.chaselx.com",
        "businessLicenseUrl": "www.chaselx.com",
        "phone": "12345678910",
        "address": "广东省广州市"
    }

2. 查看商户信息
    GET: 127.0.0.1:9527/merchants/53
    header: token/imooc-passbook-merchants

3. 投放优惠券
    POST: 127.0.0.1:9527/merchants/drop
    header: token/imooc-passbook-merchants
    {
        "background": 1,
        "desc": "chasel1优惠券",
        "end": "2019-05-30",
        "hasToken": false,
        "id": 53,
        "limit": 1000,
        "start": "2019-03-01",
        "summary": "优惠券简介",
        "title": "chasel1优惠券-1"
    }
    {
        "background": 1,
        "desc": "chasel1优惠券",
        "end": "2019-05-30",
        "hasToken": true,
        "id": 53,
        "limit": 1000,
        "start": "2019-03-01",
        "summary": "优惠券简介",
        "title": "chasel1优惠券-2"
    }
