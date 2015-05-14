class JumbleUser{
  public static int lengthLongestNDCSS1(Jumble j){
	JumbleIt jumIt = new JumbleIt(j);
	JumbleIt jIt = new JumbleIt(j);
	int spcs = 1;
	int curTotal = 1;
	int cur;
	int length = 0;
	
	try{
		while(jIt.hasNext()){ //determines smallest length
			jIt.next();
			length++;
		}//end while
	}//end try
	catch(UsingIteratorPastEndException e){} 
	
	if(length == 0)
		return(0);
	else
		cur = 0;

	try{
		jumIt.next();// begins comparision with 2nd element
	}
	catch(UsingIteratorPastEndException e){} 

	if(length == 2){// handle 2 elements seperately
		try{
      JumbleIt jIt2 = new JumbleIt(j);
      int first = jIt2.next();
      int second = jIt2.next();
		if(first <= second)
			return(2);
		else
			return(1);
		}
		catch (UsingIteratorPastEndException e){}
	}//end if length == 2
	else{ //arrays with > 2 elements
		try{
      JumbleIt jIt3 = new JumbleIt(j);
      int previous = jIt3.next();
      while(jIt3.hasNext()){//using hasNext() to sweep thru array
        cur = jIt3.next();
        if(previous <= cur){
          curTotal++;
          if(spcs < curTotal)// don't look back
            spcs = curTotal; // always the largest
        }//end if
        else{ //break occurs
          curTotal = 1; //reset counter
        }//end else
        previous = cur;
      }//end while
      return(spcs);
	  }//end try
	  catch(UsingIteratorPastEndException e){} 
	}//end else
	return 0;
  }//end lengthLongestNDCSS1()
	
  //copy and pasted function above...
  //cannot use hasNext(), use try/catch instead
  public static int lengthLongestNDCSS2(Jumble j){
    JumbleIt jumIt = new JumbleIt(j);
    JumbleIt jIt = new JumbleIt(j);
    int spcs = 1;
    int curTotal = 1;
    int cur;
    int length = 0;
    
    try{
      while(true){
        jIt.next();
        length++; //determine smallest length
      }//end while
    }//end try
    catch(UsingIteratorPastEndException e){}
    
    if(length == 0)
      return(0);
    else
      cur = 0;
      
    if(length == 1)
      return(1);
        
    //length greater than 1
    try{
      jumIt.next();// begins comparision with 2nd element
    }
    catch (UsingIteratorPastEndException e){}

    if(length == 2){// handle 2 elements seperately
      try{
        JumbleIt jIt2 = new JumbleIt(j);
        int first = jIt2.next();
        int second = jIt2.next();
        if(first <= second)
          return(2);
        else
          return(1);
      }
      catch(UsingIteratorPastEndException e){}
    }//end if
    else{//arrays with > 2 elements
      try{
        JumbleIt jIt3 = new JumbleIt(j);
        int previous = jIt3.next();
        int current = 0;
  
        while(true){ //sweep thru array without hasNext()
          if(cur+1 >= length)
            throw new UsingIteratorPastEndException("blah");
            
          current = jIt3.next();
          
          if(previous <= current){
            curTotal++;
            if(spcs < curTotal)// never need to look back
              spcs = curTotal; // always the largest
          }//end if
          else{ //break occurs
            curTotal = 1; //reset counter
          }//end else
          cur++; //increment index
          previous = current;
        }//end while
      }//end try
      catch (UsingIteratorPastEndException e){
        return(spcs); //this is how we do!
      }//end catch
    }//end else
    return 0;
  }//end lengthLongestNDCSS2()
}//end class JumbleUser
