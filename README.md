main.cpp
puzzle2.txt out2.txt all_solutions
#include <iostream>
#include <fstream>
#include <utility>
#include <vector>
#include <set>
#include <algorithm>

//For each position on the grid, it finds all the banned words that start with the same character, then checks in each direction
//one character at a time, checking if it matches any of the banned word's next character until either it runs out of matches, where it returns false
//or one of the banned words runs out of characters, then it returns true 
bool checkForIllegalWords(const std::vector< std::vector <char> > &grid, const std::vector<std::string> &bannedWords, const std::vector<short> &indexOfMatchingBannedWords, 
							const std::pair<short, short> directions[8], unsigned short iteration, unsigned short direction, short x, short y){
	if(iteration == 0){
		std::vector<short> nextIndexOfMatchingBannedWords;
		unsigned short ySize = grid.size();
		unsigned short xSize = grid[0].size();
		unsigned short bannedWordsSize = bannedWords.size();
		for(unsigned short i = 0; i < ySize; i++){
			for(unsigned short j = 0; j < xSize; j++){
				for(unsigned short k = 0; k < bannedWordsSize; k++){
					if(grid[i][j] == bannedWords[k][0])
						nextIndexOfMatchingBannedWords.push_back(k);
				}
				if(checkForIllegalWords(grid, bannedWords, nextIndexOfMatchingBannedWords, directions, 1, 0, j, i))
					return true;
				nextIndexOfMatchingBannedWords.clear();
			}		
		}
		return false;
	}
	else if(iteration == 1){
		unsigned short matchSize = indexOfMatchingBannedWords.size();
		if(matchSize == 0)
			return false;
		for(unsigned short i = 0; i < matchSize; i++)
		{
			if(bannedWords[indexOfMatchingBannedWords[i]].length() <= 1)
				return true;
		}
		std::vector<short> nextIndexOfMatchingBannedWords;
		short xSize = grid[0].size();
		short ySize = grid.size();
		for(unsigned short i = 0; i < 8; i++){
			short newX = x + directions[i].first;
			if(newX < 0 || newX >= xSize)
				continue;
			short newY = y + directions[i].second;
			if(newY < 0 || newY >= ySize)
				continue;
			for(unsigned short j = 0; j < matchSize; j++)
			{
				if(grid[newY][newX] == bannedWords[indexOfMatchingBannedWords[j]][1]){
					nextIndexOfMatchingBannedWords.push_back(indexOfMatchingBannedWords[j]);
				}
			}
			if(checkForIllegalWords(grid, bannedWords, nextIndexOfMatchingBannedWords, directions, 2, i, newX, newY)){
				return true;
			}
			nextIndexOfMatchingBannedWords.clear();
		}
		return false;
	}
	else{
		unsigned short matchSize = indexOfMatchingBannedWords.size();
		if(matchSize == 0)
			return false;
		for(unsigned short i = 0; i < matchSize; i++)
		{
			if(bannedWords[indexOfMatchingBannedWords[i]].length() <= iteration)
				return true;
		}
		std::vector<short> nextIndexOfMatchingBannedWords;
		short xSize = grid[0].size();
		short ySize = grid.size();
		short newX = x + directions[direction].first;
		if(newX < 0 || newX >= xSize)
			return false;
		short newY = y + directions[direction].second;
		if(newY < 0 || newY >= ySize)
			return false;
		for(unsigned short i = 0; i < matchSize; i++)
		{
			if(bannedWords[indexOfMatchingBannedWords[i]].length() <= iteration)
				return true;
			if(grid[newY][newX] == bannedWords[indexOfMatchingBannedWords[i]][iteration])
				nextIndexOfMatchingBannedWords.push_back(indexOfMatchingBannedWords[i]);
		}
		if(checkForIllegalWords(grid, bannedWords, nextIndexOfMatchingBannedWords, directions, iteration+1, direction, newX, newY)){
			return true;
		}
		return false;
	}
	return false;
}

