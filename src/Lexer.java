
import java.util.*;

public class Lexer {
    public static enum Type {

        ADDITION('+'), SUBTRACTION('-'), MULTIPLICATION('*'),DIVISION('/'),NONOPERATOR(' '),EQUALS('='),;
        private final char symbol;

        Type(char symbol) {
            this.symbol=symbol;

        }
    }

    public static class Token {
        public final Type t;
        public final String c; // tokens

        // could have column and line number fields too, for reporting errors later
        public Token(Type t, String c) {
            this.t = t;
            this.c = c;
        }

        public String toString() {
            if (t == Type.NONOPERATOR) {
                return "lexeme<" + c + ">";
            }
            return t.toString();
        }
    }

    public static String getChar(String s, int i) {
        int j = i;
        while(j < s.length() ) {
            if((s.charAt(j)=='{'||s.charAt(j)=='}'||s.charAt(j)=='('||s.charAt(j)==')'||s.charAt(j)=='"' ||s.charAt(j)=='\''
                    ||s.charAt(j)=='['||s.charAt(j)==']'||s.charAt(j)==','||s.charAt(j)==';'||s.charAt(j)=='.'||s.charAt(j)==':')&&i==j) {
                return s.substring(j, j + 1);
            }else if (Character.isLetter(s.charAt(j))) {
                j++;
            }else if(Character.isDigit(s.charAt(j))) {
                j++;
            }
            else {
                return s.substring(i, j);
            }
        }
        return s.substring(i, j);
    }

    public static List<Token> lex(String input) {
        List<Token> result = new ArrayList<Token>();
        for (int i = 0; i < input.length(); ) {
            char inputChar=input.charAt(i);
            switch (inputChar) {
                case '+':
                    result.add(new Token(Type.ADDITION, "+"));
                    i++;
                    break;
                case '-':
                    result.add(new Token(Type.SUBTRACTION, "-"));
                    i++;

                case '*':
                    result.add(new Token(Type.MULTIPLICATION, "*"));
                    i++;
                    break;
                case '/':
                    result.add(new Token(Type.DIVISION, "/"));
                    i++;
                    break;
                case '=':
                    result.add(new Token(Type.EQUALS, "/"));
                    i++;
                    break;
                default:
                    if (Character.isWhitespace(input.charAt(i))) {
                        i++;
                    } else {
                        String lexeme = getChar(input, i);
                        i += lexeme.length();
                        result.add(new Token(Type.NONOPERATOR, lexeme));
                    }
                    break;
            }
        }
        return result;
    }
}