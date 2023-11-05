main.cpp

#include <iostream>
int main(){
  std::cout << "hello wordld " << endl;
}
----------
$ g++ ./main.cpp -g -Wall -Wextra -o test.exe
./main.cpp: In function ‘int main()’:
./main.cpp:3:35: error: ‘endl’ was not declared in this scope; did you mean ‘std::endl’?
    3 |   std::cout << "hello wordld " << endl;
      |                                   ^~~~
      |                                   std::endl
In file included from /usr/include/c++/11/iostream:39,
                 from ./main.cpp:1:
/usr/include/c++/11/ostream:684:5: note: ‘std::endl’ declared here
  684 |     endl(basic_ostream<_CharT, _Traits>& __os)
      |     ^~~~
$ ./test.exe 
➔ Command not run (did the previous command throw an error?)

Ran at 02:26 PM EST