package com.cxh.jasypt;

import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;

@Slf4j
@SpringBootTest
class JasyptApplicationTests {

    @Test
    void contextLoads() {
    }

    @Value("${datasource.password}")
    private String password;

    @Value("${jasypt.encryptor.password}")
    private String secret;


    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
        System.setProperty("jasypt.encryptor.password", secret);
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        System.out.println("加密： " + stringEncryptor.encrypt(password));
        System.out.println("解密： " + stringEncryptor.decrypt("Hic0Pt6CjALcBoiWKE2kvYsHlnkhjFVTgy52csepimQ22RdioHnYy+iAKye+fZ7Y"));
    }


}
