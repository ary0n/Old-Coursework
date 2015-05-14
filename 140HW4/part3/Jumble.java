// Jumble Class

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

  public int min(){
    if (values.length == 0)
      return 0;
    int temp = values[0];
    for (int k = 1; k < values.length; k++)
      if (values[k] < temp)
        temp = values[k];
    return temp;
  }//end min()

}//end class Jumble
