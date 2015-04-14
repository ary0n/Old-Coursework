# First I had to open the text file so i made infile = open the dict file in read form
# I then created a new dictionary
infile = open ("dict.txt", "r")

d={}

# This function for printing the anagrams later on
# For this smartoutput, I made if the length of the dictionary value associated with a word is two then it will print the two values
# If not, it will print the first element of the list, which is all the values associated with the key, then do recursion and keep running through the function until there is only 2 values left in the list of values
def smartoutput(L):
    if len(L)==2:
        print(L[0],"and",L[1])
    else:
        print(L[0],',',end="")
        return smartoutput(L[1:])

# For main, first I made a temporary list in which to add the elements of the dictionary file
# It read one line at a time through the dict.txt file and then if it didn't equal a new line, it would append each line to the empty list
def main():

    temporarylist=[]
    for line in infile:
        if line !="\n":
            line=line[:-1]
            newline=line.lower()
            temporarylist.append(newline)



# Here, I made a for loop that read each word in the temporary list
# I then made a new variable that had each word lower cased and stripped of white spaces
# I then sorted each word alphabetically
# Then I joined each word back into strings
# If my new string (which is each word alphabetically sorted by character) wasn't in the dictionary, then it would add it to the dictionary as the key of its unsorted value. If the sorted word (the key) is already in there, then it will add another word to its value list
# If my key value is already in the dictionary, then it won't add the key word again
    for word in temporarylist:
        word_list = list(word.strip().lower())
        word_list.sort()
        newkey=(''.join(word_list))
        if newkey not in d:
            d[newkey] =[word]
        else:
            if word not in d[newkey]:
                d[newkey].append(word)

# The while True will keep asking the user for another input until exits
# Here I asked the user for an input which is scramble
# I then lower cased it
# Made it into a list and alphabetically sorted each character in the input word and made it back to a string
# I made L be the dictionary value for the inputed word
# If there is an EOF error, then this will break out of the loop and end
# If there is a type error, however, this will ignore it
#If there is a valueerror, it will ignore it as well

    while (True):
        try:

            scramble = str(input("Your word? "))
            scramble2 = scramble.lower()
            scramblelist = list(scramble2)
            scramblelist.sort()
            scramblelist2=''.join(scramblelist)
            L=(d.get(scramblelist2,0))
            NewL=list((d.get(scramblelist2,0)))
        except EOFError:
            return
        except TypeError:
            pass
        except ValueError:
            pass
# If the sorted inputed word isn't in the temporary list which has all the words of the dictionary, then it will print that the inputed word isn't in the dictionary
# If the length of the list of values for the sorted input word is 1, then it will print there aren't any anagrams because that length of 1 is the word itself
# For the value of length 2, I needed to make a new list that was the same as L. Here if L had two values associated with the sorted inputed word, then it would remove the inputed word and then print the only anagram was the other word value
# If there is more than 2 values for the sorted input word, then I had to use the smartoutput function above to print out correctly according to how many values there were
        if scramble2 not in temporarylist:
            print("The word", scramble,"is not in dictionary")
        elif len(L)==1:
            print("There are no anagrams for",scramble)
        elif len(L)==2:
            NewL.remove(scramble2)
            L3=''.join(NewL)
            print("The only anagram for",scramble, "is", L3)
        else:
            NewL.remove(scramble2)
            print("The anagrams for", scramble,"are", end=" ")
            smartoutput(NewL)


main()

# Here I closed the file

infile.close()





