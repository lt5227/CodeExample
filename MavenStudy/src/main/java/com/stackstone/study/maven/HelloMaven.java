package com.stackstone.study.maven;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author lilei
 */
public class HelloMaven {
    public static void main(String[] args) {
        var str = "Hello Maven";
        System.out.println(str);
        InputStream inputStream = HelloMaven.class.getClassLoader().getResourceAsStream("demo.yml");
        Yaml yaml = new Yaml();
        for (Object o : yaml.loadAll(inputStream)) {
            Map<?, ?> map = (Map<?, ?>) o;
            System.out.println(map);
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
