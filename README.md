main.cpp

std::cout << "hello" << std::endl


----------
$ g++ ./main.cpp -g -Wall -Wextra -o test.exe
./main.cpp:1:6: error: ‘cout’ in namespace ‘std’ does not name a type
    1 | std::cout << "hello" << std::endl
      |      ^~~~
./main.cpp:1:1: note: ‘std::cout’ is defined in header ‘<iostream>’; did you forget to ‘#include <iostream>’?
  +++ |+#include <iostream>
    1 | std::cout << "hello" << std::endl
$ ./test.exe 
➔ Command not run (did the previous command throw an error?)

Ran at 09:18 PM EST