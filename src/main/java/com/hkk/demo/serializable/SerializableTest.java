package com.hkk.demo.serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

/**
 * @author kang
 * @date 2022/5/31
 */

public class SerializableTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SerializableDemo demo = new SerializableDemo();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(demo);
        out.flush();
        out.close();
        os.close();
        try (ObjectInputStream in = new ObjectInputStream(
            new ByteArrayInputStream(os.toByteArray()))) {
            in.readObject();
        }
    }


    private static class SerializableDemo implements Serializable {

        SerializableDemo() {
            LoggerFactory.getLogger(SerializableDemo.class).info("constructor");
        }
    }

}
