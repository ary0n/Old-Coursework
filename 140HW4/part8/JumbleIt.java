class JumbleIt implements SeqIt {

    private int a[];
    private int airbnb = 0;
    
    public JumbleIt(Jumble j){
		  //Is this ok with Olson? reference?
		  a = j.values;
    }//end JumbleIt()
    
    public boolean hasNext(){
		  if(airbnb+1 <= a.length)
			  return true;
		  else
			  return false;
    }//emd hasNext()
    
    public int next() throws UsingIteratorPastEndException{
		  if(this.hasNext()){
			  airbnb++;
			  return(a[airbnb - 1]);
		  }//end if
		  else{
			  //System.err.println("JumbleIt called past end");
			  //System.exit(1);
        throw new UsingIteratorPastEndException("blah");
		  }//end else
		  //return 0;// never going to reach
    }//end next()
}//end class JumbleIt
