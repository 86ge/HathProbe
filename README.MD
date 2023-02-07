## 爬取e-hentai的hentaiathome数据
![数据](https://s1.yesimg.com/2022/059808a8502e8.png)
### 使用方法
1. 导入hath.sql到数据库
2. 为application配置文件配置如下字段
    - hath.ipb_pass_hash = "e-hentai cookie对应字段"
    - hath.ipb_member_id= "e-hentai cookie对应字段"
3. 运行
### Api
- `/getClients` 获取该账号下所有客户端
- `/getMessage` 获取指定客户端信息
    - clientId -客户端id
    - intervalTime -获取n分钟内数据
        - 可选 `10|30|60|360|720|1440`
### 预览
![效果图](https://s1.yesimg.com/2022/059808a8502e8.png)
