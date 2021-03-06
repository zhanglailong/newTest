
package org.jeecg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients(basePackages = {"org.jeecg"})
@SpringBootApplication(scanBasePackages = "org.jeecg")
public class JeecgCloudTEPMServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JeecgCloudTEPMServiceApplication.class, args);
    }
}
