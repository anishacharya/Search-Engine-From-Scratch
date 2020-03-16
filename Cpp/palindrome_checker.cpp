#include <iostream>
#include <map>
#include <cstring>
#include <fstream>
#include <queue>
#include <boost/algorithm/string.hpp>
#include <list>
#include <algorithm>
using namespace std;


bool check_palindrome(std::string s)
{
    std::size_t i = 0;
    std::size_t j = s.length() - 1;
    while (i < j)
    {
        if (s[i++] != s[j--])
        {
            return false;
        }
    }

    return true;
}

std::list<string> GetPalindrome()
{
	string filename;
	cout << "What file do you want to use for input? ";
	getline(cin, filename);
	ifstream fin;
	fin.open(filename.c_str());
	string str;
	char * token;

	std::list<string>List_Palindrome;

	while (fin >> str)
	{
		char *fileName = (char*)str.c_str();
		token = strtok (fileName," ,.-!;:\|'}{][)(");
		while (token != NULL)
		{
			boost::algorithm::to_lower(token);
			//mymap[token]++;
			if(check_palindrome(token))
			{
				List_Palindrome.push_back(str);
			}

			token = strtok (NULL, " ,.-!;:\|'}{][)(");

		}
	}
	fin.close();
	return List_Palindrome;
}

void print (std::list<string> myMap)
{
	for (std::list<string>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		std::cout << *it<< endl;
	}
}

int print_freq_sorted(std::map<std::string, int> myMap)
{
	int total=0;

	std::priority_queue<std::pair<int, std::string> > pq;
	for (std::map<string,int>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		pq.push(std::make_pair(it->second, it->first)); ///push into heap
		total=total+it->second;
	}
	int size=myMap.size();
	for(int i=1; i<=size;i++)
	{
		std::cout << pq.top().first << ", " << pq.top().second << std::endl;
		pq.pop();
	}
	cout<<"Total Number of words"<<":"<<total<<endl;
	return total;
}

void print_freq(std::map<std::string,int> mymap)
{

	for (std::map<std::string,int>::iterator it=mymap.begin(); it!=mymap.end(); ++it)
		std::cout << it->first << " => " << it->second << '\n';

}

std::map<string, int> GetFreq(std::list<string>list)
{
	std::map<std::string, int> Frequency;
	for(std::list<string>::iterator it=list.begin();it!=list.end();++it)
		Frequency[*it]++;
	return Frequency;
}

int main()
{
	std::list<string>Palindromes=GetPalindrome(); /// get the palindromes
	print(Palindromes);				/// print the palindromes
	std::map<string, int> Frequency=GetFreq(Palindromes); //// get the frequency of the palindromes
	print_freq(Frequency);         
	print_freq_sorted(Frequency);  ///print the frequency in sorted order 

}
// It has a linear time complexity.
// The palindrome checker is O(m/2)~O(m) where m is the size of each string. 
// Then I used a hashmap to get the frequency. And then used a priority queue to sort them and print in order
// Thus, worst case is O(n). 
