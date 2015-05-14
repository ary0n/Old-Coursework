class ConstantIt implements SeqIt {

    private int a[];
    private int airbnb = 0;
    
    //c.num is protected, need to check??
    public ConstantIt(Constant c){
		  a = new int[c.num];
		  for(int i = 0; i < a.length; i++)
			  a[i] = c.value;
    }//end Constant()
    
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
			  //System.err.println("ConstantIt called past end");
			  //System.exit(1);
        throw new UsingIteratorPastEndException(
          "= caught UsingIteratorPastEndException from ConstantIt");
		  }//endelse
		  //return 0;// never going to reach
    }//end next()
}//end class ConstantIt
