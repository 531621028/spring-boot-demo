package lark;

import com.lark.oapi.Client;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kang
 * @date 2023/8/2
 */
@Configuration
public class LarkConfig {

    @Bean
    public Client larkClient() {
        return Client.newBuilder("appId", "appSecret")
            .marketplaceApp() // 设置 app 类型为商店应用
            .openBaseUrl("https://open.f.mioffice.cn") // 设置域名，默认为飞书
            .requestTimeout(60, TimeUnit.SECONDS) // 设置httpclient 超时时间，默认永不超时
            .build();
    }

}
