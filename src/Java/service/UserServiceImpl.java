package service;

import Extannotartion.ExtAutowired;
import Extannotartion.ExtService;

@ExtService
public class UserServiceImpl implements UserService {

    @ExtAutowired
    private OrderService orderServiceImpl;
    public UserServiceImpl() {
        System.out.println("UserServiceImpl构造函数执行。。。。。。。。。。");
    }
    public void add() {
        orderServiceImpl.add();
        System.out.println("user的Add方法执行");
    }
    public String printString() {
        return "ok";
    }
    public String printMess(String name, String address) {
        String mess = "Name = " + name +"    " + " Address = " + address;
        return mess;

    }


}
