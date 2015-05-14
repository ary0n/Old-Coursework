/* *** This file is given as part of the programming assignment. *** */
/*
ECS 140a
Ashton Yon, Raymond S. Chan, Giovanni Tenorio
Parser part 2
*/
public class Parser {

    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
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
        block();
    }

    private void block() {
		if(tok.kind == TK.VAR)
			declarations();
		statement_list();
    }
	
	
    private void declarations() {
        mustbe(TK.VAR);
        while( is(TK.ID) ) {
            scan();
        }
        mustbe(TK.RAV);
    }
	
	private void statement_list() {
		while( is(TK.ID) || is(TK.PRINT) || is(TK.IF) || is(TK.DO) || is(TK.FA))
			statement();
	}
	
	private void statement() {
		if(tok.kind == TK.ID)
			assignment();
		else if (tok.kind == TK.PRINT)
			print();
		else if (tok.kind == TK.IF)
			c_if();
		else if (tok.kind == TK.DO)
			c_do();
		else if (tok.kind == TK.FA)
			c_fa();
		else{
			parse_error("statement");
		}
	}		
	
	private void assignment(){
		mustbe(TK.ID);
		mustbe(TK.ASSIGN);
		expression();
	}
	
	private void print(){
		mustbe(TK.PRINT);
		expression();
	}
	
	private void c_if(){
		mustbe(TK.IF);
		guarded_commands();
		mustbe(TK.FI);
	}
	
	private void c_do(){
		mustbe(TK.DO);
		guarded_commands();
		mustbe(TK.OD);
	}
	
	private void c_fa(){
		mustbe(TK.FA);
		mustbe(TK.ID);
		mustbe(TK.ASSIGN);
		expression();
		mustbe(TK.TO);
		expression();
		if(tok.kind == TK.ST){
			scan();
			expression();
		}
		commands();
		mustbe(TK.AF);
		
	}
	
	private void guarded_commands() {
		guarded_command();
		while(is(TK.BOX)){
			scan();
			guarded_command();
		}
		
		if(tok.kind == TK.ELSE) {
			scan();
			commands();
		}
	
	}
	
	private void guarded_command(){
		expression();
		commands();
	}
	
	private void commands(){
		mustbe(TK.ARROW);
		block();
	}
	
	private void expression(){
		simple();
		if(is(TK.EQ) || is(TK.NE) || is(TK.LT) || is(TK.GT) || is(TK.LE) || is(TK.GE)){
			relop();
			simple();
		}
	}
	
	private void simple(){
		term();
		while(is(TK.MINUS) || is(TK.PLUS) )
		{
			addop();
			term();
		}
	}
	
	private void term(){
		factor();
		while(is(TK.TIMES) || is(TK.DIVIDE) )
		{
			multop();
			factor();
		}
	}
	
	private void factor(){
		if(tok.kind == TK.LPAREN){
			scan();
			expression();
			mustbe(TK.RPAREN);
		}
		else if (tok.kind == TK.ID)
			scan();
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

    // is current token what we want?
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
}
