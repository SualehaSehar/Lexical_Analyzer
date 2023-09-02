package lexical_analyzer;
/**
 *
 * @author Sualeha
 */
public class Token {
    private String classPart;
    private String valuePart;
    private int lineNo;

    public Token(String classPart, String valuePart, int lineNo) {
        this.classPart = classPart;
        this.valuePart = valuePart;
        this.lineNo = lineNo;
    }

    public String getClassPart() {
        return classPart;
    }

    public void setClassPart(String classPart) {
        this.classPart = classPart;
    }

    public String getValuePart() {
        return valuePart;
    }

    public void setValuePart(String valuePart) {
        this.valuePart = valuePart;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    @Override
    public String toString() {
        return "("+ classPart + "," + valuePart + ","+ lineNo +')';
    }
    
    
}

