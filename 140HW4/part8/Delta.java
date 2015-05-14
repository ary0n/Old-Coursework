// Constant Class


public class Delta extends Seq{
  
  protected int num;
  protected int intial;
  protected int delta;

  public Delta (int i_num, int i_intial, int i_delta){
    num = i_num;
    
    if(i_num != 0){
      intial = i_intial;
      delta = i_delta;
    }
    else{
      intial = 0;
      delta = 0;
    }
  }//end Delta()
  
  // should these not be public?
  public String toString(){
    return "< " + num + " : " + intial + " &" + delta + " >"; 
  }//end toString()
  
  public int min(){
    if (delta >=0)
      return intial;
    else
      return intial + ((num-1)*delta);
  }//end min()
 
  public SeqIt createSeqIt(){
    return new DeltaIt(this);
  }//end createSeqIt()

}//end Delta()
