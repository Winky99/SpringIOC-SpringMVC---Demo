package service;

import Extannotartion.ExtAutowired;
import Extannotartion.ExtController;
import Extannotartion.ExtRequestMapping;
import Extannotartion.ExtRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ExtController
@ExtRequestMapping("/index")
public class FirstController {

    public FirstController() {
        System.out.println("controller构造函数执行。。。。。。。。。");
    }

    @ExtAutowired
    private UserService userServiceImpl;
    @ExtRequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,
                    @ExtRequestParam("name") String name,
                    @ExtRequestParam("address") String address) {


        String result = userServiceImpl.printMess(name, address);
        try {
            PrintWriter pw = response.getWriter();
            pw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String test01() {

        return userServiceImpl.printString();
    }
}
