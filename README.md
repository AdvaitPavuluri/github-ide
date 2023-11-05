main.cpp

#include <iostream>
int main(){
    std::cout<<"Hello World"<<std::endl;
}
----------
$ javac Gurney.java
Gurney.java:1: error: ';' expected
class Gurney {    public static void main(String[] args) {        System.out.println("Big big gurn");        System.out.println("Big big gurn again");        System.out.println("Big big gurn again 2")    }    }
                                                                                                                                                                                                        ^
1 error
$ java Gurney
Error: Could not find or load main class Gurney
Caused by: java.lang.ClassNotFoundException: Gurney

Ran at 08:16 PM EST
