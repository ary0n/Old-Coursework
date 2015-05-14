class JumbleUser{
	public static int lengthLongestNDCSS1(Jumble j){
		JumbleIt jumIt = new JumbleIt(j);
		JumbleIt jIt = new JumbleIt(j);
		int spcs = 1;
		int curTotal = 1;
		int cur;
		int length = 0;
		
		while(jIt.hasNext()){
			jIt.next();
			length++;
		}
		
		if(length == 0)
			return(0);
		else
			cur = 0;

		jumIt.next();// begins comparision with 2nd element

		if(length == 2){// handle 2 elements seperately
			JumbleIt jIt2 = new JumbleIt(j);
			int first = jIt2.next();
			int second = jIt2.next();
			if(first <= second)
				return(2);
			else
				return(1);
		}//end if
		else{//arrays with > 2 elements
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
		}//end else
  }//end lengthLongestNDCSS1()

}//end class JumbleUser
