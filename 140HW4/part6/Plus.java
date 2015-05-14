public class Plus{
  public static Seq plus (Constant x, Constant y){
    ConstantIt xi = new ConstantIt(x);
    ConstantIt yi = new ConstantIt(y);
    int count = 0;
    int sum = 0;
    while(xi.hasNext() && yi.hasNext()){
      try {
        sum = xi.next() + yi.next();
        count +=1;
      }//end try()
      catch (UsingIteratorPastEndException e) {}
    }//end while
    return new Constant(count,sum);
  }//end plus Constant()

  public static Seq plus (Delta x, Delta y){
    DeltaIt xi = new DeltaIt(x);
    DeltaIt yi = new DeltaIt(y);
    int count, new_inital, new_delta, x_start, y_start;
    count = new_inital = new_delta = x_start = y_start = 0;

    while(xi.hasNext() && yi.hasNext()){
      try {
        if (count == 0){
          x_start = xi.next();
          y_start = yi.next();
          new_inital = x_start + y_start;
          count += 1;
        }//end if
        else if( count == 1){
          new_delta = xi.next() - x_start;
          new_delta += yi.next() - y_start;
          count += 1;
        }//end elseif
        else{
          count +=1;
          xi.next();
          yi.next();
        }//end else
      }//end try
      catch (UsingIteratorPastEndException e) {}
    }//end while
    return new Delta(count,new_inital,new_delta);
  }
  
  public static Seq plus (Jumble x, Jumble y){
    JumbleIt xi = (JumbleIt)x.createSeqIt();
    JumbleIt yi = (JumbleIt)y.createSeqIt();
    JumbleIt xi2 = new JumbleIt(x);
    JumbleIt yi2 = new JumbleIt(y);

    int [] array;
    int count = 0;
    // first sweep to determine count
    while(xi.hasNext() && yi.hasNext()){
      try {
        xi.next(); //don't forget!
        yi.next(); //MOVING
        count +=1;
      }//end try
      catch (UsingIteratorPastEndException e) {}
    }//end while
    
    if(count == 0)
      return new Jumble(new int [] {});
    else{
      array = new int [count];
      // second sweep to add elements together
      count = 0; //reset count
      while(xi2.hasNext() && yi2.hasNext()){
        try {
          array[count] = xi2.next() + yi2.next();
          count +=1;
        }
        catch (UsingIteratorPastEndException e) {}
      }//end while
      return new Jumble(array);
    }//end else
  }//end plus() Jumble

}//end class Plus()
