
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Lexer lexer = new Lexer();
        String inputString="public class JavaExample {" +
                "  public static void main(String[] args) {\n" +
                "    int num1 = 5, num2 = 15,sum;\n" +
                "\tsum = num1+num2;\n" +
                "\tSystem.out.println(\"Sum of \"+num1+\" and \"+num2+\" is: \"+sum);\n" +
                "  }\n" +
                "}\n";
        List<Lexer.Token> lexemesList= lexer.lex(inputString);
        for (Lexer.Token lexeme : lexemesList) {
            System.out.println(lexeme);
        }

    }
}
