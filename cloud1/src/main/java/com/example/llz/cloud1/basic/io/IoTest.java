package com.example.llz.cloud1.basic.io;

import com.example.llz.cloud1.entity.Person;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import java.io.File;
import java.io.IOException;

public class IoTest {
    /**
     * 读取大json
     * @param path
     * @throws IOException
     */
    private void readBigJson(String path) throws IOException {
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createParser(new File(path));
        while (jp.nextToken() != null) {
            if (jp.currentToken() == JsonToken.FIELD_NAME){
                TreeNode treeNode = jp.readValueAsTree();
                System.out.println(treeNode);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        IoTest ioTest = new IoTest();
        ioTest.readBigJson("C:\\Users\\llz\\Documents\\southgis\\云南业务提取.json");
    }
}
