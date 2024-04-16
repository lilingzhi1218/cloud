package com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.spring;

import com.example.llz.cloud1.SpringSourceCodes.handWriteSpring.user.ApplicationConfig;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MySpringApplicationContext {
    private Class applicationConfigClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public MySpringApplicationContext(Class<ApplicationConfig> applicationConfigClass) {
        this.applicationConfigClass = applicationConfigClass;

        //扫描
        if (applicationConfigClass.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScan = applicationConfigClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();
            path = path.replace(".", "/");//相对路径

            //获取类加载器
            ClassLoader classLoader = MySpringApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);//绝对路径


            File file = new File(resource.getFile());
            if (file.isDirectory()){
                for (File f : file.listFiles()) {
                    //bean 文件绝对路径
                    String fileName = f.getAbsolutePath();

                    //截取beanName
                    fileName = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    fileName = fileName.replace("/", ".");
                    //反射的方式判断这个类是否有@Component 注解
                    try {
                        Class<?> clazz = classLoader.loadClass(fileName);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            Component component = clazz.getAnnotation(Component.class);

                            //收集 bean 后置处理器
                            if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) clazz.newInstance();
                                beanPostProcessorList.add(beanPostProcessor);
                            }

                            //生成 BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition();
                            if (clazz.isAnnotationPresent(Scope.class)){
                                Scope scope = clazz.getAnnotation(Scope.class);
                                String scopeType = scope.value();
                                beanDefinition.setScope(scopeType);
                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinition.setType(clazz);

                            //放到 BeanDefinitionMap
                            String beanName = component.value();
                            if (beanName.equals("")) {
                                beanName = Introspector.decapitalize(clazz.getSimpleName());
                            }
                                this.beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        //实例化单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")){
                singletonObjects.put(beanName, createBean(beanName, beanDefinition));
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();

            //自动注入
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    field.set(instance, getBean(field.getName()));
                }
            }

            //是否实现了 Aware
            if (instance instanceof BeanNameAware){
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            //bean后置处理器-初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.beanProcessBeforeInitialization(beanName, instance);
            }

            //是否实现初始化接口
            if (instance instanceof InitializingBean){
                ((InitializingBean) instance).afterPropertiesSet();
            }

            //bean后置处理器-初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.beanProcessAfterInitialization(beanName, instance);
            }


            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new Exception("找不到beanDefinition:" + beanName);
        }

        Object bean;

        if ("singleton".equals(beanDefinition.getScope())){
            bean = singletonObjects.get(beanName);
            if (bean == null){
                bean = createBean(beanName, beanDefinition);
            }
        } else {
            bean = createBean(beanName, beanDefinition);
        }
        return bean;
    }
}
