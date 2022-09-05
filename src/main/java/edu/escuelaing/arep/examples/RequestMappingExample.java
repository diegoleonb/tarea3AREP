package edu.escuelaing.arep;

import edu.escuelaing.arep.RequestMapping;

public class RequestMappingExample {

    @RequestMapping("/")
    public static String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/holaTest1")
    public static void Test1() {
    }

    public static void Test2() throws Exception {
        throw (new Exception("Error Test2"));
    }

    @RequestMapping("/holaTest3")
    public static void Test3() {
    }

    @RequestMapping("/holaTest4")
    public static void Test4() throws Exception {
        throw (new Exception("Error test4"));
    }

    public static void Test5() {
    }

    @RequestMapping("/holaTest6")
    public static void Test6() throws Exception {
        throw (new Exception("Error Test6"));
    }
}
