/* *** This file is given as part of the programming assignment. *** */
/*
ECS 140a
Ashton Yon, Raymond S. Chan, Giovanni Tenorio
Parser part 5
*/
import java.util.*;

public class Parser {

	public class Outer {
		int depth;
		public ArrayList <Token> row = new ArrayList <Token> ();
		boolean onTable;
	}
    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
	public ArrayList<Outer> hp = new ArrayList<Outer>(); //Global permanent variable list
	public ArrayList<String> workday = new ArrayList<String>();
	public int block_count = -1;
	public int hp_ptr = 0;
	
	                                    
	private void scan() {
        tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
		System.out.println("#include<stdlib.h>");
		System.out.println("#include<stdio.h>");
		System.out.println("int main(){");
        this.scanner = scanner;
        scan();
        program();
        if( tok.kind != TK.EOF )
            parse_error("junk after logical end of program");
		System.out.println("}");
		hp_printer();
    }

    private void program() {
		Stack <LinkedList <String>> symboltable = new Stack <LinkedList<String>>();
		block(symboltable);
    }

    private void block(Stack <LinkedList <String>> symboltable) {
		LinkedList <String> list = new LinkedList <String>();
		symboltable.push(list);
		boolean declared = false;
		block_count++; // Yon Corpus
		if(tok.kind == TK.VAR){
			declarations(symboltable);
			declared = true;
		}	
			
		statement_list(symboltable);
		
		symboltable.pop();
		block_count--;
		if (declared) {
			hp_ptr--;
			hp.get(hp.size()-1).onTable = false;
		}	
    }
	
    private void declarations(Stack <LinkedList <String>> symboltable) {
		LinkedList <String>templist = new LinkedList<String>();
		Outer tempOut = new Outer();
		templist = symboltable.pop();
        mustbe(TK.VAR);
        while( is(TK.ID) ) {
			if(!templist.contains(tok.string)){
				Token tok2 = new Token(tok.kind, tok.string, tok.lineNumber);
				templist.add(tok.string);
				tok2.string = tok.string;
				tok2.lineDeclared = tok.lineNumber;
				tok2.blockDepth = block_count;
				tempOut.depth = block_count;
				tempOut.row.add(tok2);
				System.out.println("int "+"x_"+tok.string+" = -12345;");
			}	
			else{
				redec_error();
			}
            scan();
        }
		tempOut.onTable = true;
		hp.add(tempOut);
		hp_ptr = hp.size()-1;
		symboltable.push(templist);
        mustbe(TK.RAV);
    }
	
	private void statement_list(Stack <LinkedList <String>> symboltable) {
		while( is(TK.ID) || is(TK.PRINT) || is(TK.IF) || is(TK.DO) || is(TK.FA))
			statement(symboltable);
	}
	
	private void statement(Stack <LinkedList <String>> symboltable) {
		if(tok.kind == TK.ID)
			assignment(symboltable);
		else if (tok.kind == TK.PRINT)
			print(symboltable);
		else if (tok.kind == TK.IF)
			c_if(symboltable);
		else if (tok.kind == TK.DO)
			c_do(symboltable);
		else if (tok.kind == TK.FA)
			c_fa(symboltable);
		else{
			parse_error("statement");
		}
	}		
	
