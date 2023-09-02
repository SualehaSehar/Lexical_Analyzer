package lexical_analyzer.helper;

import java.util.List;
import lexical_analyzer.Token;

/**
 *
 * @author Sualeha
 */
public class SyntaxHelper {

    List<Token> TS;
    static int i;

    public SyntaxHelper(List<Token> TS) {
        this.TS = TS;
        this.i = 0;
    }

    public void analyseSyntax() {
        if (this.validate()) {
            System.out.println("We are good!");
        } else {
            System.out.println("Ivalid syntax at line " + TS.get(i).getLineNo() + "   " + TS.get(i).getValuePart());
        }
    }

    public boolean validate() {
        String s = TS.get(i).getClassPart();
        if (S()) {
            if (s().equals("$")) {
                return true;
            }
        }
        return false;
    }

    private String s() {
        String ss = TS.get(i).getClassPart();
        return ss;
    }

    private boolean S() {
        //first write condition for first of this NT
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (s().equals("final") || s().equals("class")) {          // if word is  final
                if (finalT()) {               // non terminal
                    if (struct()) {           // non terminal
                        if (s().equals("{")) {  //terminal, so inc i
                            i++;
                            if (CB()) {
                                return true;

                            }
                        }
                    }
                }
            } else if (s().equals("abstract")) {  //if word is  abstract //terminal so inc i
                i++;
                if (struct()) {           // non terminal
                    if (s().equals("{")) {  //terminal, so inc i
                        i++;
                        if (ACB()) {
                            return true;
                        }
                    }
                }

            }

        }

