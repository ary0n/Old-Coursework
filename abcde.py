# To figure this one out I tried to use regular expressions for python
# I imported the regular expressions and used it to ignore all special characters and numbers
# If the length of the string was less than 2 characters then it is true, this is the base case
# If the first letter was greater than the second letter in the string then it is false because it should be bigger as the letters progress
# Then I returned the string minus the first letter

import re
def isabcde(s):
    s = s.lower()
    s=re.sub("[^a-zA-Z]","",s)
    if len(s)<2:
        return True
    if s[0]>s[1]:
        return False
    return isabcde(s[1:])


# To call this I used a main that if the function above is true then it will print out it is an abcderian
# If the function above is false then it will print it isn't an abcderian
# Then I  checked for errors including the EOFerror

def main():

    while True:
        try:
            s= (input("The string?"))
            if isabcde(s) == True:
                print(s, "is an abcderian")
            if isabcde(s)== False:
                print(s, "is not an abcderian")
        except EOFError:
            return
        except:
            return

main()