	private void assignment(Stack <LinkedList <String>> symboltable){
		if( is(TK.ID))
		{
			check_table(symboltable);
			AddtoAssign();	
			System.out.println("x_"+tok.string);
			scan();
		}
		else
		{
			System.err.println( "mustbe: want " + TK.ID + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
			scan();
		}
		mustbe(TK.ASSIGN);
		System.out.println(" = ");
		expression(symboltable);
		System.out.println(";");
	}
	
	private void print(Stack <LinkedList <String>> symboltable){
		mustbe(TK.PRINT);
		System.out.print("printf(\"%d\\n\",");
		expression(symboltable);
		System.out.println(");");
	}
	private void c_if(Stack <LinkedList <String>> symboltable){
		mustbe(TK.IF);
		workday.add("if");
		System.out.println("if (");
		guarded_commands(symboltable);
		mustbe(TK.FI);
		workday.remove(workday.size()-1);
	}
	
	private void c_do(Stack <LinkedList <String>> symboltable){
		mustbe(TK.DO);
		workday.add("do");
		System.out.println("while (1) {");
		System.out.print("if (");
		guarded_commands(symboltable);
		mustbe(TK.OD);
		System.out.println("else { break; }");
		System.out.println("}");
		workday.remove(workday.size()-1);
	}
	
	private void c_fa(Stack <LinkedList <String>> symboltable){
		String variable = new String();
		mustbe(TK.FA); 
		System.out.println("for(");
		if( is(TK.ID))
		{
			check_table(symboltable);
			AddtoAssign();
			variable = "x_"+tok.string;
			System.out.println(variable);
			scan();
		}
		else
		{
			System.err.println( "mustbe: want " + TK.ID + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
			scan();
		}
		mustbe(TK.ASSIGN);
		System.out.println("=");
		expression(symboltable);
		mustbe(TK.TO);
		System.out.println(";");
		System.out.println(variable+ "<=");
		expression(symboltable);
		
		//Do we have to worry about ST?

		System.out.println(";");
		System.out.println(variable+"++ )");
		
		if(tok.kind == TK.ST){
			scan();
			System.out.print("if ( ");
			expression(symboltable);
			System.out.println("){");
			commands(symboltable);
			System.out.println("}");
			
		}
		else{
			commands(symboltable);
		}
		mustbe(TK.AF);
	
	}
	
	private void guarded_commands(Stack <LinkedList <String>> symboltable) {
		guarded_command(symboltable);
		while(is(TK.BOX)){
			System.out.println("else if (");
			scan();
			guarded_command(symboltable);
				
		}
		if(tok.kind == TK.ELSE) {
			scan();
			System.out.println("else");
			commands(symboltable);
		}
	}
	
	private void guarded_command(Stack <LinkedList <String>> symboltable){
		expression(symboltable);
		System.out.println(")");
		commands(symboltable);
	}
	
	private void commands(Stack <LinkedList <String>> symboltable){
		mustbe(TK.ARROW);
		System.out.print("{");
		block(symboltable);
		System.out.println("}");
	}
	
	private void expression(Stack <LinkedList <String>> symboltable){
		simple(symboltable);
		if(is(TK.EQ) || is(TK.NE) || is(TK.LT) || is(TK.GT) || is(TK.LE) || is(TK.GE)){
			relop();
			simple(symboltable);
		}
	}
	
	private void simple(Stack <LinkedList <String>> symboltable){
		term(symboltable);
		while(is(TK.MINUS) || is(TK.PLUS) ) //REMEMBER MINUS
		{
			addop();	
			term(symboltable);
		}
	}
	
	private void term(Stack <LinkedList <String>> symboltable){
		factor(symboltable);
		while(is(TK.TIMES) || is(TK.DIVIDE) ) // this is multop
		{
			multop();
			factor(symboltable);
		}
	}
	
	private void factor(Stack <LinkedList <String>> symboltable){
		if(tok.kind == TK.LPAREN){
			System.out.print("(");
			scan();
			expression(symboltable);
			mustbe(TK.RPAREN);
			System.out.println(")");
		}
		else if (tok.kind == TK.ID)
		{
			check_table(symboltable);
			AddtoUsed();
			System.out.print("x_"+tok.string);
			scan();
		}
			//scan(); // what do
		else if (tok.kind == TK.NUM)
		{
			System.out.println(tok.string);
			scan(); // what do
		}
		else{
			parse_error("factor");
		}
	}
	
	private void relop(){
		if (is (TK.EQ)){
			System.out.print("==");
			scan();
		}
		else if (is (TK.LT)){
			System.out.print("<");
			scan();
		}
		else if (is (TK.GT)){
			System.out.print(">");	
			scan();
		}
		else if (is (TK.NE)){
			System.out.print("!=");
			scan();
		}
		else if (is (TK.LE)){
			System.out.print("<=");
			scan();
		}
		else if (is (TK.GE)){
			System.out.print(">=");
			scan();
		}
		else{
			parse_error("relop");
		}
	}
  
	private void addop() {
		if (is(TK.MINUS)){
			System.out.print("-");
			scan();
		}
		else if (is(TK.PLUS)){
			System.out.print("+");
			scan();
		}
		else{
			parse_error("addop");
		}
	}
  
	private void multop() {
		if(is(TK.TIMES)){
			System.out.print("*");
			scan();
		}
		else if(is(TK.DIVIDE)){
			System.out.print("/");
			scan();
		}
		else{
			parse_error("multop");
		}
	}
 
    // you'll need to add a bunch of methods here

    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
        if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
        }
        scan();
    }

