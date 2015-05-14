// Constant Class

public class Constant extends Seq{ 
  protected int num;
  protected int value;

  public Constant(int i_num, int i_value){
    num = i_num;
    if(i_num != 0)
      value = i_value;
    else
      value = 0;
  }//end Constant()
  
  public String toString(){
    return "[ " + num + " : " + value + " ]"; 
  }//end toString()

  public int min(){
    return value;
  }//end min()

}//end class Constant()
