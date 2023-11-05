main.cpp

#include <iostream>
using namespace std;
int main(){
  cout << "hello world" << endl;
}
----------
$ javac main.java
main.java:2: error: <identifier> expected
  System.out.println('test')
                    ^
main.java:2: error: unclosed character literal
  System.out.println('test')
                     ^
main.java:2: error: unclosed character literal
  System.out.println('test')
                          ^
main.java:3: error: <identifier> expected
  int pen = 69
         ^
main.java:4: error: ';' expected
  int is = 420
              ^
5 errors
$ java main
Error: Could not find or load main class main
Caused by: java.lang.ClassNotFoundException: main

Ran at 12:57 PM EST
