# First I made the first function to check for duplicates
# If the length of the list is the same as the length of the set which doesn't include extras then it is true
# If the two don't equal then it is false
# I also imported random to be able to use the random command later
import random
def hasduplicates(l):
    return len(l)!=len(set(l))

# I first made an empty list
# Then for n in the range for whatever value count may be, I made random numbers from 1-365 and added them to the empty list for however long count is
def onetest(count):
    listm = []
    for n in range(count):
        listm.append(random.randint(1,365))
    return hasduplicates(listm)

# For this function, I made a counter to start off
# We test if onetest (the above function) is true, then it will add one to the counter
# We do this test num times for what value num is
# This will return the number of truths divied by how many times we run the test which is num
def probab(count,num):
    i=0
    for n in range(num):
        onetest(count)
        if onetest(count) is True:
            i+=1
    return(i/num)

# For the final function, I inputed the values for count and num to get the approximations
# If the probability is less than .9 of getting a duplicate birthday, then it will continue to run
# We start at count as 2 people and it will keep running through count until it gets to .9 probability of having the same birthday
def main():
    count=1
    num=5000
    while probab(count,num)<0.9:
        count+=1
        print("for",count,"people the probability of 2 birthdays is",probab(count,num))

main()

#We need 41 people for the probability to be common of over .9
#For at least .5 we need 23 people


