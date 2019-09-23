package servlet;

import Extannotartion.ExtController;
import Extannotartion.ExtRequestMapping;
import Extannotartion.ExtRequestParam;
import classPathXmlApplication.ExtAnnoClassPathXmlApplicationContext;
import com.sun.deploy.net.HttpResponse;
import service.FirstController;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    HashMap<String, Object> handlerMap = new HashMap<>();
    HashMap<String, Object> pathObjectMap = new HashMap<>();
//初始化bean
    @Override
    public void init() throws ServletException {
        try {
            ExtAnnoClassPathXmlApplicationContext classPathXmlApplicationContext
                    = new ExtAnnoClassPathXmlApplicationContext("service");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            urlMapping();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    //
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String context = req.getContextPath();
        String path = uri.replace(context,"");
        Method method = (Method) handlerMap.get(path);
        Object controller = pathObjectMap.get("/" + path.split("/")[1]);
   //     String st = path.split("/")[1];
        Object[] hand = hand(req, resp, method);
        try {
            method.invoke(controller, hand);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    //解析并操作含有ExtRequestMapping注解的控制器
    public void urlMapping() throws IllegalAccessException, InstantiationException {
        for(Map.Entry<String, Object> entry : ExtAnnoClassPathXmlApplicationContext.beans.entrySet()) {
            Object instance = entry.getValue();
            Class<?> aClass = instance.getClass();
            if(aClass.isAnnotationPresent(ExtController.class)) {
                ExtRequestMapping requestMapping = aClass.getAnnotation(ExtRequestMapping.class);
                String classPath = requestMapping.value();
                pathObjectMap.put(classPath, instance);
                Method[] methods = aClass.getMethods();
                for (Method method : methods) {
                    ExtRequestMapping methodsMapping = method.getAnnotation(ExtRequestMapping.class);
                    if(methodsMapping != null) {
                        String methodPath = methodsMapping.value();
                        handlerMap.put(classPath+methodPath, method);
                    }

                }

            }else{
                continue;
            }
        }
    }

    //解析传进来的参数
    private Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method) {
        Class<?>[] paramClazzs = method.getParameterTypes();
        Object[] args = new Object[paramClazzs.length];
        int args_i = 0;
        int index = 0;
        for (Class<?> paramClazz : paramClazzs) {
            if(ServletRequest.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = request;
            }
            if(ServletResponse.class.isAssignableFrom(paramClazz)) {
                args[args_i++] = response;
            }
            Annotation[] paramAns = method.getParameterAnnotations()[index];
            if(paramAns.length > 0) {
                for(Annotation annotation : paramAns) {
                    if(ExtRequestParam.class.isAssignableFrom(annotation.getClass())) {
                        ExtRequestParam extRequestParam = (ExtRequestParam)annotation;
                        args[args_i++] = request.getParameter(extRequestParam.value());
                    }
                }
            }
            index++;
        }
        return args;
    }
}
