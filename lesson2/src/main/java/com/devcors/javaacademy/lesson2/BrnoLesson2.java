package com.devcors.javaacademy.lesson2;

public class BrnoLesson2 {

    /**
     * Fill main method with your calls of {@link Conditions} and {@link Loops} methods.
     *
     * <p>Output results of methods calls using System.out.println with reasonable message.
     */
    public static void main(String[] args) {
        DevCorsListImpl<String> neco = new DevCorsListImpl<String>();

        neco.add("ahoj");
        neco.add("dalsi");
        neco.add("nejdalsi");

        for (String element : neco) {
            System.out.println(element);
        }

    }

}
