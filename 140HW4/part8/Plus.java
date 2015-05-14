public class Plus{
  public static Seq plus(Seq x, Seq y){
    SeqIt xi = x.createSeqIt();
    SeqIt yi = y.createSeqIt();
    SeqIt xi2 = x.createSeqIt();
    SeqIt yi2 = y.createSeqIt();
    int count, new_inital, new_delta, x_start, y_start, old_new_delta, intel_x, intel_y;
    count = new_inital = new_delta = x_start = y_start = old_new_delta = intel_x = intel_y = 0;
    int [] array;
    boolean jFlag = false;
    
    while(xi.hasNext() && yi.hasNext()){
      try {
        if (count == 0){
          x_start = xi.next();
          y_start = yi.next();
          new_inital = x_start + y_start;
          count += 1;
        }//end if
        else if(count == 1){
          intel_x = xi.next();
          intel_y = yi.next();
          new_delta = intel_x - x_start;
          new_delta += intel_y - y_start;
          old_new_delta = new_delta;
          x_start = intel_x;
          y_start = intel_y;
          count += 1;
        }//end else
        else{
          intel_x = xi.next();
          intel_y = yi.next();
          new_delta = intel_x - x_start;
          new_delta += intel_y - y_start;
          x_start = intel_x;
          y_start = intel_y;
          if(old_new_delta != new_delta) //toggle flag
			      jFlag = true;
          count += 1;
        }//end else
      }//end try
      catch (UsingIteratorPastEndException e) {}
    }//end while
    
    if(jFlag == false){
		  if(new_delta == 0) // its a constant
			  return new Constant(count, new_inital);
		  else //its delta
			  return new Delta(count, new_inital, new_delta);
    }//end if jFlag == false
    else{//its jumble
	    if(count == 0)
        return new Constant(0,0);
	    else{ //count != 0
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
	    }//end else // count != 0
    }//end else its jumble
  }//end plus() Seq Seq

}//end class Plus()