//The grid is passed as a copy since this function will make changes to grid and will be called multiple times from the same position
//Has four possible casses, the word is added, the letter to be added is the first one, the letter to be added is the senond one, and the letter is any letter after the second
//When it is the first letter, it can be added anywhere in the grid as long there is an empty space or that letter is already there
//If it is the second letter, it has to be one of the 8 adjacent spots that are available
//Otherwise, there is only one spot to check, the next one in that direction
//Checks if a word is a palendrom because if it is, it only has to check half the directions 
void AddWordInAllPossiblePositions(std::vector< std::vector <char> > grid, const std::string &wordToAdd, const std::pair<short, short> directions[8], 
									unsigned short letterIndex, short direction, short x, short y, std::set<std::vector< std::vector <char> > > &output,
									const std::vector<std::string> &bannedWords){
	if(letterIndex == wordToAdd.size() && !checkForIllegalWords(grid, bannedWords, std::vector<short>(0), directions, 0, 0, 0, 0)){
		output.insert(grid);
	}
	else if(letterIndex == 0){
		unsigned short ySize = grid.size();
		unsigned short xSize = grid[0].size();
		for(unsigned short i = 0; i < ySize; i++){
			for(unsigned short j = 0; j < xSize; j++){
				if(grid[i][j] == wordToAdd[0]){
					AddWordInAllPossiblePositions(grid, wordToAdd, directions, 1, 0, j, i, output, bannedWords);
				}
				else if(grid[i][j] == '_'){
					grid[i][j] = wordToAdd[0];
					AddWordInAllPossiblePositions(grid, wordToAdd, directions, 1, 0, j, i, output, bannedWords);
					grid[i][j] = '_';
				}
			}		
		}
	}
	else if(letterIndex == 1){
		short length = wordToAdd.length() - 1;
		short xSize = grid[0].size();
		short ySize = grid.size();
		std::string reverse = wordToAdd;
		std::reverse(reverse.begin(), reverse.end());
		unsigned short directionsToCheck = 8;
		if(wordToAdd == reverse)
			directionsToCheck = 4;
		for(unsigned short i = 0; i < directionsToCheck; i++){
			short endX = x + (length * directions[i].first);
			if(endX < 0 || endX >= xSize)
				continue;
			short endY = y + (length * directions[i].second);
			if(endY < 0 || endY >= ySize)
				continue;
			short newX = x + directions[i].first;
			if(newX < 0 || newX >= xSize)
				continue;
			short newY = y + directions[i].second;
			if(newY < 0 || newY >= ySize)
				continue;
			if(grid[newY][newX] == wordToAdd[1]){
				AddWordInAllPossiblePositions(grid, wordToAdd, directions, 2, i, newX, newY, output, bannedWords);
			}
			else if(grid[newY][newX] == '_'){
				grid[newY][newX] = wordToAdd[1];
				AddWordInAllPossiblePositions(grid, wordToAdd, directions, 2, i, newX, newY, output, bannedWords);
				grid[newY][newX] = '_';
			}
		}
	}
	else{
		short xSize = grid[0].size();
		short ySize = grid.size();
		short newX = x + directions[direction].first;
		if(newX < 0 || newX >= xSize)
			return;
		short newY = y + directions[direction].second;
		if(newY < 0 || newY >= ySize)
			return;
		if(grid[newY][newX] == wordToAdd[letterIndex]){
			AddWordInAllPossiblePositions(grid, wordToAdd, directions, letterIndex+1, direction, newX, newY, output, bannedWords);
		}
		else if(grid[newY][newX] == '_'){
			grid[newY][newX] = wordToAdd[letterIndex];
			AddWordInAllPossiblePositions(grid, wordToAdd, directions, letterIndex+1, direction, newX, newY, output, bannedWords);
			grid[newY][newX] = '_';
		}
	}
}

//Given a position on a grid, adds each letter there and if it doesn't form a banned word it adds it to the grid
void FillEmptySpace(std::vector< std::vector <char> > grid, const std::vector<char> &notBannedChars, std::set<std::vector< std::vector <char> > > &output, unsigned short x, unsigned short y, 
					const std::vector<std::string> &bannedWords, const std::pair<short, short> directions[8]){
	unsigned short size = notBannedChars.size();
	for(unsigned short i = 0; i < size; i++){
		grid[y][x] = notBannedChars[i];
		if(!checkForIllegalWords(grid, bannedWords, std::vector<short>(0), directions, 0, 0, 0, 0))
			output.insert(grid);
	}
}












