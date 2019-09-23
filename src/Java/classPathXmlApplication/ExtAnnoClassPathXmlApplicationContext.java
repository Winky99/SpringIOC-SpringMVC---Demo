package classPathXmlApplication;

import Extannotartion.ExtAutowired;
import Extannotartion.ExtController;
import Extannotartion.ExtService;
import util.ClassUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ExtAnnoClassPathXmlApplicationContext {

    private String packageName;
    //bean容器
    public static ConcurrentHashMap<String, Object> beans = null;

    public ExtAnnoClassPathXmlApplicationContext(String packageName) throws Exception {
        this.packageName = packageName;
        beans = new ConcurrentHashMap<String, Object>();
        initBeans();
        initEntryField();
    }

    public void test() {
        for(String s : beans.keySet()) {
            System.out.println("---------------"+s);
        }
    }

    //获取bean
    public Object getBean(String beanId) throws Exception {
        if(beanId == null || beanId.length()==0) {
            throw new Exception("beanID不能为空");
        }
        Object object = beans.get(beanId);
        if( object == null) {
            throw new Exception("没你要找的bean呀");
        }else {
            return object;
        }
    }
    //反射初始化对像
    public Object newInstance(Class<?> classInfo) throws IllegalAccessException, InstantiationException {
        return classInfo.newInstance();
    }

    //初始化对像
    public void initBeans() throws Exception {
        // 1. 使用Java反射扫包，获取当前包下所有的类
        Set<Class<?>> classes = ClassUtil.getClasses(packageName);
        
        // 2. 判断类上是否有注入bean的注解
        ConcurrentHashMap<String, Object> classExtIsAnnnotation = findClassExtIsAnnnotation(classes);
        if(classExtIsAnnnotation == null || classExtIsAnnnotation.isEmpty()) {
            throw new Exception("该包下没有任何类加上注解");
        }

    }

    //判断类上是否有注入bean的注解
    public ConcurrentHashMap<String, Object> findClassExtIsAnnnotation(Set<Class<?>> classes) throws InstantiationException, IllegalAccessException {
        for (Class<?> classInfo : classes) {
            //判断类上是否有注解
            ExtService annotation = classInfo.getAnnotation(ExtService.class);
            if(annotation != null) {
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                beans.put(beanId, classInfo.newInstance());
            }
            ExtController extController = classInfo.getAnnotation(ExtController.class);
            if(extController != null) {
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                beans.put(beanId, classInfo.newInstance());
            }
        }
        return beans;
    }

    public void atrriAssign(Object object) throws Exception {
        //使用反射获得当前类所有的属性
        Class<?> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();
        //判断类属性是是否有注解
        for(Field field : declaredFields) {
            ExtAutowired extAutowired = field.getAnnotation(ExtAutowired.class);
            if(extAutowired != null) {
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if(bean != null) {
                    field.setAccessible(true);
                    field.set(object, bean);
                }
            }
        }
    }

    public void initEntryField() throws Exception {
        //遍历容器里面的bean
        for(Map.Entry<String, Object> entry : beans.entrySet()) {
            //判断是否有注解
            Object value = entry.getValue();
            atrriAssign(value);
        }
    }

    //类名首字母转换为小写
    public static String toLowerCaseFirstOne(String s) {
        if(Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase((s.charAt(0))))
                    .append(s.substring(1)).toString();
        }
    }
}
