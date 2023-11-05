main.cpp

void main(){
int a = 5;
int b = 7;
std::cout<<a+b<<std::endl;
}
----------
$ g++ ./main.cpp -g -Wall -Wextra -o test.exe
./main.cpp:1:1: error: ‘::main’ must return ‘int’
    1 | void main(){
      | ^~~~
./main.cpp: In function ‘int main()’:
./main.cpp:4:6: error: ‘cout’ is not a member of ‘std’
    4 | std::cout<<a+b<<std::endl;
      |      ^~~~
./main.cpp:1:1: note: ‘std::cout’ is defined in header ‘<iostream>’; did you forget to ‘#include <iostream>’?
  +++ |+#include <iostream>
    1 | void main(){
./main.cpp:4:22: error: ‘endl’ is not a member of ‘std’
    4 | std::cout<<a+b<<std::endl;
      |                      ^~~~
./main.cpp:1:1: note: ‘std::endl’ is defined in header ‘<ostream>’; did you forget to ‘#include <ostream>’?
  +++ |+#include <ostream>
    1 | void main(){
$ ./test.exe 
➔ Command not run (did the previous command throw an error?)

Ran at 09:20 PM EST