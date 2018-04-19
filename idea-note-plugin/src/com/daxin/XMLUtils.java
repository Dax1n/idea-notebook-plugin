package com.daxin;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuguangxin on 2018/4/18.
 */
public class XMLUtils {

    private static String dataFileNameInIdea = "codenote.xml";

    public static void main(String[] args) throws Exception {
        XMLUtils.WriteXml("", "Key: ", "数据库");
    }


    public static Map<String, String> readXmlToMap(String filePath) throws Exception {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();
        File file = new File(filePath + dataFileNameInIdea);
        if (!file.exists()) {
            return null;
        }
        FileInputStream in = new FileInputStream(file);
        Document doc = reader.read(in);
        doc.setXMLEncoding("UTF-8");
        Element root = doc.getRootElement();
        List elementList = root.elements();
        for (int i = 0; i < elementList.size(); i++) {
            DefaultElement de = (DefaultElement) (elementList.get(i));

            String keyContent = de.element("key").getText().trim();
            String valueContent = de.element("value").getText().trim();

            map.put(keyContent, valueContent);
        }
        return map;
    }


    public static boolean existKey(String key, String filePath) throws Exception {

        return readXmlToMap(filePath).get(key) == null ? false : true;
    }

    public static void CreateXml(String filePath) throws Exception {
        File file = new File(filePath + dataFileNameInIdea);
        if (file.exists()) {
            return;
        }
        Document doc = DocumentHelper.createDocument();
        doc.setXMLEncoding("UTF-8");
        doc.addElement("Records");

        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = new FileOutputStream(new File(filePath + dataFileNameInIdea));
        XMLWriter w = new XMLWriter(out,format);
        w.write(doc);
        w.flush();
        w.close();

    }

    public static void WriteXml(String filePath, String key, String value) throws Exception {

        File file = new File(filePath + dataFileNameInIdea);
        if (!file.exists()) {
            CreateXml(filePath);
        }

        if (existKey(key, filePath)) {
            updateNode(filePath, key, value);
            return;
        }

        SAXReader reader = new SAXReader();
        FileInputStream in = new FileInputStream(new File(filePath + dataFileNameInIdea));
        Document doc = reader.read(in);
        doc.setXMLEncoding("UTF-8");
        Element e = doc.getRootElement();
        Element record = e.addElement("Record");
        Element keyE = record.addElement("key");
        keyE.setText(key);
        Element valueE = record.addElement("value");
        valueE.setText(new String(value.getBytes("UTF-8"), "UTF-8"));



        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = new FileOutputStream(new File(filePath + dataFileNameInIdea));
        XMLWriter w = new XMLWriter(out,format);
        w.write(doc);
        w.flush();
        w.close();


    }


    public static void removeNodeByKeyValue(String filePath, String keyValue) throws Exception {
        SAXReader reader = new SAXReader();
        FileInputStream in = new FileInputStream(new File(filePath +dataFileNameInIdea));
        Document doc = reader.read(in);
        doc.setXMLEncoding("UTF-8");
        List records = doc.getRootElement().elements();

        for (int i = 0; i < records.size(); i++) {
            DefaultElement de = (DefaultElement) (records.get(i));

            String keyContent = de.element("key").getText().trim();
            if (keyContent.equals(keyValue)) {
                records.remove(de);
            }
        }

        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = new FileOutputStream(new File(filePath + dataFileNameInIdea));
        XMLWriter w = new XMLWriter(out,format);
        w.write(doc);
        w.flush();
        w.close();



    }


    public static void updateNode(String filePath, String keyValue, String newValue) throws Exception {
        SAXReader reader = new SAXReader();
        FileInputStream in = new FileInputStream(new File(filePath + "codenote.xml"));
        Document doc = reader.read(in);
        doc.setXMLEncoding("UTF-8");
        List records = doc.getRootElement().elements();

        for (int i = 0; i < records.size(); i++) {
            DefaultElement de = (DefaultElement) (records.get(i));

            String keyContent = de.element("key").getText().trim();
            if (keyContent.equals(keyValue)) {
                de.element("value").setText(newValue);
            }
        }


        OutputFormat format = OutputFormat.createPrettyPrint();
        OutputStream out = new FileOutputStream(new File(filePath + "codenote.xml"));
        XMLWriter w = new XMLWriter(out,format);
        w.write(doc);
        w.flush();
        w.close();
    }


}
