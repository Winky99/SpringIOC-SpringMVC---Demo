import Extannotartion.ExtAutowired;
import Extannotartion.ExtController;
import Extannotartion.ExtRequestMapping;
import Extannotartion.ExtRequestParam;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ExtController
@ExtRequestMapping("/index")
public class FirstController {

    @ExtAutowired
    private UserService userServiceImpl;

    @ExtRequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,
                    @ExtRequestParam("name") String name,
                    @ExtRequestParam("age") String age) {

        try {
            PrintWriter pw = response.getWriter();
            String result = userServiceImpl.printMess(name, age);
            pw.write("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
