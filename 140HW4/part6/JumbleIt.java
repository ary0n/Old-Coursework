class JumbleIt implements SeqIt {
  private int a[];
  private int airbnb = 0;
  
  public JumbleIt(Jumble j){
    a = j.values;
  }//end JumbleIt()
  
  public boolean hasNext(){
    if(airbnb+1 <= a.length)
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
      throw new UsingIteratorPastEndException("blah");
    }//end else
  }//end next()
}//end class JumbleIt
