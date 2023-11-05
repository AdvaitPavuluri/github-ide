main.cpp

#include <iostream>
using namespace std;
int main(){
  for(int i = 0; i < 100, i++){
    cout << "Hello World" << endl;
  }
}
----------
$ g++ ./main.cpp -g -Wall -Wextra -o test.exe
./main.cpp: In function ‘int main()’:
./main.cpp:4:20: warning: left operand of comma operator has no effect [-Wunused-value]
    4 |   for(int i = 0; i < 100, i++){
      |                  ~~^~~~~
./main.cpp:4:30: error: expected ‘;’ before ‘)’ token
    4 |   for(int i = 0; i < 100, i++){
      |                              ^
      |                              ;
$ ./test.exe 

Ran at 08:44 PM EST