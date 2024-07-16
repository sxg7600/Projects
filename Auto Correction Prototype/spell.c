#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "spell.h"

/*  Suggestions
- When you work with 2D arrays, be careful. Either manage the memory yourself, or
work with local 2D arrays. Note C99 allows parameters as array sizes as long as 
they are declared before the array in the parameter list. See: 
https://www.geeksforgeeks.org/pass-2d-array-parameter-c/

Worst case time complexity to compute the edit distance from T test words
 to D dictionary words where all words have length MAX_LEN:
Student answer:  Theta(3^MAX_LEN)


Worst case to do an unsuccessful binary search in a dictionary with D words, when 
all dictionary words and the searched word have length MAX_LEN 
Student answer:  Theta(MAX_LEN * log(D))
*/


/* You can write helper functions here */
int min(int a, int b, int c);
int printTable(char* first_string, char* second_string, int dp[][strlen(second_string) + 1]);
int compare(const void *arg1, const void *arg2);
int checkSeparator(char* word);
int binarySearch(char dictWords[1000][100], char*searchWordCopy, int printOn, int left, int right);
int noOfSearches(char dictWords[1000][100], char*searchWordCopy, int printOn, int left, int right, int count);
int minDist(int arr[], int size);
int howManyMinDistance(int distances[], int size, int minimumDistance);

int howManyMinDistance(int distances[], int size, int minimumDistance)
{
	int count = 0;
	for(int i = 0; i < size; i ++)
	{
		if(distances[i] == minimumDistance)
			count += 1;
	}
	return count;
}

int minDist(int arr[], int size)
{
	int min = arr[0];
	for(int i = 0; i < size; i ++)
	{
		if(min > arr[i])
			min = arr[i];
	}
	return min;
}

int noOfSearches(char dictWords[1000][100], char*searchWordCopy, int printOn, int left, int right, int count)
{
	if(left > right)
	{
		return count;
	}
	int mid = (left + right) / 2;
	
	if(strcmp(dictWords[mid], searchWordCopy) == 0)
		return count += 1;
	
	if(strcmp(dictWords[mid], searchWordCopy) > 0)
		return noOfSearches(dictWords, searchWordCopy, printOn, left, mid - 1, count += 1);
	
	else
		return noOfSearches(dictWords, searchWordCopy, printOn, mid + 1, right, count += 1);
}

int binarySearch(char dictWords[100][100], char*searchWordCopy, int printOn, int left, int right)
{
	if(left > right)
	{
		if(printOn)
			printf("\nNot found");
		return 0;
	}
	int mid = (left + right) / 2;
	if(printOn)
		printf("\ndict[%d] = %s", mid, dictWords[mid]);
	
	if(strcmp(dictWords[mid], searchWordCopy) == 0)
	{
		if(printOn)
			printf("\nWord found");
		return 1;
	}
	
	if(strcmp(dictWords[mid], searchWordCopy) > 0)
		return binarySearch(dictWords, searchWordCopy, printOn, left, mid - 1);
	
	else
		return binarySearch(dictWords, searchWordCopy, printOn, mid + 1, right);
}

int checkSeparator(char* word)
{
	for(int i = 0; i < strlen(word); i ++)
	{
		if(word[i] == '.' || word[i] == ',' || word[i] == '?' || word[i] == '!')
			return 1;
	}
	
	return 0;
	
}

int compare(const void *arg1, const void *arg2)
{
	 return strcmp(arg1, arg2);
}

int printTable(char* first_string, char* second_string, int dp[][strlen(second_string) + 1])
{
		char bar = '|';
	printf("%4c%4c", bar, bar);
	for(int i = 0; i < strlen(second_string); i ++)
	{
	    printf("%3c%c", second_string[i], bar);
	}
	printf("\n");
	for(int i = 0; i < strlen(second_string) + 2; i ++)
	{
	    printf("----");
	}
    printf("\n");
    for(int i = 0; i < strlen(first_string) + 1; i ++)
    {
        for(int j = 0; j < strlen(second_string) + 1; j ++)
        {
            if(i == 0 && j == 0)
                printf("%4c", bar);
            else if(j == 0)
                printf("%3c%c", first_string[i - 1], bar);
            printf("%3d%c", dp[i][j], bar);
        } 
        printf("\n");
	   for(int i = 0; i < strlen(second_string) + 2; i ++)
	   {
	        printf("----");
	   }
	   printf("\n");
    }
	return dp[strlen(first_string)][strlen(second_string)];
}

