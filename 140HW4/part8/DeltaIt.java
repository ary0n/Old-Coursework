class DeltaIt implements SeqIt {

    private int a[];
    private int airbnb = 0;
    
    public DeltaIt(Delta d){
		  a = new int[d.num];
		  int initial = d.intial;
		  for(int i = 0; i < a.length; i++){
			  a[i] = initial;
			  initial = initial + d.delta;
		  }//end for
    }// end DeltaIt()
    
    public boolean hasNext(){
		  if(airbnb + 1 <= a.length)
			  return true;
		  else
			  return false;
    }//end hasNext()
    
    public int next() throws UsingIteratorPastEndException{
		  if(this.hasNext()){
			  airbnb++;
			  return(a[airbnb - 1]);
		  }//end if
		  else{
			  //System.err.println("DeltaIt called past end");
			  //System.exit(1);
        throw new UsingIteratorPastEndException("blah");
		  }//end else
		  //return 0;// never going to reach
    }//end next()
}//end class DeltaIt
