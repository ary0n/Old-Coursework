/* *** This file is given as part of the programming assignment. *** */
/*
ECS 140a
Ashton Yon, Raymond S. Chan, Giovanni Tenorio
Parser part 3
*/

import java.util.*;

public class Parser {
    private Token tok; // the current token
    private void scan() {
        tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
        this.scanner = scanner;
        scan();
        program();
        if( tok.kind != TK.EOF )
            parse_error("junk after logical end of program");
    }

    private void program() {
		Stack <LinkedList <String>> symboltable = new Stack <LinkedList<String>>();
		block(symboltable);
    }

    private void block(Stack <LinkedList <String>> symboltable) {
		LinkedList <String> list = new LinkedList <String>();
		symboltable.push(list);
		if(tok.kind == TK.VAR)
			declarations(symboltable);
		statement_list(symboltable);
		
		symboltable.pop();
    }
	
    private void declarations(Stack <LinkedList <String>> symboltable) {
		LinkedList <String>templist = new LinkedList<String>();
		templist = symboltable.pop();
        mustbe(TK.VAR);
        while( is(TK.ID) ) {
			if(!templist.contains(tok.string))
				templist.add(tok.string);
			else{
				redec_error();
			}
            scan();
        }
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
		expression(symboltable);
	}
	
	private void print(Stack <LinkedList <String>> symboltable){
		mustbe(TK.PRINT);
		expression(symboltable);
	}
	
	private void c_if(Stack <LinkedList <String>> symboltable){
		mustbe(TK.IF);
		guarded_commands(symboltable);
		mustbe(TK.FI);
	}
	
	private void c_do(Stack <LinkedList <String>> symboltable){
		mustbe(TK.DO);
		guarded_commands(symboltable);
		mustbe(TK.OD);
	}
	
	private void c_fa(Stack <LinkedList <String>> symboltable){
		mustbe(TK.FA); 
		if( is(TK.ID))
		{
			check_table(symboltable);
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
		expression(symboltable);
		mustbe(TK.TO);
		expression(symboltable);
		
		if(tok.kind == TK.ST){
			scan();
			expression(symboltable);
		}
	
		commands(symboltable);
		mustbe(TK.AF);
		
	}
	
	private void guarded_commands(Stack <LinkedList <String>> symboltable) {
		guarded_command(symboltable);
		while(is(TK.BOX)){
			scan();
			guarded_command(symboltable);
			
		}
		
		if(tok.kind == TK.ELSE) {
			scan();
			commands(symboltable);
		}
	}
	
	private void guarded_command(Stack <LinkedList <String>> symboltable){
		expression(symboltable);
		commands(symboltable);
	}
	
	private void commands(Stack <LinkedList <String>> symboltable){
		mustbe(TK.ARROW);
		block(symboltable);
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
		while(is(TK.MINUS) || is(TK.PLUS) )
		{
			addop();
			
			term(symboltable);
		}
	}
	
	private void term(Stack <LinkedList <String>> symboltable){
		factor(symboltable);
		while(is(TK.TIMES) || is(TK.DIVIDE) )
		{
			multop();
			factor(symboltable);
		}
	}
	
	private void factor(Stack <LinkedList <String>> symboltable){
		if(tok.kind == TK.LPAREN){
			scan();
			expression(symboltable);
			mustbe(TK.RPAREN);
		}
		else if (tok.kind == TK.ID)
		{
			check_table(symboltable);
			scan();
		}
		else if (tok.kind == TK.NUM)
			scan();
		else{
			parse_error("factor");
		}
	}
	
	private void relop(){
		if (is (TK.EQ))
			scan();
		else if (is (TK.LT))
			scan();
		else if (is (TK.GT))
			scan();
		else if (is (TK.NE))
			scan();
		else if (is (TK.LE))
			scan();
		else if (is (TK.GE))
			scan();
		else{
				parse_error("relop");
		}
	}
  
	private void addop() {
		if (is(TK.MINUS))
			scan();
		else if (is(TK.PLUS))
			scan();
		else{
			parse_error("addop");
		}
	}
  
	private void multop() {
		if(is(TK.TIMES))
			scan();
		else if(is(TK.DIVIDE))
			scan();
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
        System.err.println( "can't parse: line "
                            + tok.lineNumber + " " + msg );
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
			System.out.println("undeclared variable "+tok.string+" on line "+tok.lineNumber);
			System.exit(1);
		}
		else{
			while(!yelp_stack.empty()){
				symboltable.push(yelp_stack.pop());
			}
		}
	}
}
