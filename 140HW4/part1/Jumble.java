//Jumble Class

public class Jumble extends Seq{
  protected int [] values;

  public Jumble (int [] i_values){
    values = new int [i_values.length];
    for (int i = 0; i < i_values.length; i++)
      values[i] = i_values[i];
  }//end Jumble()
  
  public String toString(){
    String ans =  "{ " + values.length + " : ";
    for (int j = 0; j < values.length; j++)
      ans  = ans + values[j] + " ";
    ans = ans + "}";
    return ans;
  }//end toString()

}//end class Jumble
