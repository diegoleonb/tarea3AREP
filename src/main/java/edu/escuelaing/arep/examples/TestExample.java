package edu.escuelaing.arep.examples;

import edu.escuelaing.arep.RequestMapping;
import edu.escuelaing.arep.Test;

public class TestExample {
    @Test
    public static void Test1() {
    }

    public static void Test2() throws Exception {
        throw (new Exception("Error en Test2"));
    }

    @Test
    public static void Test3() {
    }

    @Test
    public static void Test4() throws Exception {
        throw (new Exception("Error en Test4"));
    }

    public static void Test5() {
    }

    @RequestMapping("/hola")
    public static void Test6() throws Exception {
        throw (new Exception("Error en Test6"));
    }

}
