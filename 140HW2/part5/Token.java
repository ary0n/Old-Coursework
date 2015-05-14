import java.util.*;




public class Token {
    public TK kind;
    public String string;
    public int lineNumber;
	public int lineDeclared;
	public int blockDepth;
    public Token(TK kind, String string, int lineNumber) {
        this.kind = kind;
        this.string = string;
        this.lineNumber = lineNumber;
    }
	
	public ArrayList <Integer> assignList = new ArrayList <Integer> ();
	public ArrayList <Integer> timesAssigned = new ArrayList <Integer> ();
	public ArrayList <Integer> usedList = new ArrayList <Integer> ();
	public ArrayList <Integer> timesUsed = new ArrayList <Integer> ();	
	
    public String toString() { // make it printable for debugging
        return string+"\n"+"  declared on line "+lineDeclared+" at nesting depth "+blockDepth;
    }
}
