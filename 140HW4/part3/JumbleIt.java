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
  
  public int next(){
    if(this.hasNext()){
      airbnb++;
      return(a[airbnb - 1]);
    }//end if
    else{
      System.err.println("JumbleIt called past end");
      System.exit(1);
    }//end else
    return 0;// never going to reach
  }//end next()

}//end class JumbleIt
