package com.hkk.demo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.hkk.demo.domain.Person;
import java.io.IOException;

public class JsonNodeTest {

    public static void main(String[] args) throws IOException {
        test1();
        test4();
    }

    public static void test1() throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;

        System.out.println("------ValueNode值节点示例------");
        // 数字节点
        JsonNode node = factory.numberNode(1);
        System.out.println(node.isNumber() + ":" + node.intValue());

        // null节点
        node = factory.nullNode();
        System.out.println(node.isNull() + ":" + node.asText());

        // missing节点
        node = factory.missingNode();
        System.out.println(node.isMissingNode() + "_" + node.asText());

        // POJONode节点
        node = factory.pojoNode(new Person("YourBatman", 18));
        System.out.println(node.isPojo() + ":" + node.asText());

        System.out.println("---" + node.isValueNode() + "---");
    }

    public static void test4() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        String jsonStr = "{\"name\":\"YourBatman\",\"age\":18,\"dog\":{\"name\":\"旺财\",\"color\":\"WHITE\"},\"hobbies\":[\"篮球\",\"football\"]}";
        JsonNode node = mapper.readTree(jsonStr);

        System.out.println(node.get("dog").get("color").asText());
//        以树路径进行遍历
        System.out.println(node.at("/dog/color").asText());
    }

}
