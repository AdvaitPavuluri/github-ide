main.java

public class Main {
  System.out.println('PENIS LICKER')
  int pen = 69
  int is = 420
  
  public static void main(String[] args) {
  System.out.println(69*420);
  }
}
----------
$ javac main.java
main.java:2: error: <identifier> expected
  System.out.println('PENIS LICKER')
                    ^
main.java:2: error: unclosed character literal
  System.out.println('PENIS LICKER')
                     ^
main.java:2: error: unclosed character literal
  System.out.println('PENIS LICKER')
                                  ^
main.java:3: error: ';' expected
  int pen = 69
              ^
main.java:4: error: ';' expected
  int is = 420
              ^
5 errors
$ java main
Error: Could not find or load main class main
Caused by: java.lang.ClassNotFoundException: main

Ran at 09:33 PM EST