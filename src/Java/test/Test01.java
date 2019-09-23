package test;

import classPathXmlApplication.ExtAnnoClassPathXmlApplicationContext;
import service.FirstController;
import service.UserService;

public class Test01 {

    public static void main(String[] args) throws Exception {
        ExtAnnoClassPathXmlApplicationContext classPathXmlApplicationContext
                = new ExtAnnoClassPathXmlApplicationContext("service");
//        UserService userService = (UserServiceImpl) classPathXmlApplicationContext.getBean("userServiceImpl");
//        UserService userService1 = (UserServiceImpl) classPathXmlApplicationContext.getBean("userServiceImpl");
//        System.out.println(userService==userService1);
    //    UserService userServiceImpl = (UserService) classPathXmlApplicationContext.getBean("userServiceImpl");
//        FirstController firstController = (FirstController) classPathXmlApplicationContext.getBean("firstController");
//        System.out.println(firstController.test01());
     //   userServiceImpl.add();
   //     System.out.println(userServiceImpl.printMess("zs", "22"));

    }
}
