public class RecursiveDescentParser {
    private final String[] tokens;
    private int currentIndex;
    private String variable;
    private String[] currentExpression=new String[3];

    public RecursiveDescentParser(String input) {
        tokens = input.split("\\s+");
        currentIndex = 0;
    }

    private String currentToken() {
        return currentIndex < tokens.length ? tokens[currentIndex] : null;
    }

    private void consume(String expected) throws Exception {
        if (expected.equals(currentToken())) {
            currentIndex++;
        } else {
            throw new Exception("Expected: " + expected + " but found: " + currentToken());
        }
    }
    private void consume(String[] expected) throws Exception {
         if (tokens[currentIndex + 1].equals("<") || tokens[currentIndex + 1].equals(">")//checks if expression is a boolean expression after current index
                    || tokens[currentIndex + 1].equals("<=") || tokens[currentIndex + 1].equals(">=")
                    || tokens[currentIndex + 1].equals("==") || tokens[currentIndex + 1].equals("!=") ||
                    tokens[currentIndex + 1].equals("=")) {
                for (int i = 0; i < 3; i++) {
                    currentExpression[i] = tokens[currentIndex + i];
                }
            }
        currentIndex=currentIndex+3;
        if (!(expected[1].equals("<") || expected[1].equals(">")||expected[1].equals("<=")||expected[1].equals(">=")||expected[1].equals("=="))){
                throw new Exception("Expected: " + expected[1] + " but found: " + tokens[currentIndex+1]);

        }
    }
    public void parseIfStmt() throws Exception {// checks for if statements within string,  will compare to initai
        consume("if");
        consume(currentExpression);


        if (currentToken() != null && currentToken().equals("elif")) {
            parseElifStmt();
        } else if (currentToken() != null && currentToken().equals("else")) {
            parseElseBlock();
        }
    }

    public void parseElifStmt() throws Exception {
        consume("elif");


        if (currentToken() != null && currentToken().equals("elif")) {
            parseElifStmt();
        } else if (currentToken() != null && currentToken().equals("else")) {
            parseElseBlock();
        }
    }

    public void parseElseBlock() throws Exception {
        consume("else:");

    }
    private void parsePrint(){
        Boolean foundClose=true;
        while (foundClose){
            if (currentToken().endsWith(")")){
                foundClose=false;
                currentIndex++;
            }
            else{
                currentIndex++;
            }
        }
    }
    private void parse()throws Exception{
        String[] expression=new String[3];
        if (tokens[currentIndex+1].equals("<")||tokens[currentIndex+1].equals(">")//checks if expression is a boolean expression after current index
                ||tokens[currentIndex+1].equals("<=")||tokens[currentIndex+1].equals(">=")
                ||tokens[currentIndex+1].equals("==")||tokens[currentIndex+1].equals("!=")||
                tokens[currentIndex+1].equals("=")) {
            for(int i=0;i<3;i++) {
                expression[i]=tokens[currentIndex+i];
            }

            if(!expression[1].equals("=")) evaluateExpression(expression);
            else variable=tokens[currentIndex+2];
            currentIndex=currentIndex+3;
        }
        else if(currentToken().equals("if")){
                parseIfStmt();
            }
        else if(currentToken().startsWith("else")){
                parseElseBlock();
            }
        else if(currentToken().equals("elif")){
                parseElifStmt();
            }
        else if(currentToken().startsWith("print(")){//will consider print to be capable of printing anything past the parenthesis for the token
                parsePrint();
            }
        else{
            throw new Exception("Expected: method but found: " + tokens[currentIndex]);
        }

        if(currentIndex<tokens.length-1){
            parse();
        }

        return;
    }
    private void evaluateExpression(String[] expression) throws Exception {

        return;
    }

    public static void main(String[] args) {
        try {
            RecursiveDescentParser parser = new RecursiveDescentParser("x = 10\n" +
                    "\n" +
                    "if x < 0:\n" +
                    "    prin>t(\"x is negative\")\n" +
                    "elif x == 0:\n" +
                    "    print(\"x is zero\")\n" +
                    "else:\n" +
                    "    print(\"x is positive\")\n");
            parser.parse();
            System.out.println("Parsing successful!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}