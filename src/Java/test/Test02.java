package test;

import classPathXmlApplication.XmlClassPathXmlApplication;

public class Test02 {
    public static void main(String[] args) throws Exception {
        XmlClassPathXmlApplication xmlClassPathXmlApplication
        = new XmlClassPathXmlApplication("spring.xml");
        Object userService = xmlClassPathXmlApplication.getBean("orderServiceImpl");
        System.out.println(userService);
    }
}