        return false;

    }

    private boolean SCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (S()) {
                return true;
            }
        } else if (s().equals("}") || s().equals("$")) {
            return true;
        }

        return false;
    }

    private boolean finalT() {
        String s = TS.get(i).getClassPart(); // String s = TS.get(i).getClassPart();  get class part
        if (s().equals("final")) { //first of current NT
            i++;
            return true;

        } else if (s().equals("class")) {
            return true;
        }
        return false;
    }

    private boolean struct() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("class")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                i++;
                if (inherit()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean CB() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                i++;
                if (SCompliment()) {
                    return true;
                }
            } else if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (CBB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ACB() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID") || s().equals("abstract")) { //first of current NT
            if (s().equals("}")) {
                i++;
                if (SCompliment()) {
                    return true;
                }
            } else if (s().equals("abstract") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (ACBB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean inherit() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("inherit")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                i++;
                if (lst()) {
                    return true;
                }
            }
        } else if (s().equals("{")) {
            return true;
        }
        return false;
    }

    private boolean lst() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals(",")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                i++;
                if (lst()) {
                    return true;
                }
            }
        } else if (s().equals("{")) {
            return true;
        }
        return false;
    }

    private boolean CBB() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                i++;
                if (d1()) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                i++;
                if (d6()) {
                    return true;
                }
            } else if (s().equals("final")) {
                i++;
                if (d3()) {
                    return true;
                }
            } else if (s().equals("static")) {
                i++;
                if (f()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (f()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ACBB() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("abstract") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                i++;
                if (dd1()) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                i++;
                if (dd6()) {
                    return true;
                }
            } else if (s().equals("final")) {
                i++;
                if (dd3()) {
                    return true;
                }
            } else if (s().equals("static")) {
                i++;
                if (fACB()) {
                    return true;
                }
            } else if (s().equals("abstract")) {
                if (abs_func()) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fACB()) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean d1() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (d3()) {
                    return true;
                }
            } else if (s().equals("static")) {
                if (d2()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (f()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d6() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (d3()) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (d3()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d3() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                i++;
                if (f()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (f()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean f() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (fn_d()) {
                    if (CB()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (CB()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (CB()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dd1() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (dd3()) {
                    return true;
                }
            } else if (s().equals("static")) {
                if (dd2()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fACB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd6() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (dd3()) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (dd3()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd3() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                i++;
                if (fACB()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fACB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fACB() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (fn_d()) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (ACB()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean ACBCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("abstract") || s().equals("AM") || s().equals("final")
                || s().equals("static") || s().equals("void") || s().equals("DT")
                || s().equals("ID") || s().equals("}")) { //first of current NT
            if (s().equals("}")) {
                i++;
                return true;
            } else {
                if (ACBB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d2() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static")) { //first of current NT
            i++;
            if (d4()) {
                return true;
            }
        }
        return false;
    }

    private boolean d4() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (d5()) {
                    return true;
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (CB()) {

                        return true;

                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (CB()) {

                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean d5() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("func") || s().equals("main")) { //first of current NT
            if (s().equals("func")) {
                if (fn_d()) {
                    if (CB()) {
                        return true;
                    }
                }
            } else if (s().equals("main")) {
                if (Main()) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                i++;
                if (def()) {
                    return true;
                }
            } else if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (dddCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean f2() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("ID") || s().equals("func") || s().equals("[")) { //first of current NT
            if (s().equals("ID")) {
                i++;
                if (dec()) {
                    return true;
                }
            } else if (s().equals("[") || s().equals("func")) {
                if (b()) {
                    if (fn_d()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean CBCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID") || s().equals("}")) { //first of current NT
            if (s().equals("}")) {
                i++;
                return true;
            } else {
                if (CBB()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean f1() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("ID") || s().equals("func") || s().equals("[") || s().equals("(")) { //first of current NT
            if (s().equals("ID")) {
                i++;
                if (dec()) {
                    return true;
                }
            } else if (s().equals("[") || s().equals("func")) {
                if (b()) {
                    if (fn_d()) {
                        return true;
                    }
                }
            } else if (s().equals("(")) {
                if (constructor()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dddCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                i++;
                if (d6Compliment()) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                i++;
                if (d6Compliment()) {
                    return true;
                }
            } else if (s().equals("final")) {
                i++;
                if (d3Compliment()) {
                    return true;
                }
            } else if (s().equals("static")) {
                i++;
                if (fCompliment()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean Main() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("main")) { //first of current NT
            i++;
            if (s().equals("(")) {
                i++;
                if (s().equals(")")) {
                    i++;
                    if (s().equals("{")) {
                        i++;
                        if (MST()) {
                            if (s().equals("}")) {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean def() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (class_def()) {
                if (def()) {
                    return true;
                }
            }
        } else if (s().equals("}") || s().equals("$")) {
            return true;
        }
        return false;
    }

    private boolean d6Compliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (d3Compliment()) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (d3Compliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d3Compliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                i++;
                if (fCompliment()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (fn_d()) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dd2() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static")) { //first of current NT
            i++;
            if (dd4()) {
                return true;
            }
        }
        return false;
    }

    private boolean dd4() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (dd5()) {
                    return true;
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (ACB()) {
                        return true;
                    }

                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (ACB()) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    private boolean dd5() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("func") || s().equals("main")) { //first of current NT
            if (s().equals("func")) {
                if (fn_d()) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("main")) {
                if (Main()) {
                    if (ddCompliment()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean ddCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("abstract") || s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                i++;
                if (def()) {
                    return true;
                }
            } else if (s().equals("abstract") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (ddddCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ddddCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("abstract") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("abstract")) {
                if (abs_func()) {
                    if (ddCompliment()) {
                        if (def()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                i++;
                if (dd6Compliment()) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                i++;
                if (dd6Compliment()) {
                    return true;
                }
            } else if (s().equals("final")) {
                i++;
                if (dd3Compliment()) {
                    return true;
                }
            } else if (s().equals("static")) {
                i++;
                if (fACBCompliment()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fACBCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fACBCompliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (fn_d()) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            } else if (s().equals("DT")) {
                i++;
                if (f2()) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (f1()) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean dd3Compliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                i++;
                if (fACBCompliment()) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (fACBCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd6Compliment() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                i++;
                if (dd3Compliment()) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                if (dd3Compliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean class_def() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (s().equals("final") || s().equals("class")) {
                if (finalT()) {
                    if (struct()) {
                        if (s().equals("{")) {
                            i++;
                            if (dCompliment()) {
                                return true;
                            }
                        }
                    }
                }
            } else if (s().equals("abstract")) {
                i++;
                if (struct()) {
                    if (s().equals("{")) {
                        i++;
                        if (ddCompliment()) {
                            return true;
                        }
                    }
                }

            }

        }

        return false;
    }

    private boolean fn_d() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("func")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                i++;
                if (s().equals("(")) {
                    i++;
                    if (ar_dec()) {
                        if (s().equals(")")) {
                            i++;
                            if (s().equals("{")) {
                                i++;
                                if (MST()) {
                                    if (s().equals("}")) {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean b() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                if (b1()) {
                    return true;
                }
            }
        } else if (s().equals("func")) {
            return true;
        }

        return false;
    }

    private boolean b1() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                if (b2()) {
                    return true;
                }
            }
        } else if (s().equals("func")) {
            return true;
        }

        return false;
    }

    private boolean b2() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                return true;
            }
        } else if (s().equals("func")) {
            return true;
        }

        return false;
    }

    private boolean ar_dec() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("DT")) {
                i++;
                if (bb()) {
                    if (s().equals("ID")) {
                        i++;

                        if (decc()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (bb()) {
                    if (s().equals("ID")) {
                        i++;

                        if (decc()) {
                            return true;
                        }
                    }
                }
            }
        } else if (s().equals(")")) {
            return true;
        }

        return false;
    }

    private boolean decc() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals(",")) {
            i++;
            if (q()) {
                return true;
            }
        } else if (s().equals(")")) {
            return true;
        }

        return false;
    }

    private boolean bb() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                if (bb1()) {
                    return true;
                }
            }
        } else if (s().equals("ID")) {
            return true;
        }

        return false;
    }

    private boolean bb1() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                if (bb2()) {
                    return true;
                }
            }
        } else if (s().equals("ID")) {
            return true;
        }

        return false;
    }

    private boolean bb2() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("[")) { //first of current NT
            i++;
            if (s().equals("]")) {
                i++;
                return true;
            }
        } else if (s().equals("ID")) {
            return true;
        }

        return false;
    }

    private boolean q() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("DT")) {
                i++;
                if (bb()) {
                    if (s().equals("ID")) {
                        i++;

                        if (decc()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (bb()) {
                    if (s().equals("ID")) {
                        i++;

                        if (decc()) {
                            return true;
                        }
                    }
                }
            }
        } else if (s().equals(")")) {
            return true;
        }

        return false;
    }

    private boolean abs_func() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("abstract")) { //first of current NT
            i++;
            if (A()) {
                if (Ret_type()) {
                    if (s().equals("func")) {
                        i++;
                        if (s().equals("ID)")) {
                            i++;
                            if (s().equals("(")) {
                                i++;
                                if (ar_dec()) {
                                    if (s().equals(")")) {
                                        i++;
                                        if (s().equals(";")) {
                                            i++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean A() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) { //first of current NT
            i++;
            return true;
        } else if (s().equals("void") || s().equals("ID") || s().equals("DT")) {
            return true;
        }
        return false;
    }

    private boolean Ret_type() {
        String s = TS.get(i).getClassPart();  // get class part
        if (s().equals("DT") || s().equals("ID") || s().equals("void")) { //first of current NT
            if (s().equals("DT")) {
                i++;
                if (b()) {
                    return true;
                }
            } else if (s().equals("ID")) {
                i++;
                if (b()) {
                    return true;
                }
            } else if (s().equals("void")) {
                i++;
                return true;
            }
        }

        return false;
    }

    private boolean As_fn() {
        String s = TS.get(i).getClassPart();
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("(")) {
                i++;
                if (exp_d()) {
                    if (s().equals(")")) {
                        i++;
                        if (h()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("=")) {
                if (assign()) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (exp()) {
                    if (s().equals("]")) {
                        i++;
                        if (more()) {
                            if (t()) {
                                return true;
                            }
                        }
                    }

                }
            } else if (s().equals(".")) {
                if (New()) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean t() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=") || s().equals(".")) {
            if (s().equals("=")) {
                i++;
                if (init()) {
                    return true;
                }
            }
            if (s().equals(".")) {
                if (New()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean New() {
        String s = TS.get(i).getClassPart();
        if (s().equals(".")) {
            i++;
            if (NewCompliment()) {
                return true;
            }
        }
        return false;
    }

    private boolean NewCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("ID") || s().equals("new")) {
            if (s().equals("ID")) {
                i++;
                if (As_fn()) {
                    return true;
                }
            } else if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (New()) {
                                    return true;
                                }
                            }
                        }

                    }
                }

            }
        }
        return false;
    }

    private boolean more2() {
        String s = TS.get(i).getClassPart();
        if (s().equals("[")) {
            i++;
            if (exp()) {
                if (s().equals("]")) {
                    i++;
                    return true;
                }
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",")
                || s().equals(".") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean more() {
        String s = TS.get(i).getClassPart();
        if (s().equals("[")) {
            i++;
            if (exp()) {
                if (s().equals("]")) {
                    i++;
                    if (more2()) {
                        return true;
                    }
                }

            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",")
                || s().equals(".") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean exp() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (AE()) {
                if (expCompliment()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean expCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("LOL")) {
            i++;
            if (AE()) {
                if (expCompliment()) {
                    return true;
                }
            }
        } else if (s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean AE() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (RE()) {

                if (AECompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean AECompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("LOH")) {
            i++;
            if (RE()) {
                if (AECompliment()) {
                    return true;
                }
            }
        } else if (s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean assign() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=")) {
            i++;
            if (init()) {
                return true;
            }
        }
        return false;
    }

    private boolean RE() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (E()) {
                if (RECompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean RECompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("RO")) {
            i++;
            if (E()) {
                if (RECompliment()) {
                    return true;
                }
            }
        } else if (s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean E() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (T()) {
                if (ECompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ECompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("PM")) {
            i++;
            if (T()) {
                if (ECompliment()) {
                    return true;
                }
            }
        } else if (s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean T() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (F()) {
                if (TCompliment()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean TCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("MDM")) {
            i++;
            if (F()) {
                if (TCompliment()) {
                    return true;
                }

            }
        } else if (s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean F() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (s().equals("this") || s().equals("super") || s().equals("ID")) {
                if (TS()) {
                    if (s().equals("ID")) {
                        i++;
                        if (o()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("int_const")
                    || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                    || s().equals("str_const")) {
                if (Const()) {
                    return true;
                }
            } else if (s().equals("(")) {
                i++;
                if (exp()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean TS() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super")) {
            i++;
            if (s().equals(".")) {
                i++;
                return true;
            }
        } else if (s().equals("ID")) {
            return true;
        }
        return false;
    }

    private boolean o() {
        String s = TS.get(i).getClassPart();
        if (s().equals("(") || s().equals("=") || s().equals(".") || s().equals("[")) {
            if (RHP()) {
                return true;
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }

        return false;
    }

    private boolean BreakT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("break")) {
            i++;
            if (s().equals(";")) {
                i++;
                return true;
            }
        }
        return false;
    }

    private boolean ContinueT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("continue")) {
            i++;
            if (s().equals(";")) {
                i++;
                return true;
            }
        }
        return false;
    }

    private boolean ReturnT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("return")) {
            i++;
            if (oo()) {
                return true;
            }
        }
        return false;
    }

    private boolean oo() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (exp()) {
                if (s().equals(";")) {
                    i++;
                    return true;
                }
            }
        } else if (s().equals(";")) {
            return true;
        }
        return false;
    }

    private boolean Try_CatchT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("try")) {
            i++;
            if (s().equals("{")) {
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        i++;
                        if (CatchT()) {
                            if (FinallyT()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean FinallyT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("finally")) {
            i++;
            if (s().equals("{")) {
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            }
        } else if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("}") || s().equals("case") || s().equals(":")
                || s().equals("default") || s().equals("else")) {
            return true;
        }
        return false;
    }

    private boolean CatchT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("catch")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (s().equals("ID")) {
                        i++;
                        if (s().equals(")")) {
                            i++;
                            if (s().equals("{")) {
                                i++;
                                if (MST()) {
                                    if (s().equals("}")) {
                                        i++;
                                        if (CatchT()) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else if (s().equals("finally") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("}") || s().equals("case") || s().equals(":")
                || s().equals("default") || s().equals("else")) {
            return true;
        }
        return false;
    }

    private boolean loop() {
        String s = TS.get(i).getClassPart();
        if (s().equals("loop")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (exp()) {
                    if (s().equals(")")) {
                        i++;
                        if (body()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean body() {
        String s = TS.get(i).getClassPart();
        if (s().equals(";") || s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new")) {
            if (s().equals(";")) {
                i++;
                return true;
            } else if (s().equals("{")) {
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            } else {
                if (SST()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean If_ElseT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("if")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (exp()) {
                    if (s().equals(")")) {
                        i++;
                        if (body()) {
                            if (ElseT()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean ElseT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("else")) {
            i++;
            if (body()) {
                return true;
            }
        } else if (s().equals(";") || s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("}") || s().equals("case") || s().equals(":")
                || s().equals("default") || s().equals("else")) {
            return true;
        }
        return false;
    }

    private boolean Switch_st() {
        String s = TS.get(i).getClassPart();
        if (s().equals("switch")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (exp()) {
                    if (s().equals(")")) {
                        i++;
                        if (s().equals("{")) {
                            i++;
                            if (p1()) {
                                if (s().equals("}")) {
                                    i++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean p1() {
        String s = TS.get(i).getClassPart();
        if (s().equals("case") || s().equals("default")) {
            if (s().equals("case")) {
                if (CaseT()) {
                    return true;
                }
            } else if (s().equals("default")) {
                if (DefaultT()) {
                    return true;
                }
            }

        } else if (s().equals("}")) {
            i++;
            return true;
        }
        return false;
    }

    private boolean CaseT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("case")) {
            i++;
            if (Const()) {
                if (s().equals(":")) {
                    i++;
                    if (LL()) {
                        if (p1()) {
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private boolean DefaultT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("default")) {
            i++;
            if (s().equals(":")) {
                i++;
                if (LL()) {
                    if (cse()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean const_MST() {
        String s = TS.get(i).getClassPart();
        if (s().equals("DT") || s().equals("ID") || s().equals("super") || s().equals("throw")
                || s().equals("this") || s().equals("break") || s().equals("continue") || s().equals("return")
                || s().equals("try") || s().equals("loop")
                || s().equals("if") || s().equals("switch") || s().equals("new")) {
            if (s().equals("super")) {
                if (superT()) {
                    if (MST()) {
                        return true;
                    }
                }
            } else if (s().equals("this")) {
                if (thisT()) {
                    if (MST()) {
                        return true;
                    }
                }
            } else {
                if (MST()) {
                    return true;
                }
            }
        } else if (s().equals("}")) {
            return true;
        }

        return false;
    }

    private boolean Const() {
        String s = TS.get(i).getClassPart();
        if (s().equals("int_const") || s().equals("flt_const") || s().equals("char_const")
                || s().equals("bool_const") || s().equals("str_const")) {
            i++;
            return true;
        }
        return false;
    }

    private boolean h() {
        String s = TS.get(i).getClassPart();
        if (s().equals(".")) {
            if (New()) {
                return true;
            }
        } else if (s().equals(";")||s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean RHP() {
        String s = TS.get(i).getClassPart();
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("(")) {
                i++;
                if (exp_d()) {
                    if (s().equals(")")) {
                        i++;
                        if (hCompliment()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("=")) {
                if (assign()) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (exp()) {
                    if (s().equals("]")) {
                        i++;
                        if (more()) {
                            if (tCompliment()) {
                                return true;
                            }
                        }
                    }

                }
            } else if (s().equals(".")) {
                if (RHPCompliment()) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean tCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=") || s().equals(".")) {
            if (s().equals("=")) {
                i++;
                if (init()) {
                    return true;
                }
            }
            if (s().equals(".")) {
                if (RHPCompliment()) {
                    return true;
                }
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;

        }
        return false;
    }

    private boolean hCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals(".")) {
            if (RHPCompliment()) {
                return true;
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;

        }
        return false;
    }

    private boolean RHPCompliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals(".")) {
            i++;
            if (ro()) {
                return true;
            }
        }
        return false;
    }

    private boolean ro() {
        String s = TS.get(i).getClassPart();
        if (s().equals("ID") || s().equals("new")) {
            if (s().equals("ID")) {
                i++;
                if (RR()) {
                    return true;
                }
            } else if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (RHPCompliment()) {
                                    return true;
                                }
                            }
                        }

                    }
                }

            }
        }
        return false;
    }

    private boolean RR() {
        String s = TS.get(i).getClassPart();
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (RHP()) {
                return true;
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean LL() {
        String s = TS.get(i).getClassPart();
        if (s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new")) {
            if (s().equals("{")) {
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            } else {
                if (MST()) {
                    return true;
                }
            }
        } else if (s().equals("case") || s().equals("default") || s().equals(":")) {
            return true;
        }
        return false;
    }

    private boolean cse() {
        String s = TS.get(i).getClassPart();
        if (s().equals("case")) {
            i++;
            if (Const()) {
                if (s().equals(":")) {
                    i++;
                    if (LL()) {
                        return true;
                    }
                }

            }
        } else if (s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean superT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("super")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (ar_dec()) {
                    if (s().equals(")")) {
                        i++;
                        if (s().equals(";")) {
                            i++;
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private boolean thisT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (ar_dec()) {
                    if (s().equals(")")) {
                        i++;
                        if (s().equals(";")) {
                            i++;
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    private boolean constructor() {
        String s = TS.get(i).getClassPart();
        if (s().equals("(")) {
            i++;
            if (ar_dec()) {
                if (s().equals(")")) {
                    i++;
                    if (s().equals("{")) {
                        i++;
                        if (const_MST()) {
                            if (s().equals("}")) {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    private boolean SST() {
        String s = TS.get(i).getClassPart();
        if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("return") || s().equals("new")) {
            if (s().equals("DT")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (dec()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                i++;
                if (for_fn()) {
                    return true;
                }
            } else if (s().equals("throw")) {
                if (throwT()) {
                    return true;
                }
            } else if (s().equals("this")) {
                i++;
                if (New()) {
                    if (s().equals(";")) {
                        i++;
                        return true;
                    }
                }
            } else if (s().equals("super")) {
                i++;
                if (New()) {
                    if (s().equals(";")) {
                        i++;
                        return true;
                    }
                }
            } else if (s().equals("break")) {
                if (BreakT()) {
                    return true;
                }
            } else if (s().equals("continue")) {
                if (ContinueT()) {
                    return true;
                }
            } else if (s().equals("return")) {
                if (ReturnT()) {
                    return true;
                }
            } else if (s().equals("try")) {
                if (Try_CatchT()) {
                    return true;
                }
            } else if (s().equals("loop")) {
                if (loop()) {
                    return true;
                }
            } else if (s().equals("if")) {
                if (If_ElseT()) {
                    return true;
                }
            } else if (s().equals("switch")) {
                if (Switch_st()) {
                    return true;
                }
            } else if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (New()) {
                                    if (s().equals(";")) {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }

        return false;
    }

    private boolean MST() {
        String s = TS.get(i).getClassPart();
        if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new")) {
            if (SST()) {
                if (MST()) {
                    return true;
                }
            }
        } else if (s().equals("}") || s().equals("case") || s().equals("default")
                || s().equals(":")) {
            return true;
        }
        return false;
    }

    private boolean for_fn() {
        String s = TS.get(i).getClassPart();
        if (s().equals("ID") || s().equals("instanceof") || s().equals("(")
                || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("ID")) {
                i++;
                if (dec()) {
                    return true;
                }
            } else if (s().equals("instanceof")) {
                if (inst_of()) {
                    return true;
                }
            } else if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
                if (As_fn()) {
                    if (s().equals(";")) {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean throwT() {
        String s = TS.get(i).getClassPart();
        if (s().equals("throw")) {
            i++;
            if (k()) {
                return true;
            }
        }
        return false;
    }

    private boolean inst_of() {
        String s = TS.get(i).getClassPart();
        if (s().equals("instanceof")) {
            i++;
            if (s().equals("ID")) {
                i++;
                if (s().equals(";")) {
                    i++;
                    return true;
                }
            }
        }
        return false;
    }

    private boolean k() {
        String s = TS.get(i).getClassPart();
        if (s().equals("ID")) {
            i++;
            if (s().equals(";")) {
                i++;
                return true;
            }
        } else if (s().equals("new")) {
            i++;
            if (s().equals("ID")) {
                i++;
                if (s().equals("(")) {
                    i++;
                    if (s().equals("str_const")) {
                        i++;
                        if (s().equals(")")) {
                            i++;
                            if (s().equals(";")) {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean dec() {
        String s = TS.get(i).getClassPart();
        if (s().equals("[") || s().equals("=") || s().equals(";")
                || s().equals(",")) {
            if (s().equals("[")) {
                i++;
                if (s().equals("]")) {
                    i++;
                    if (D()) {
                        if (list()) {
                            return true;
                        }
                    }
                }
            } else if ((s().equals(";")) || (s().equals(",")) || (s().equals("="))) {
                if (init_d()) {
                    if (list()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean init() {
        String s = TS.get(i).getClassPart();
        if (s().equals("new") || s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const")
                || s().equals("bool_const") || s().equals("str_const")) {
            if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (h()) {
                                    return true;
                                }
                            }
                        }

                    }
                }

            } else {
                if (exp()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean init_d() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=")) {
            i++;
            if (init()) {
                return true;
            }
        } else if ((s().equals(";")) || (s().equals(","))) {
            return true;

        }
        return false;
    }

    private boolean list() {
        String s = TS.get(i).getClassPart();
        if (s().equals(";") || s().equals(",")) {
            if (s().equals(";")) {
                i++;
                return true;
            } else if (s().equals(",")) {
                i++;
                if (s().equals("ID")) {
                    i++;
                    if (dec()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean D() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=") || s().equals("[")) {
            if (s().equals("=")) {
                if (init1()) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (s().equals("]")) {
                    i++;
                    if (TwoD()) {
                        return true;
                    }
                }

            }
        } else if (s().equals(";") || s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean TwoD() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=") || s().equals("[")) {
            if (s().equals("=")) {
                if (init2()) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (s().equals("]")) {
                    i++;
                    if (init3()) {
                        return true;
                    }
                }

            }
        } else if (s().equals(";") || s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean init1() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=")) {
            i++;
            if (init1Compliment()) {
                return true;
            }
        } else if (s().equals(";") || s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean exp_d() {
        String s = TS.get(i).getClassPart();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (exp()) {
                if (cont()) {
                    return true;
                }
            }
        } else if (s().equals(")") || s().equals("}")) {
            return true;
        }

        return false;
    }

    private boolean init2() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=")) {
            i++;
            if (init2Compliment()) {
                return true;
            }
        } else if (s().equals(";") || s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean init3() {
        String s = TS.get(i).getClassPart();
        if (s().equals("=")) {
            i++;
            if (init3Compliment()) {
                return true;
            }
        } else if (s().equals(";") || s().equals(",")) {
            return true;
        }
        return false;
    }

    private boolean init1Compliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("new") || s().equals("{")) {
            if (s().equals("new")) {
                i++;
                if (s().equals("DT")) {
                    i++;
                    if (br_size()) {
                        return true;
                    }
                }
            } else if (s().equals("{")) {
                i++;
                if (exp_d()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean br_size() {
        String s = TS.get(i).getClassPart();
        if (s().equals("[")) {
            i++;
            if (exp()) {
                if (s().equals("]")) {
                    i++;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean cont() {
        String s = TS.get(i).getClassPart();
        if (s().equals(",")) {
            i++;
            if (exp()) {
                if (cont()) {
                    return true;
                }
            }
        } else if (s().equals(")") || s().equals("}")) {
            return true;
        }

        return false;
    }

    private boolean init2Compliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("new") || s().equals("{")) {
            if (s().equals("new")) {
                i++;
                if (s().equals("DT")) {
                    i++;
                    if (br_size()) {
                        if (br_size()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("{")) {
                i++;
                if (data2()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean init3Compliment() {
        String s = TS.get(i).getClassPart();
        if (s().equals("new") || s().equals("{")) {
            if (s().equals("new")) {
                i++;
                if (s().equals("DT")) {
                    i++;
                    if (br_size()) {
                        if (br_size()) {
                            if (br_size()) {
                                return true;
                            }
                        }
                    }
                }
            } else if (s().equals("{")) {
                i++;
                if (data3()) {
                    if (s().equals("}")) {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean data2() {
        String s = TS.get(i).getClassPart();
        if (s().equals("{")) {
            i++;
            if (exp_d()) {
                if (s().equals("}")) {
                    i++;
                    if (cont2()) {
                        return true;
                    }
                }
            }

        } else if (s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean data3() {
        String s = TS.get(i).getClassPart();
        if (s().equals("{")) {
            i++;
            if (data2()) {
                if (s().equals("}")) {
                    i++;
                    if (cont3()) {
                        return true;
                    }
                }
            }

        } else if (s().equals("}")) {
            return true;
        }
        return false;
    }

    private boolean cont2() {
        String s = TS.get(i).getClassPart();
        if (s().equals(",")) {
            i++;
            if (s().equals("{")) {
                i++;
                if (exp_d()) {
                    if (s().equals("}")) {
                        i++;
                        if (cont2()) {
                            return true;
                        }
                    }
                }

            }
        } else if (s().equals("}")) {
            return true;
        }

        return false;
    }

    private boolean cont3() {
        String s = TS.get(i).getClassPart();
        if (s().equals(",")) {
            i++;
            if (s().equals("{")) {
                i++;
                if (data2()) {
                    if (s().equals("}")) {
                        i++;
                        if (cont3()) {
                            return true;
                        }
                    }
                }

            }
        } else if (s().equals("}")) {
            return true;
        }

        return false;
    }
}