int main(int argc, char* argv[]){
	//Some simple checks to make sure the inputs are good
	//O(1)
	if(argc < 4){
		std::cerr << "Error: Not enough command line arguments" << std::endl;
		exit(0);
	}

	std::ifstream  inStream(argv[1]);
	if(!inStream.good()){
		std::cerr << "Error: Bad input" << std::endl;
		exit(1);
	}

	std::ofstream outStream(argv[2]);
	if(!outStream.good()){
		std::cerr << "Error: Bad output" << std::endl;
		exit(2);
	}

	// 0 1 2
	// 3   4
	// 5 6 7
	//Maps an index to a direction, as show above. To be used when a word has 3 or more letters and they have to be in line.
	std::pair<short, short> directions[] = {
 	std::make_pair(-1, -1),
	std::make_pair( 0, -1),
	std::make_pair( 1, -1),
	std::make_pair(-1,  0),
	std::make_pair( 1,  0), 
	std::make_pair(-1,  1),
	std::make_pair( 0,  1),
	std::make_pair( 1,  1)};

	short x;
	short y;

	inStream >> x;
	inStream >> y;

	std::vector<std::string> requiredWords;
	std::vector<std::string> bannedWords;
	std::vector<char>		 notBannedChars;

	for(unsigned short i = 97; i < 123; i++){
		notBannedChars.push_back(char(i));
	}

	//O(f + r)
	char temp;
	std::string word;
	while(inStream >> temp){
		inStream >> word;
		if(temp == '+'){
			requiredWords.push_back(word);
		}
		else if (temp == '-'){
			bannedWords.push_back(word);
			if(word.length() == 1){
				unsigned short size = notBannedChars.size();
				for(unsigned short i = 0; i < size; i++){
					if(notBannedChars[i] == word[0]){
						notBannedChars.erase(notBannedChars.begin() + i);
					}
				}
			}
		}
		else{
			std::cerr << "Error: File not formated correcttly" << std::endl;
			exit(3);
		}
	}


	//Uses 2 sets wich alternate between between being the one the function is called from and the one output is added to
	//This is to prevent unfinished grids from being stored
	//After each word is added, it checks to see if there are any banned words before calling the next word to prune unnessesary branches
	std::vector< std::vector <char> > grid(y, std::vector<char>(x, '_'));
	std::set<std::vector< std::vector <char> > > output1;
	std::set<std::vector< std::vector <char> > > output2;
	AddWordInAllPossiblePositions(grid, requiredWords[0], directions, 0, 0, 0, 0, output1, bannedWords);
	unsigned short numberOfRequiredWords = requiredWords.size();
	bool output1HoldsCurrentData = true;
	for(unsigned short i = 1; i < numberOfRequiredWords; i++){
		if(output1HoldsCurrentData){
			output2.clear();
			for(std::set<std::vector< std::vector <char> > >::iterator itr = output1.begin(); itr != output1.end(); itr++){
				AddWordInAllPossiblePositions(*itr, requiredWords[i], directions, 0, 0, 0, 0, output2, bannedWords);
			}
		}
		else{
			output1.clear();
			for(std::set<std::vector< std::vector <char> > >::iterator itr = output2.begin(); itr != output2.end(); itr++){
				AddWordInAllPossiblePositions(*itr, requiredWords[i], directions, 0, 0, 0, 0, output1, bannedWords);
			}
		}
		output1HoldsCurrentData = !output1HoldsCurrentData;
	}
	for(unsigned short i = 0; i < y; i++){
		for(unsigned short j = 0; j < x; j++){
			if(output1HoldsCurrentData){
				output2.clear();
				for(std::set<std::vector< std::vector <char> > >::iterator itr = output1.begin(); itr != output1.end(); itr++){
					if((*itr)[i][j] == '_'){
						FillEmptySpace(*itr, notBannedChars, output2, j, i, bannedWords, directions);
					}
					else
						output2.insert(*itr);
				}
			}
			else{
				output1.clear();
				for(std::set<std::vector< std::vector <char> > >::iterator itr = output2.begin(); itr != output2.end(); itr++){					
					if((*itr)[i][j] == '_'){
						FillEmptySpace(*itr, notBannedChars, output1, j, i, bannedWords, directions);
					}
					else
						output1.insert(*itr);
				}
			}
			output1HoldsCurrentData = !output1HoldsCurrentData;
		}
	}
	std::string mode = argv[3];
	if(mode != "one_solution" && mode != "all_solutions"){
		std::cerr << "Error: Bad mode" << std::endl;
		exit(3);
	}
	if(output1HoldsCurrentData){
		if(mode == "all_solutions")
			outStream << output1.size() << " solution(s)" << std::endl;
		for(std::set<std::vector< std::vector <char> > >::iterator itr = output1.begin(); itr != output1.end(); itr++){
			outStream << "Board:" << std::endl;
			for(unsigned short i = 0; i < y; i++){
				outStream << "  ";
				for(unsigned short j = 0; j < x; j++){
					outStream << (*itr)[i][j];
				}
				outStream << std::endl;
			}
			if(mode == "one_solution")
				return -1;
		}
	}
	else{
		if(mode == "all_solutions")
			outStream << output2.size() << " solution(s)" << std::endl;
		for(std::set<std::vector< std::vector <char> > >::iterator itr = output2.begin(); itr != output2.end(); itr++){
			outStream << "Board:" << std::endl;
			for(unsigned short i = 0; i < y; i++){
				outStream << "  ";
				for(unsigned short j = 0; j < x; j++){
					outStream << (*itr)[i][j];
				}
				outStream << std::endl;
			}
			if(mode == "one_solution")
				return -1;
		}
	}
}



