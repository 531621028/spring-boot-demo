package com.hkk.demo.kafka;

import com.hkk.demo.utils.JsonUtil;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * @author kang
 * @date 2022/6/6
 */
@Slf4j
public class ConsumerDemo {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "124.223.51.141:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, Boolean.FALSE);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-demo");
        // 创建一个消费者
        ConsumerRecords<String, String> records;
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            do {
                consumer.subscribe(Collections.singletonList("test"));
                records = consumer.poll(Duration.ofSeconds(10));
                records.forEach(
                    consumerRecord -> log.info("consumer receive message content is {}",
                        consumerRecord.value()));
                consumer.commitAsync(
                    (offsets, exception) -> log.info("offset commit callback {}",
                        JsonUtil.toJsonStringOrEmpty(offsets)));
            } while (!records.isEmpty());
        }

    }
}
