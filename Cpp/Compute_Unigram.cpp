#include <iostream>
#include <map>
#include <cstring>
#include <fstream>
#include <queue>
#include <boost/algorithm/string.hpp>
#include <list>
#include <algorithm>

using namespace std;

std::map<std::string, int> Tokenize()
		{
	string filename;
	cout << "What file do you want to use for input? ";
	getline(cin, filename);

	std::map<string,int> mymap;

	ifstream fin;
	fin.open(filename.c_str());
	char * token;

	string str;

	while (fin >> str)
	{
		char *fileName = (char*)str.c_str();
		token = strtok (fileName," ,.-!;:\|'}{][)(");
		while (token != NULL)
		{
			boost::algorithm::to_lower(token);
			mymap[token]++;
			token = strtok (NULL, " ,.-!;:\|'}{][)(");
		}
	}
	fin.close();
	return mymap;
		}



void print (std::list<string> myMap)
{
	for (std::list<string>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		std::cout << *it<< endl;
	}
}

std::list<string>Tokenize_list()
		{
	std::list<string> mylist;
	std::map<std::string, int> myMap =Tokenize();

	for (std::map<string,int>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		mylist.push_back(it->first);
	}

	return mylist;
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


	/*
	for (std::map<string,int>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		std::cout <<it->first<<"=>"<< it->second<< endl;
		total=total+it->second;
	}
	 */
	cout<<"Total Number of words"<<":"<<total<<endl;
	return total;
}

int main()
{
	//int total;
	std::list<string> mylist= Tokenize_list();      //// Tokenizes the document into a list
	print(mylist);					//// prints the contents of the tokenized list

	//std::list<string> mylist;
	std::map<std::string, int> myMap =Tokenize(); // Calculate Frequencies
	int total=print_freq_sorted(myMap);		// Print the frequencies in  sorted manner (priority queue used)		



	return 0;
}