    private void parse_error(String msg) {
        System.err.println( "can't parse: line " + tok.lineNumber + " " + msg );
        System.exit(1);
    }
	
	private void redec_error(){
		System.err.println("variable "+tok.string+" is redeclared on line "+tok.lineNumber);
	}
	
	//Checks if variable is in the symbol table
	private void check_table(Stack <LinkedList <String>> symboltable)
	{
		Stack <LinkedList <String>> yelp_stack = new Stack <LinkedList <String>>();
		int count;
		LinkedList <String> tempy_list = new LinkedList <String>();
		while(!symboltable.empty()){
			tempy_list = symboltable.pop();
			if(tempy_list.contains(tok.string)){
				symboltable.push(tempy_list);
				break;
			}
			else{
				yelp_stack.push(tempy_list);
			}
		}
		if(symboltable.empty()){
			System.err.println("undeclared variable "+tok.string+" on line "+tok.lineNumber);
			System.exit(1);
		}
		else{
			while(!yelp_stack.empty()){
				symboltable.push(yelp_stack.pop()); //put everything back
			}
		}
	}
	
	//Prints the info for every declared variable
	private void hp_printer(){
		for(int i = 0; i < hp.size(); i++){
			for(int j = 0; j< (hp.get(i)).row.size(); j++){
				System.err.println(hp.get(i).row.get(j));
				if(hp.get(i).row.get(j).assignList.size() == 0){
					System.err.println("  never assigned");
				}
				else{
					Token temp2 = new Token(tok.kind,tok.string,tok.lineNumber);
					temp2 = hp.get(i).row.get(j);
					System.err.print("  assigned to on: ");
					for(int k = 0; k < temp2.assignList.size(); k++){
						if(temp2.timesAssigned.get(k) > 1 && k < temp2.assignList.size()-1)
							System.err.print(temp2.assignList.get(k)+"("+temp2.timesAssigned.get(k)+") ");
						else if (temp2.timesAssigned.get(k) > 1 && k == temp2.assignList.size() -1)
							System.err.print(temp2.assignList.get(k)+"("+temp2.timesAssigned.get(k)+")");
						else if (temp2.timesAssigned.get(k) == 1 && k == temp2.assignList.size() -1)
							System.err.print(temp2.assignList.get(k));
						else
							System.err.print(temp2.assignList.get(k)+" ");
					}
					System.err.print("\n");
				}
				//Print used
				if(hp.get(i).row.get(j).usedList.size() == 0){
					System.err.println("  never used");
				}
				else{
					Token temp2 = new Token(tok.kind,tok.string,tok.lineNumber);
					temp2 = hp.get(i).row.get(j);
					System.err.print("  used on: ");
					for(int k = 0; k < temp2.usedList.size(); k++){
						if(temp2.timesUsed.get(k) > 1 && k < temp2.usedList.size()-1){
							System.err.print(temp2.usedList.get(k)+"("+temp2.timesUsed.get(k)+") ");
						}
						else if (temp2.timesUsed.get(k) > 1 && k == temp2.usedList.size()-1)
							System.err.print(temp2.usedList.get(k)+"("+temp2.timesUsed.get(k)+")");
						else if (temp2.timesUsed.get(k) == 1 && k == temp2.usedList.size() -1)
							System.err.print(temp2.usedList.get(k));
						else //should be 1 item in temp list and its not the last one
							System.err.print(temp2.usedList.get(k)+" ");
					}
					System.err.print("\n");
				}
			}
		}
	}

