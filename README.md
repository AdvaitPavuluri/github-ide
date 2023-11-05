main.cpp

#include <iostream>
int main(){
    std::cout<<"Hello World"<<std::endl;
}
----------
$ g++ main.cpp -g -Wall -Wextra -o test.exe
main.cpp:1:24: warning: extra tokens at end of #include directive
    1 | #include <iostream>int main(){    std::cout<<"Hello World"<<std::endl;}
      |                        ^~~~
main.cpp:1:10: fatal error: iostream>in: No such file or directory
    1 | #include <iostream>int main(){    std::cout<<"Hello World"<<std::endl;}
      |          ^~~~~~~~~~~~~
compilation terminated.
$ ./test.exe 

Ran at 08:30 PM EST