int min(int a, int b, int c)
{
	if(a <= b && a <= c)
	    return a;
	if(b <= a && b <= c)
	    return b;
	return c;
	
}


/*
Parameters:
first_string - pointer to the first string (displayed vertical in the table)
second_string - pointer to the second string (displayed horizontal in the table)
print_table - If 1, the table with the calculations for the edit distance will be printed.
              If 0 (or any value other than 1)it will not print the table
(See sample run)
Return:  the value of the edit distance (e.g. 3)
*/
int edit_distance(char * first_string, char * second_string, int print_table){
	int dp[strlen(first_string) + 1][strlen(second_string) + 1];
	
	for(int i = 0; i <= strlen(first_string); i ++)
	{
		for(int j = 0; j <= strlen(second_string); j ++)
		{
			if(i == 0 && j == 0)
				dp[i][j] = 0;
			else if(i == 0)
				dp[i][j] = 1 + dp[i][j - 1];
			else if(j == 0)
				dp[i][j] = 1 + dp[i - 1][0];
			else
			{
				if(first_string[i - 1] == second_string[j - 1])
					dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1]);
				else
					dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1);
			}
		}
	}
	
	if(print_table == 0)
		return dp[strlen(first_string)][strlen(second_string)];
	return printTable(first_string, second_string, dp);	
}
	  