	//Increments times assigned for a variable
	public void AddtoAssign(){
		boolean aFound = false;
		boolean endAssign = false;
		boolean firstTime = true;
		for(int i = (hp.size()-1); i >= 0; i--){
			if(hp.get(i).depth < hp.get(hp.size()-1).depth || firstTime == true){
				firstTime = false;
				for(int j = (hp.get(i).row.size()) - 1; j >= 0; j--){
					if(tok.string.equals(hp.get(i).row.get(j).string)){
						aFound = true;
						if(hp.get(i).row.get(j).assignList.size() == 0){
							hp.get(i).row.get(j).assignList.add(tok.lineNumber);
							hp.get(i).row.get(j).timesAssigned.add(1);
							break;
						}
						else{
							for(int k = 0; k < hp.get(i).row.get(j).assignList.size(); k++){
								if(hp.get(i).row.get(j).assignList.get(k) == tok.lineNumber){
									int num = hp.get(i).row.get(j).timesAssigned.get(k);
									num+=1;
									hp.get(i).row.get(j).timesAssigned.remove(k);
									hp.get(i).row.get(j).timesAssigned.add(k, num);
									endAssign = true; //found line number in assign list
									break;
								}
							}							
							if(endAssign == false){
								hp.get(i).row.get(j).assignList.add(tok.lineNumber);
								hp.get(i).row.get(j).timesAssigned.add(1);
							}
						}
					}
				}//end of j for loop
				if(aFound == true)
					break;
			}//end if
		}//end of i for loop
	}//end of AddtoAssign

	//Increments times used for a variable
	public void AddtoUsed(){
		boolean aFound = false;
		boolean endUsed = false;
		for(int i = (hp_ptr); i >= 0; i--){
			if(hp.get(i).onTable == true){
				//firstTime = false;
				for(int j = (hp.get(i).row.size()) - 1; j >= 0; j--){
					//Token temp = new Token(tok.kind,tok.string,tok.lineNumber);
					if(tok.string.equals( hp.get(i).row.get(j).string) && hp.get(i).row.get(j).blockDepth <= block_count){ //made strictly less than CARE
						aFound = true;
						if(hp.get(i).row.get(j).usedList.size() == 0){
							hp.get(i).row.get(j).usedList.add(tok.lineNumber);
							hp.get(i).row.get(j).timesUsed.add(1);
							break;
						}
						else{
							for(int k = 0; k < hp.get(i).row.get(j).usedList.size(); k++){
								if(hp.get(i).row.get(j).usedList.get(k) == tok.lineNumber){
									
									int num = hp.get(i).row.get(j).timesUsed.get(k);
									num+=1;
									hp.get(i).row.get(j).timesUsed.remove(k);
									hp.get(i).row.get(j).timesUsed.add(k, num);
									endUsed = true; //found line number in assign list
									break;
								}
							}	
							if(endUsed == false){
								hp.get(i).row.get(j).usedList.add(tok.lineNumber);
								hp.get(i).row.get(j).timesUsed.add(1);
							}	
							break;
						}
					}
				}//end of j for loop
				if(aFound == true)
					break;
			}//end if
		}//end of i for loop
	}//end of AddtoUsed
}

