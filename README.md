main.cpp

#include <iostream>
int main(){
  std::cout << "test"<< std::endl;
}
----------
$ g++ ./main.cpp -g -Wall -Wextra -o test.exe
$ ./test.exe 
test

Ran at 02:26 PM EST