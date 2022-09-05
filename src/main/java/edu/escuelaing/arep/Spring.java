package edu.escuelaing.arep;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Spring {
    private static Map<String, Method> services = new HashMap<String, Method>();
    
    public static void main(String[] args) throws Exception{
        for (Method method : Class.forName(args[0]).getMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                try {
                    String uri = method.getAnnotation(RequestMapping.class).value();
                    System.out.println(uri + ": " + method.invoke(null));
                    services.put(uri,method);
                } catch (Throwable ex) {
                    System.out.print(ex.getMessage());
                }
            }
        }
    }

}
