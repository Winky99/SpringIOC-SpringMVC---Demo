package service;

import Extannotartion.ExtService;


@ExtService
public class OrderServiceImpl implements OrderService {

    public OrderServiceImpl() {
        System.out.println("order构造函数执行。。。。。。。。。");
    }

    public void add() {
        System.out.println("order的Add方法执行。。。。。。。。");
    }
}
