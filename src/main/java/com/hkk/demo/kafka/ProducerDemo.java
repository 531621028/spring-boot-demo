package com.hkk.demo.kafka;

import com.hkk.demo.utils.JsonUtil;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author kang
 * @date 2022/6/6
 */
@Slf4j
public class ProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "124.223.51.141:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip"); // 开启GZIP压缩
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 10000; i++) {
                producer.send(new ProducerRecord<>("test", "demo" + RandomUtils.nextInt(0, 100)),
                    (metadata, exception) -> log.info("complete send callback {}",
                        JsonUtil.toJsonStringOrEmpty(metadata)));
            }
        }
    }

}
