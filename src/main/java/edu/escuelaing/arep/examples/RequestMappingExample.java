package edu.escuelaing.arep.examples;

import edu.escuelaing.arep.annotations.RequestMapping;

public class RequestMappingExample {

    @RequestMapping("/hello")
    public static String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/helloTest1")
    public static String Test1() {
        return "Funciona el Test1";
    }

    public static void Test2() throws Exception {
        throw (new Exception("Error Test2"));
    }

    @RequestMapping("/helloTest3")
    public static String Test3() {
        return "Ha funcionado Test3. Muy bien";
    }

    @RequestMapping("/helloTest4")
    public static String Test4() throws Exception {
        return "Saludos desde Test4";
    }

    public static void Test5() {
    }

    @RequestMapping("/helloTest6")
    public static String Test6() throws Exception {
        return "Greetings from Test6!";
    }
}