/*
Parameters:
testname - string containing the name of the file with the paragraph to spell check, includes .txt e.g. "text1.txt" 
dictname - name of file with dictionary words. Includes .txt, e.g. "dsmall.txt"
printOn - If 1 it will print EXTRA debugging/tracing information (in addition to what it prints when 0):
			 dictionary as read from the file	
			 dictionary AFTER sorting in lexicographical order
			 for every word searched in the dictionary using BINARY SEARCH shows all the "probe" words and their indices indices
			 See sample run.
	      If 0 (or anything other than 1) does not print the above information, but still prints the number of probes.
		     See sample run.
*/
void spell_check(char * testname, char * dictname, int printOn){
	// Write your code here
	char outputFile[50] = "out_";
	strcat(outputFile, testname);
	printf("Corrected output filename: %s", outputFile);
	FILE* dictFile = fopen(dictname, "r");
	FILE* output = fopen(outputFile, "w");
	int size;
	fscanf(dictFile, "%d", &size);
	printf("\n\nLoading the dictionary file name: %s", dictname);
	printf("\n\nDictionary has size: %d", size);
	char dictWords[size][100];
	int i = 0;
	
	while(!feof(dictFile))
	{
		fscanf(dictFile, "%s", dictWords[i]);
		//printf("\n\ndictWords[%d] -> %s", i, dictWords[i]);
		i += 1;
	}
	fclose(dictFile);
	
	if(printOn == 1)
	{
		printf("\n\nOriginal dictionary: ");
		for(int i = 0; i < size; i ++)
		{
			printf("\n%d. %s", i, dictWords[i]);
		}
	}
	
	qsort((void *)dictWords, (size_t)size, 25, compare);

	if(printOn == 1)
	{
		printf("\n\nSorted dictionary (alphabetical order): ");
		for(int i = 0; i < size; i ++)
		{
			printf("\n%d. %s", i, dictWords[i]);
		}
	}

	FILE* inputFile = fopen(testname, "r");
	char line[100000];
	fgets(line, 100000, inputFile);
	fclose(inputFile);
	char lineCopy[100000];
	strcpy(lineCopy, line);
	int noOfWords = 0;
	int indexCounter = 0;
	while(indexCounter != strlen(line))
	{
		int counterIncreased = 0;
		if(line[indexCounter] == ' ' || line[indexCounter] == ',' || line[indexCounter] == '.' || line[indexCounter] == '?' || line[indexCounter] == '!')
		{	
			while((line[indexCounter] == ' ' || line[indexCounter] == ',' || line[indexCounter] == '.' || line[indexCounter] == '?' || line[indexCounter] == '!'))
			{
				indexCounter += 1;
				counterIncreased = 1;
			}
			noOfWords += 1;
		}
		if(!counterIncreased)
			indexCounter += 1;
	}
	if(!(line[strlen(line) - 1] == ' ' || line[strlen(line) - 1] == '.' || line[strlen(line) - 1] == ',' || line[strlen(line) - 1] == '?') || line[strlen(line) - 1] == '!')
		noOfWords += 1;
	
	char allWords[noOfWords][100];
	
	char* token;
	char delimeters[] = " ,!?.";
	token = strtok(line, delimeters);
	indexCounter = 0;
	while(token != NULL)
	{
		strcpy(allWords[indexCounter], token);
		token = strtok(NULL, delimeters);
		indexCounter += 1;
	}
	
	int lineOffset = 0;
	for(int i = 0; i < noOfWords; i ++)
	{
		char searchWord[100] = "";
		strcpy(searchWord, allWords[i]);
		
		char searchWordCopy[100] = "";
		strcpy(searchWordCopy, allWords[i]);
		
		int left = 0, right = size - 1, j = 0;
		char userWord[100];
		char searchWordCopyLower[100] = "";
		strcpy(searchWordCopyLower, searchWordCopy);
		while (searchWordCopyLower[j]) 
		{
			searchWordCopyLower[j] = tolower(searchWordCopyLower[j]);
			j += 1;
		}
		if(printOn == 1)
			printf("\n\nBinary Search for: %s", searchWordCopyLower);
		int searchFound = binarySearch(dictWords, searchWordCopyLower, printOn, left, right);
		
		printf("\n----> |%s| (Words compared when searching: %d)", searchWordCopy, noOfSearches(dictWords, searchWordCopyLower, printOn, left, right, 0));
		if(!searchFound)
		{
			printf("\n-1 - type correction");
			printf("\n 0 - leave word as is (do not fix spelling)");
			
			int distances[size];
			for(int i = 0; i < size; i ++)
			{
				distances[i] = edit_distance(searchWordCopyLower, dictWords[i], 0);
			}
			
			int minimumDistance = minDist(distances, size);
			printf("\n\tMinimum distance: %d  (computed edit distances: %d)", minimumDistance, size);
			printf("\n\tWords that give minimum distance:");
			
			int noOfMinDistances = howManyMinDistance(distances, size, minimumDistance);
			
			char minDistWords[noOfMinDistances][100];
			int indexForMinDistWords = 0;
			
			for(int i = 0; i < size; i ++)
			{
				if(distances[i] == minimumDistance)
				{
					strcpy(minDistWords[indexForMinDistWords], dictWords[i]);
					indexForMinDistWords += 1;
				}
			}
			
			for(int i = 0; i < noOfMinDistances; i ++)
			{
				printf("\n %d - %s", i + 1, minDistWords[i]);
			}
			
			int userChoice;
			printf("\nEnter your choice (from the above options): ");
			scanf("%d", &userChoice);
			
			if(userChoice == -1)
			{
				printf("\nType your correction: ");
				scanf("%s", userWord);
			}
			
			else if(userChoice == 0)
			{
				strcpy(userWord, searchWordCopy);
			}
			
			else
			{
				strcpy(userWord, minDistWords[userChoice - 1]);
			}
		}
		else
		{
			strcpy(userWord, searchWord);
			printf("\n\t- OK\n");
		}
		lineOffset += strlen(searchWordCopy);
		fprintf(output, "%s", userWord);
		
		while((lineOffset != strlen(lineCopy)) && (lineCopy[lineOffset] == ' ' || lineCopy[lineOffset] == ',' || lineCopy[lineOffset] == '.' || lineCopy[lineOffset] == '?' || lineCopy[lineOffset] == '!'))
		{
			fprintf(output, "%c", lineCopy[lineOffset]);
			lineOffset += 1;
		}
	}
	fclose(output);
}
