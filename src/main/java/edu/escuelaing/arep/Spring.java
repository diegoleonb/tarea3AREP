package edu.escuelaing.arep;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import edu.escuelaing.arep.annotations.RequestMapping;

public class Spring {
    private static Map<String, Method> services = new HashMap<String, Method>();
    
    public static void run() throws Exception{
        for (Method method : Class.forName("edu.escuelaing.arep.examples.RequestMappingExample").getMethods()) {
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

    /**
     * Devuelve el metodo de nuestro hashmap
     * @param uri key
     * @return value
     */
    public static String getMethod(String uri) throws Exception{
        return (String)services.get(uri).invoke(null);
    }

}
