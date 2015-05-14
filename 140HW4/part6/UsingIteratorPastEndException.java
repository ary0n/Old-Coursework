class UsingIteratorPastEndException extends Exception{

  //prevents warnings from javac
  static final long serialVersionUID = 98L; //Olsson's said any value works

  //mirror Lecture Notes 9's example code
  public UsingIteratorPastEndException(String msg){
    super(msg); //calls constructor in base class
  }

}//end class Using...Exception
