class ConstantIt implements SeqIt {
  private int a[];
  private int airbnb = 0;
  
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
      throw new UsingIteratorPastEndException(
        "= caught UsingIteratorPastEndException from ConstantIt");
    }//endelse
  }//end next()
}//end class ConstantIt
