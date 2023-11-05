Gurney.java

class Gurney {

    public static void main(String[] args) {
        System.out.println("Big big gurn);
    }
    
}
----------
javac Gurney.java
Gurney.java:1: error: unclosed string literal
class Gurney {    public static void main(String[] args) {        System.out.println("Big big gurn);    }    }
                                                                                     ^
Gurney.java:1: error: reached end of file while parsing
class Gurney {    public static void main(String[] args) {        System.out.println("Big big gurn);    }    }
                                                                                                              ^
2 errors
java Gurney
Error: Could not find or load main class Gurney
Caused by: java.lang.ClassNotFoundException: Gurney

Ran at 08:11 PM EST