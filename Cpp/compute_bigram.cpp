#include <iostream>
#include <map>
#include <cstring>
#include <fstream>
#include <queue>
#include <boost/algorithm/string.hpp>
#include <list>
#include <algorithm>

using namespace std;

std::list<string> tokenizer()
{
	std::list<string> mylist;
	string filename;
	cout << "What file do you want to use for input? ";
	getline(cin, filename);
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

			mylist.push_back(token);
			token = strtok (NULL, " ,.-!;:\|'}{][)(");
		}
	}
	fin.close();


	return mylist;
}

void print_tokens(std::list<string>mylist)
{
	for (std::list<string>::iterator it=mylist.begin(); it!=mylist.end(); ++it)
	{
		cout<<*it<<endl;
	}
}

std::list<string> compute_bigrams(std::list<string> mylist)
{
	std::list<string> bigrams;
	std::list<string>::iterator it_fast=mylist.begin();
	it_fast++;
	std::list<string>::iterator it=mylist.begin();
	while ( it_fast!=mylist.end())
	{
		bigrams.push_back(*it+*it_fast);
		it_fast++;
		it++;
	}
	return bigrams;
}


std::map<string,int> Freq_bigram(std::list<string> bigrams)
		{
			std::map<string,int> mymap;
			for (std::list<string>::iterator it=bigrams.begin(); it!=bigrams.end(); ++it)
			{
				mymap[*it]++;

			}
/*
			for (std::map<string,int>::iterator it=mymap.begin(); it!=mymap.end(); ++it)
					{
						//pq.push(std::make_pair(it->second, it->first)); ///push into heap
						cout<<it->second<<endl;
					}
					*/
			return mymap;

		}

int print_freq_sorted(std::map<std::string, int> myMap)
{
	int total=0;

	std::priority_queue<std::pair<int, std::string> > pq;
	for (std::map<string,int>::iterator it=myMap.begin(); it!=myMap.end(); ++it)
	{
		//cout<<it->second<<endl;
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

int main()
{
	std::list<string> tokens=tokenizer();
	print_tokens(tokens);
	std::list<string> bigrams=compute_bigrams(tokens);
	print_tokens(bigrams);
	std::map<string,int> Frequency = Freq_bigram(bigrams);
	print_freq_sorted(Frequency);
	return 0;
}
