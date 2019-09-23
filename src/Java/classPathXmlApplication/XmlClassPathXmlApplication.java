package classPathXmlApplication;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlClassPathXmlApplication {
    //xml路径地址
    private String xmlPath;

    public XmlClassPathXmlApplication(String xmlPath) {
        this.xmlPath = xmlPath;
    }
    public List<Element> readXML() throws Exception {
        //解析xml文件
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(getResourceAsStream(xmlPath));
        //读取根节点
        Element rootElement = read.getRootElement();
        //获取根节点下所有的子节点
        List<Element> elements = rootElement.elements();
        return elements;
    }

    public InputStream getResourceAsStream(String xmlPath) throws Exception {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        if(resourceAsStream==null) {
            throw new Exception("根目录下么有找到该配置文件");
        }
        return resourceAsStream;
    }
    //根据id获取bean对象
    public Object getBean(String beanId) throws Exception {
        if(beanId==null||beanId.length()==0) {
            throw new Exception("beanId不能为空");
        }

        //解析xml文件，获得所有bean节点的信息
        List<Element> elements = readXML();
        if(elements == null ||  elements.isEmpty()) {
            throw new Exception("配置文件中没有bean的配置信息");
        }
        String byElementId = findByElementId(elements, beanId);
        if(byElementId == null || byElementId.length() == 0) {
            throw new Exception("该bean对象没有配置class地址");
        }
        return newInstance(byElementId);
//        for(Element element : elements) {
//            String id = element.attributeValue("id");
//            if(id == null || id.length() == 0) {
//                continue;
//            }
//            if(id.equals(beanId)) {
//                String idClass = element.attributeValue("class");
//                return newInstance(idClass);
//            }
//        }

    }
    //使用参数id查找配置文件中相匹配的class信息
    public String findByElementId(List<Element> elements, String beanId) {
        for(Element element : elements) {
            String id = element.attributeValue("id");
            if(id == null || id.length() == 0) {
                continue;
            }
            if(id.equals(beanId)) {
                String idClass = element.attributeValue("class");
                return idClass;
            }
        }
        return null;
    }

    //初始化对象
    public Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> aClass = Class.forName(className);
        return aClass.newInstance();
    }
}
