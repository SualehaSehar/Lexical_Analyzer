package lexical_analyzer.helper;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lexical_analyzer.DT;
import lexical_analyzer.FDT;
import lexical_analyzer.MT;
import lexical_analyzer.Semantic;
import static lexical_analyzer.Semantic.stack;
import lexical_analyzer.Token;

/**
 *
 * @author Sualeha
 */
public class SyntaxHelper {

    MT ref;
    List<Token> TS;
    static int i;
    ArrayList<Integer> scop = (ArrayList) stack.clone();
    int count = 0;

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

    private String vp() {
        String ss = TS.get(i).getValuePart();
        return ss;
    }

    private String line() {
        String ss = Integer.toString(TS.get(i).getLineNo());
        return ss;
    }

    private boolean S() {
        String id = UUID.randomUUID().toString();
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) {
            if (s().equals("final") || s().equals("class")) {
                if (finalT()) {
                    if (struct()) {
                        MT cc = new MT(id, Attributes.class_N, Attributes.class_t, Attributes.class_tm, Attributes.class_in);
                        if (!Semantic.insertMT(cc)) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);

                        } else {
                            ref = cc;
                        }
                        if (s().equals("{")) {  //terminal, so inc i
                            Semantic.createScope();
                            i++;
                            if (CB()) {
                                return true;

                            }
                        }
                    }
                }
            } else if (s().equals("abstract")) {  //if word is  abstract //terminal so inc i
                i++;
                if (struct()) {
                    MT cc = new MT(id, Attributes.class_N, Attributes.class_t, "abstract", Attributes.class_in);
                    if (!Semantic.insertMT(cc)) {
                        System.out.println("Redeclaration error at " + line());
                        System.exit(0);

                    } else {
                        ref = cc;
                    }
                    if (s().equals("{")) {  //terminal, so inc i
                        Semantic.createScope();
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
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (S()) {
                return true;
            }
        } else if (s().equals("$")) {
            return true;
        }

        return false;
    }

    private boolean finalT() {
        if (s().equals("final")) { //first of current NT
            Attributes.class_tm = vp();
            i++;
            return true;

        } else if (s().equals("class")) {
            Attributes.class_tm = "";
            return true;
        }
        return false;
    }

    private boolean struct() {
        if (s().equals("class")) { //first of current NT
            Attributes.class_t = vp();
            i++;
            if (s().equals("ID")) {
                Attributes.class_N = vp();
                i++;
                Attributes.class_in = new ArrayList<String>();
                if (inherit()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean CB() {
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID") || s().equals("abstract")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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
        if (s().equals("inherit")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                String N = vp();
                MT y = Semantic.lookupMT(N);
                if (y == null) {
                    System.out.println("class " + N + " " + "is not declared! at " + line());
                    System.exit(0);
                } else if (y.getTm().equals("final")) {
                    System.out.println("class " + N + " " + "can not be inherited! at " + line());
                    System.exit(0);
                } else {
                    Attributes.class_in.add(N);
                }
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
        if (s().equals(",")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                String N = vp();
                MT y = Semantic.lookupMT(N);
                if (y == null) {
                    System.out.println("class " + N + " " + "is not declared!");
                    System.exit(0);
                } else if (y.getTm().equals("final")) {
                    System.out.println("class " + N + " " + "can not be inherited!");
                    System.exit(0);
                } else {
                    Attributes.class_in.add(N);
                }
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
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                String A = "public";
                i++;
                if (d1(A)) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                String A = "private";
                i++;
                if (d6(A)) {
                    return true;
                }
            } else if (s().equals("final")) {
                String A = "private";
                String F = "final";
                i++;
                if (d3(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String A = "private";
                String F = "";
                String S = "static";
                i++;
                if (f(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String A = "private";
                String F = "";
                String S = "";
                if (f(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean ACBB() {
        if (s().equals("abstract") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                String A = "public";
                i++;
                if (dd1(A)) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                String A = "private";
                i++;
                if (dd6(A)) {
                    return true;
                }
            } else if (s().equals("final")) {
                String A = "private";
                String F = "final";
                i++;
                if (dd3(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String A = "private";
                String F = "";
                String S = "static";
                i++;
                if (fACB(A, F, S)) {
                    return true;
                }
            } else if (s().equals("abstract")) {
                if (abs_func()) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String A = "private";
                String F = "";
                String S = "";
                if (fACB(A, F, S)) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean d1(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (d3(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String F = "";
                if (d2(A, F)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                String S = "";
                if (f(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d6(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (d3(A, F)) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                if (d3(A, F)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d3(String A, String F) {
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                String S = "static";
                i++;
                if (f(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String S = "";
                if (f(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean f(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                if (fn_d(A, F, S)) {
                    if (CB()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (CB()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();

                i++;
                if (f1(A, F, S)) {
                    if (CB()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dd1(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (dd3(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String F = "";
                if (dd2(A, F)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                String S = "";
                if (fACB(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd6(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (dd3(A, F)) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                if (dd3(A, F)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd3(String A, String F) {
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                String S = "static";
                i++;
                if (fACB(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String S = "";
                if (fACB(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fACB(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                if (fn_d(A, F, S)) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (ACB()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                i++;
                if (f1(A, F, S)) {
                    if (ACB()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean ACBCompliment() {
        if (s().equals("abstract") || s().equals("AM") || s().equals("final")
                || s().equals("static") || s().equals("void") || s().equals("DT")
                || s().equals("ID") || s().equals("}")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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

    private boolean d2(String A, String F) {
        if (s().equals("static")) { //first of current NT
            String S = "static";
            i++;
            if (d4(A, F, S)) {
                return true;
            }
        }
        return false;
    }

    private boolean d4(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                if (d5(A, F, S)) {
                    return true;
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (CB()) {

                        return true;

                    }
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                i++;
                if (f1(A, F, S)) {
                    if (CB()) {

                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean d5(String A, String F, String S) {
        if (s().equals("func") || s().equals("main")) { //first of current NT
            if (s().equals("func")) {
                if (fn_d(A, F, S)) {
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
        if (s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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

    private boolean f2(String A, String F, String S) {
        if (s().equals("ID") || s().equals("func") || s().equals("[")) { //first of current NT
            if (s().equals("ID")) {
                String N = vp();
                i++;
                if (dec(A, F, S, N)) {
                    return true;
                }
            } else if (s().equals("[") || s().equals("func")) {
                if (b()) {
                    if (fn_d(A, F, S)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean CBCompliment() {
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID") || s().equals("}")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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

    private boolean f1(String A, String F, String S) {
        if (s().equals("ID") || s().equals("func") || s().equals("[") || s().equals("(")) { //first of current NT
            if (s().equals("ID")) {
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                String N = vp();
                i++;
                if (dec(A, F, S, N)) {
                    return true;
                }
            } else if (s().equals("[") || s().equals("func")) {
                if (b()) {
                    if (fn_d(A, F, S)) {
                        return true;
                    }
                }
            } else if (s().equals("(")) {
                if (!Attributes.fn_ret.equals(ref.getName())) {
                    System.out.println("Missing ret type, unless it is a constructor! at " + line());
                    System.exit(0);
                };
                if (constructor(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dddCompliment() {
        if (s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) {
                String A = "public";
                i++;
                if (d6Compliment(A)) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                String A = "private";
                i++;
                if (d6Compliment(A)) {
                    return true;
                }
            } else if (s().equals("final")) {
                String A = "private";
                String F = "final";
                i++;
                if (d3Compliment(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String A = "private";
                String F = "";
                String S = "static";
                i++;
                if (fCompliment(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String A = "private";
                String F = "";
                String S = "";
                if (fCompliment(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean Main() {
        String id = UUID.randomUUID().toString();
        if (s().equals("main")) { //first of current NT
            i++;
            if (!Semantic.insertDT(new DT(id, "main", "void", "public", "static", "", "", ref.getID(), ref.getName()))) {
                System.out.println("Redeclaration error at " + line());
                System.exit(0);
            }
            if (s().equals("(")) {
                Semantic.createScope();
                i++;
                if (s().equals(")")) {
                    i++;
                    if (s().equals("{")) {
                        i++;
                        if (MST()) {
                            if (s().equals("}")) {
                                Semantic.destroyScope();
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
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (class_def()) {
                if (def()) {
                    return true;
                }
            }
        } else if (s().equals("$")) {
            return true;
        }
        return false;
    }

    private boolean d6Compliment(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (d3Compliment(A, F)) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                if (d3Compliment(A, F)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean d3Compliment(String A, String F) {
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                String S = "static";
                i++;
                if (fCompliment(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String S = "";
                if (fCompliment(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fCompliment(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                i++;
                if (fn_d(A, F, S)) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                i++;
                if (f1(A, F, S)) {
                    if (dCompliment()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dd2(String A, String F) {
        if (s().equals("static")) { //first of current NT
            String S = "static";
            i++;
            if (dd4(A, F, S)) {
                return true;
            }
        }
        return false;
    }

    private boolean dd4(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                if (dd5(A, F, S)) {
                    return true;
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (ACB()) {
                        return true;
                    }

                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                i++;
                if (f1(A, F, S)) {
                    if (ACB()) {
                        return true;
                    }

                }
            }
        }
        return false;
    }

    private boolean dd5(String A, String F, String S) {
        if (s().equals("func") || s().equals("main")) { //first of current NT
            if (s().equals("func")) {
                if (fn_d(A, F, S)) {
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
        if (s().equals("abstract") || s().equals("}") || s().equals("AM") || s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("}")) {
                Semantic.destroyScope();
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
                String A = "public";
                i++;
                if (dd6Compliment(A)) {
                    return true;
                }
            } else if (s().equals("AM") && TS.get(i).getValuePart().equals("private")) {
                String A = "private";
                i++;
                if (dd6Compliment(A)) {
                    return true;
                }
            } else if (s().equals("final")) {
                String A = "private";
                String F = "final";
                i++;
                if (dd3Compliment(A, F)) {
                    return true;
                }
            } else if (s().equals("static")) {
                String A = "private";
                String F = "";
                String S = "static";
                i++;
                if (fACBCompliment(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String A = "private";
                String F = "";
                String S = "";
                if (fACBCompliment(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fACBCompliment(String A, String F, String S) {
        if (s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                if (fn_d(A, F, S)) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            } else if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (f2(A, F, S)) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                    System.exit(0);
                }
                i++;
                if (f1(A, F, S)) {
                    if (ddCompliment()) {
                        return true;

                    }
                }
            }
        }
        return false;
    }

    private boolean dd3Compliment(String A, String F) {
        if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("static")) {
                String S = "static";
                i++;
                if (fACBCompliment(A, F, S)) {
                    return true;
                }
            } else if (s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String S = "";
                if (fACBCompliment(A, F, S)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dd6Compliment(String A) {
        if (s().equals("final") || s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("final")) {
                String F = "final";
                i++;
                if (dd3Compliment(A, F)) {
                    return true;
                }
            } else if (s().equals("static") || s().equals("void") || s().equals("DT") || s().equals("ID")) {
                String F = "";
                if (dd3Compliment(A, F)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean class_def() {
        String id = UUID.randomUUID().toString();
        if (s().equals("final") || s().equals("class") || s().equals("abstract")) { //first of current NT
            if (s().equals("final") || s().equals("class")) {
                if (finalT()) {
                    if (struct()) {
                        MT cc = new MT(id, Attributes.class_N, Attributes.class_t, Attributes.class_tm, Attributes.class_in);
                        if (!Semantic.insertMT(cc)) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);

                        } else {
                            ref = cc;
                        }
                        if (s().equals("{")) {
                            Semantic.createScope();
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
                    MT cc = new MT(id, Attributes.class_N, Attributes.class_t, "abstract", Attributes.class_in);
                    if (!Semantic.insertMT(cc)) {
                        System.out.println("Redeclaration error at " + line());
                        System.exit(0);

                    } else {

                        ref = cc;
                    }
                    if (s().equals("{")) {
                        Semantic.createScope();
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

    private boolean fn_d(String A, String F, String S) {
        String id = UUID.randomUUID().toString();
        String type;
        if (s().equals("func")) { //first of current NT
            i++;
            if (s().equals("ID")) {
                String N = vp();
                i++;
                if (s().equals("(")) {
                    Semantic.createScope();
                    i++;
                    Attributes.pl = new ArrayList<String>();
                    Attributes.return_st_flag = false;
                    if (ar_dec()) {
                        if (s().equals(")")) {
                            type = Attributes.fn_ret;
                            if (!Semantic.insertDT(new DT(id, N, Attributes.pl, Attributes.fn_ret, A, S, F, "", ref.getID(), ref.getName()))) {
                                System.out.println("Redeclaration error at " + line());
                                System.exit(0);
                            }
                            i++;
                            if (s().equals("{")) {
                                i++;
                                if (MST()) {
                                    DT rm = Semantic.lookupDT(N, ref.getID(), Attributes.pl);
                                    if (Attributes.return_st_flag && rm.getType().equals("void")) {
                                        System.out.println("Return statement not allowed! at " + line());
                                        System.exit(0);

                                    } else if (!Attributes.return_st_flag && !rm.getType().equals("void")) {
                                        System.out.println("Return statement is missing! at " + line());
                                        System.exit(0);
                                    }
                                    //System.out.println("7");
                                    //System.out.println("7  " + type);
                                    //System.out.println("7  " + Attributes.exp_T1);

                                    if (!type.equals(Attributes.exp_T1) && !type.equals("void")) {
                                        System.out.println("Type mismatch at " + line());
                                        System.exit(0);
                                    }
                                    if (s().equals("}")) {
                                        Semantic.destroyScope();
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
        if (s().equals("[")) { //first of current NT
            Attributes.fn_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.fn_ret += vp();
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
        if (s().equals("[")) { //first of current NT
            Attributes.fn_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.fn_ret += vp();
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
        if (s().equals("[")) { //first of current NT
            Attributes.fn_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.fn_ret += vp();
                i++;
                return true;
            }
        } else if (s().equals("func")) {
            return true;
        }

        return false;
    }

    private boolean ar_dec() {
        if (s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("DT")) {
                Attributes.pl_ret = vp();
                i++;
                if (bb()) {
                    Attributes.pl.add(Attributes.pl_ret);
                    if (s().equals("ID")) {
                        String N = vp();
                        if (!Semantic.insertFDT(new FDT(N, Attributes.pl_ret, Semantic.peekScope()))) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);
                        }
                        i++;
                        if (decc()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("ID")) {
                Attributes.pl_ret = vp();
                System.out.println(Attributes.pl);
                MT y = Semantic.lookupMT(Attributes.pl_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.pl_ret + " " + "is not declared! at" + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.pl_ret + " " + "is abstract! at " + line());
                    System.exit(0);
                }

                i++;
                if (bb()) {
                    Attributes.pl.add(Attributes.pl_ret);
                    if (s().equals("ID")) {
                        String N = vp();
                        if (!Semantic.insertFDT(new FDT(N, Attributes.pl_ret, Semantic.peekScope()))) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);
                        }
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
        if (s().equals("[")) { //first of current NT
            Attributes.pl_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.pl_ret += vp();

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
        if (s().equals("[")) { //first of current NT
            Attributes.pl_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.pl_ret += vp();
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
        if (s().equals("[")) { //first of current NT
            Attributes.pl_ret += vp();
            i++;
            if (s().equals("]")) {
                Attributes.pl_ret += vp();
                i++;
                return true;
            }
        } else if (s().equals("ID")) {
            return true;
        }

        return false;
    }

    private boolean q() {
        if (s().equals("DT") || s().equals("ID")) { //first of current NT
            if (s().equals("DT")) {
                Attributes.pl_ret = vp();
                i++;
                if (bb()) {
                    Attributes.pl.add(Attributes.pl_ret);
                    if (s().equals("ID")) {
                        String N = vp();
                        if (!Semantic.insertFDT(new FDT(N, Attributes.pl_ret, Semantic.peekScope()))) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);
                        }
                        i++;
                        if (decc()) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("ID")) {
                Attributes.pl_ret = vp();
                MT y = Semantic.lookupMT(Attributes.pl_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.pl_ret + " " + "is not declared! at " + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.pl_ret + " " + "is abstract! at " + line());
                    System.exit(0);
                }
                i++;
                if (bb()) {
                    Attributes.pl.add(Attributes.pl_ret);
                    if (s().equals("ID")) {
                        String N = vp();
                        if (!Semantic.insertFDT(new FDT(N, Attributes.pl_ret, Semantic.peekScope()))) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);
                        }
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
        Attributes.pl = new ArrayList<String>();
        String id = UUID.randomUUID().toString();
        if (s().equals("abstract")) { //first of current NT
            i++;
            if (A()) {
                if (Ret_type()) {
                    if (s().equals("func")) {
                        i++;
                        if (s().equals("ID")) {
                            String N = vp();
                            i++;
                            if (s().equals("(")) {
                                i++;
                                if (ar_dec()) {
                                    if (s().equals(")")) {
                                        i++;
                                        if (!Semantic.insertDT(new DT(id, N, Attributes.pl, Attributes.fn_ret, Attributes.abs_modifier, "", "", "abstract", ref.getID(), ref.getName()))) {
                                            System.out.println("Redeclaration error at " + line());
                                            System.exit(0);
                                        }
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
        if (s().equals("AM") && TS.get(i).getValuePart().equals("public")) { //first of current NT
            Attributes.abs_modifier = vp();
            i++;
            return true;
        } else if (s().equals("void") || s().equals("ID") || s().equals("DT")) {
            return true;
        }
        return false;
    }

    private boolean Ret_type() {
        if (s().equals("DT") || s().equals("ID") || s().equals("void")) { //first of current NT
            if (s().equals("DT")) {
                Attributes.fn_ret = vp();
                i++;
                if (b()) {
                    return true;
                }
            } else if (s().equals("ID")) {
                Attributes.fn_ret = vp();
                i++;
                if (b()) {
                    return true;
                }
            } else if (s().equals("void")) {
                Attributes.fn_ret = vp();
                i++;
                return true;
            }
        }

        return false;
    }

    private boolean As_fn(String N) {
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("(")) {
                i++;
                if (exp_d()) {
                    if (s().equals(")")) {
                        i++;
                        
                        if (h(N)) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("=")) {
                if (assign(N)) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (exp()) {
                    if (s().equals("]")) {
                        i++;
                        if (more()) {
                            if (t(N)) {
                                return true;
                            }
                        }
                    }

                }
            } else if (s().equals(".")) {
                if (New(N)) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean t(String N) {
        if (s().equals("=") || s().equals(".")) {

            if (s().equals("=")) {

                Attributes.exp_op = "=";
                i++;
                if (init(N, "")) {
                    return true;
                }
            }
            if (s().equals(".")) {
                if (New(N)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean New(String N) {
        if (s().equals(".")) {
            i++;
            if (NewCompliment(N)) {
                return true;
            }
        }
        return false;
    }

    private boolean NewCompliment(String N) {
        //System.out.println("new' " + Attributes.exp_R);

        if (s().equals("ID") || s().equals("new")) {
            if (s().equals("ID")) {
                String N1 = vp();
                    if (!N.equals("")) {
                        exp_1(N1); // semantic
                        //System.out.println("rhp2' " + Attributes.exp_R);
                        exp_2(N1);
                        //System.out.println("rhp3' " + Attributes.exp_T1);
                        if (check(Attributes.exp_T1)) {
                            st_fn4(N1, Attributes.exp_T1);
                            //System.out.println("rhp4' " + Attributes.exp_R);
                        } 
                    } else {
                        exp_1(N1);
                        //System.out.println("rhp8' " + Attributes.exp_R);
                        exp_2(N1);
                        //System.out.println("rhp5' " + Attributes.exp_T1 + " " + N1);
                        if (check(Attributes.exp_T1)) {
                            st_fn4(N1, Attributes.exp_T1);
                            //System.out.println("rhp7' " + Attributes.exp_R);
                        } 
                    }

                //System.out.println("rhp61' " + Attributes.exp_T1);
                i++;

                if (As_fn(N1)) {

                    return true;
                }
            } else if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    String N1 = vp();
                    i++;

                    MT pt2 = Semantic.lookupMT(N1);
                    if (pt2 == null) {
                        System.out.println("class " + N1 + " " + "is not declared! at" + line());
                        System.exit(0);
                    } else {
                        Attributes.exp_R = pt2.getID();
                    }
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (New("")) {
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
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const")
                || s().equals("bool_const")
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
        if (s().equals("LOL")) {
            Attributes.exp_op = vp();
            i++;
            String t1 = Attributes.exp_T1;
            if (AE()) {
                System.out.println("1");
                System.out.println(Attributes.exp_T1);
                Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);
                if (Attributes.exp_T1.equals("")) {
                    System.out.println("Type mismatch at " + line());
                }
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
        if (s().equals("LOH")) {
            Attributes.exp_op = vp();
            i++;
            String t1 = Attributes.exp_T1;
            if (RE()) {
                System.out.println("2");
                System.out.println(Attributes.exp_T1);
                Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);
                if (Attributes.exp_T1.equals("")) {
                    System.out.println("Type mismatch at " + line());
                }
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

    private boolean assign(String N) {

        if (s().equals("=")) {
            String t1;

            if (!Attributes.exp_R.equals("")) {
                DT rct2 = Semantic.lookupDT(N, Attributes.exp_R);
                if (rct2 == null) {
                    t1 = "";
                } else {
                    t1 = rct2.getType();
                }
            } else {
                t1 = "";
            }

            Attributes.exp_op = "=";
            i++;
            if (init(N, t1)) {
                return true;
            }
        }
        return false;
    }

    private boolean RE() {
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
        if (s().equals("RO")) {
            Attributes.exp_op = vp();
            i++;
            String t1 = Attributes.exp_T1;
            if (E()) {
                System.out.println("3");
                System.out.println(Attributes.exp_T1);
                Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);
                if (Attributes.exp_T1.equals("")) {
                    System.out.println("Type mismatch at " + line());
                }
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
        if (s().equals("PM")) {
            Attributes.exp_op = vp();
            i++;
            String t1 = Attributes.exp_T1;
            if (T()) {
                System.out.println("4");
                System.out.println(Attributes.exp_T1);
                Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);
                if (Attributes.exp_T1.equals("")) {
                    System.out.println("Type mismatch at " + line());
                }
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
        if (s().equals("MDM")) {
            Attributes.exp_op = vp();
            i++;
            String t1 = Attributes.exp_T1;
            if (F()) {
                System.out.println("5");
                System.out.println(Attributes.exp_T1);
                Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);
                if (Attributes.exp_T1.equals("")) {
                    System.out.println("Type mismatch at " + line());
                }
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
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const")
                || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (s().equals("this") || s().equals("super") || s().equals("ID")) {
                if (TS()) {
                    if (s().equals("ID")) {
                        String N = vp();
                        i++;
                        if (o(N)) {
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
        if (s().equals("this") || s().equals("super")) {
            Attributes.exp_R = vp();
            i++;
            if (s().equals(".")) {
                i++;
                return true;
            }
        } else if (s().equals("ID")) {
            Attributes.exp_R = "";
            return true;
        }
        return false;
    }

    private boolean o(String N) {
        //System.out.println("o " + Attributes.exp_R);
        if (s().equals("(") || s().equals("=") || s().equals(".") || s().equals("[")) {

            exp_1(N);
            //System.out.println("rhp8' " + Attributes.exp_R);
            exp_2(N);
            //System.out.println("rhp5' " + Attributes.exp_T1);
            if (check(Attributes.exp_T1)) {
                st_fn4(N, Attributes.exp_T1);
                //System.out.println("rhp7' " + Attributes.exp_R);
            }

            if (RHP(N)) {
                return true;
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {

            exp_2(N);
            return true;
        }

        return false;
    }

    private boolean Const() {
        if (s().equals("int_const") || s().equals("flt_const") || s().equals("char_const")
                || s().equals("bool_const") || s().equals("str_const")) {
            //semantic
            if (s().equals("int_const")) {
                Attributes.exp_T1 = "int";
            } else if (s().equals("flt_const")) {
                Attributes.exp_T1 = "float";
            } else if (s().equals("char_const")) {
                Attributes.exp_T1 = "char";
            } else if (s().equals("bool_const")) {
                Attributes.exp_T1 = "bool";
            } else if (s().equals("str_const")) {
                Attributes.exp_T1 = "String";
            }
            i++;
            return true;
        }
        return false;
    }

    private boolean h(String N) {
        if (s().equals(".")) {
            if (New(N)) {
                return true;
            }
        } else if (s().equals(";") || s().equals(",")) {

            return true;
        }
        return false;
    }

    private boolean RHP(String N) {
        //System.out.println("RUPp " + N);
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("(")) {
                i++;
                if (exp_d()) {
                    if (s().equals(")")) {
                        i++;

                        if (hCompliment(N)) {

                            return true;
                        }
                    }
                }
            } else if (s().equals("=")) {
                if (assign(N)) {
                    return true;
                }
            } else if (s().equals("[")) {
                i++;
                if (exp()) {
                    if (s().equals("]")) {
                        i++;
                        if (more()) {
                            if (tCompliment(N)) {
                                return true;
                            }
                        }
                    }

                }
            } else if (s().equals(".")) {
                if (RHPCompliment(N)) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean tCompliment(String N) {
        if (s().equals("=") || s().equals(".")) {
            if (s().equals("=")) {

                Attributes.exp_op = "=";
                i++;
                if (init(N, "")) {
                    return true;
                }
            }
            if (s().equals(".")) {
                if (RHPCompliment(N)) {
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

    private boolean hCompliment(String N) {
        //System.out.println("h' " + Attributes.exp_R);
        if (s().equals(".")) {

            if (RHPCompliment(N)) {
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

    private boolean RHPCompliment(String N) {
        //System.out.println("rhp' " + Attributes.exp_R);
        if (s().equals(".")) {
            i++;
            if (ro(N)) {
                return true;
            }
        }
        return false;
    }

    private boolean ro(String N) {
        //System.out.println("ro  " + Attributes.exp_R);
        if (s().equals("ID") || s().equals("new")) {
            if (s().equals("ID")) {
                String N1 = vp();
                if (!Attributes.exp_obj) {
                    //System.out.println("ro81' " + N1);
                    exp_1(N1);
                    //System.out.println("ro88' " + Attributes.exp_R);
                    exp_2(N1);
                    //System.out.println("ro5555' " + Attributes.exp_T1 + " " + N1);
                    if (check(Attributes.exp_T1)) {
                        st_fn4(N1, Attributes.exp_T1);
                        //System.out.println("rhp7' " + Attributes.exp_R);
                    }
                } else {
                    //System.out.println("ro8' " + N);
                    exp_1(N);
                    //System.out.println("rhp8' " + Attributes.exp_R);
                    exp_2(N);
                    //System.out.println("rhp5' " + Attributes.exp_T1 + " " + N1);
                    if (check(Attributes.exp_T1)) {
                        st_fn4(N, Attributes.exp_T1);
                        //System.out.println("rhp7' " + Attributes.exp_R);
                    }
                }
                i++;
                if (RR(N1)) {
                    return true;
                }
            } else if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    String N1 = vp();
                    //System.out.println("la"+Attributes.fn_ret);                    
                    if (!N1.equals(Attributes.fn_ret)) {
                        st_fn(Attributes.fn_ret, N1);
                        if (!Attributes.inherit_chain_flag) {
                            System.out.println("Type mismatch at " + line());
                            System.exit(0);
                        }
                    }

                    i++;                  

                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (RHPCompliment(N)) {
                                    Attributes.exp_obj = true;
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

    private boolean RR(String N) {
        //System.out.println("rr " + N);
        if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (RHP(N)) {
                return true;
            }
        } else if (s().equals("MDM") || s().equals("PM")
                || s().equals("RO") || s().equals("LOH") || s().equals("LOL")
                || s().equals(";") || s().equals("=")
                || s().equals(")") || s().equals("]") || s().equals(",") || s().equals("}")) {
            //semantic
            exp_4(N, Attributes.exp_pl);
            exp_2(N);
            return true;
        }
        return false;
    }

    private boolean LL() {
        if (s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new") || s().equals("final")) {
            if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
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

    private boolean constructor(String A, String F, String S) {
        String id = UUID.randomUUID().toString();
        Attributes.pl = new ArrayList<String>();
        if (s().equals("(")) {
            Semantic.createScope();
            i++;
            if (ar_dec()) {
                if (s().equals(")")) {
                    i++;
                    if (F.equals("final") || S.equals("static")) {
                        System.out.println("Constructor can't be static or final.");
                        System.exit(0);
                    } else {
                        if (!Semantic.insertDT(new DT(id, Attributes.fn_ret, Attributes.pl, "", A, S, F, "", ref.getID(), ref.getName()))) {
                            System.out.println("Redeclaration error at " + line());
                            System.exit(0);
                        }
                    }
                    if (s().equals("{")) {
                        i++;
                        if (const_MST()) {
                            if (s().equals("}")) {
                                Semantic.destroyScope();
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
        if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("return") || s().equals("new") || s().equals("final")) {
            String A = "";
            String F = "";
            String S = "";
            if (s().equals("DT")) {
                //System.out.println("sst1 ");
                Attributes.FDT_flag = true;
                Attributes.fn_ret = vp();
                i++;
                if (s().equals("ID")) {
                    String N = vp();
                    i++;
                    if (dec(A, F, S, N)) {
                        return true;
                    }
                }
            } else if (s().equals("ID")) {
                //System.out.println("sst2 ");
                Attributes.FDT_flag = true;
                Attributes.fn_ret = vp();
                i++;
                if (for_fn(A, F, S, Attributes.fn_ret)) {
                    return true;
                }
            } else if (s().equals("final")) {
                i++;
                if (s().equals("DT") || s().equals("ID")) {
                    //System.out.println("sst3 ");
                    if (s().equals("ID")) {
                        Attributes.fn_ret = vp();

                        MT y = Semantic.lookupMT(Attributes.fn_ret);
                        if (y == null) {
                            System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at" + line());
                            System.exit(0);
                        } else if (y.getTm().equals("abstract")) {
                            System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at" + line());
                            System.exit(0);
                        }
                    }

                    Attributes.FDT_flag = true;
                    Attributes.fn_ret = vp();

                    i++;
                    if (s().equals("ID")) {
                        String N = vp();
                        i++;
                        if (dec(A, F, S, N)) {
                            return true;
                        }
                    }
                }
            } else if (s().equals("throw")) {
                if (throwT()) {
                    return true;
                }
            } else if (s().equals("this")) {
                Attributes.exp_R = "this";
                i++;
                if (New("")) {
                    if (s().equals(";")) {
                        i++;
                        return true;
                    }
                }
            } else if (s().equals("super")) {
                Attributes.exp_R = "super";
                i++;
                if (New("")) {
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
                    String N = vp();
                    i++;
                    MT pt = Semantic.lookupMT(N);
                    if (pt == null) {
                        System.out.println("class " + N + " " + "is not declared! at" + line());
                        System.exit(0);
                    }
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (New("")) {
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
        if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new") || s().equals("final")) {
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

    private boolean for_fn(String A, String F, String S, String N1) {

        if (s().equals("ID") || s().equals("instanceof") || s().equals("(")
                || s().equals("[") || s().equals(".") || s().equals("=")) {
            if (s().equals("ID")) {

                MT y = Semantic.lookupMT(Attributes.fn_ret);
                if (y == null) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is not declared! at " + line());
                    System.exit(0);
                } else if (y.getTm().equals("abstract")) {
                    System.out.println("class " + Attributes.fn_ret + " " + "is abstract! at " + line());
                    System.exit(0);
                }

                String N = vp();
                i++;
                if (dec(A, F, S, N)) {
                    return true;
                }
            } else if (s().equals("instanceof")) {
                if (inst_of()) {
                    return true;
                }
            } else if (s().equals("(") || s().equals("[") || s().equals(".") || s().equals("=")) {
                if (As_fn(N1)) {
                    if (s().equals(";")) {
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean BreakT() {
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
        if (s().equals("return")) {
            Attributes.return_st_flag = true;
            i++;
            if (oo()) {
                return true;
            }
        }
        return false;
    }

    private boolean oo() {
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
        if (s().equals("try")) {
            i++;
            if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
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
        if (s().equals("finally")) {
            i++;
            if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
                        i++;
                        return true;
                    }
                }
            }
        } else if (s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("}") || s().equals("case") || s().equals(":")
                || s().equals("default") || s().equals("else") || s().equals("final")) {
            return true;
        }
        return false;
    }

    private boolean CatchT() {
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
                                Semantic.createScope();
                                i++;
                                if (MST()) {
                                    if (s().equals("}")) {
                                        Semantic.destroyScope();
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
                || s().equals("default") || s().equals("else") || s().equals("final")) {
            return true;
        }
        return false;
    }

    private boolean loop() {
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
        if (s().equals(";") || s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("new") || s().equals("final")) {
            if (s().equals(";")) {
                i++;
                return true;
            } else if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (MST()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
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
        if (s().equals("else")) {
            i++;
            if (body()) {
                return true;
            }
        } else if (s().equals(";") || s().equals("{") || s().equals("DT") || s().equals("ID") || s().equals("super")
                || s().equals("throw") || s().equals("this") || s().equals("break") || s().equals("continue")
                || s().equals("return") || s().equals("try") || s().equals("loop") || s().equals("if")
                || s().equals("switch") || s().equals("}") || s().equals("case") || s().equals(":")
                || s().equals("default") || s().equals("else") || s().equals("final")) {
            return true;
        }
        return false;
    }

    private boolean Switch_st() {
        if (s().equals("switch")) {
            i++;
            if (s().equals("(")) {
                i++;
                if (exp()) {
                    if (s().equals(")")) {
                        i++;
                        if (s().equals("{")) {
                            Semantic.createScope();
                            i++;
                            if (p1()) {
                                if (s().equals("}")) {
                                    Semantic.destroyScope();
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
            Semantic.destroyScope();
            i++;
            return true;
        }
        return false;
    }

    private boolean CaseT() {
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
        if (s().equals("DT") || s().equals("ID") || s().equals("super") || s().equals("throw")
                || s().equals("this") || s().equals("break") || s().equals("continue") || s().equals("return")
                || s().equals("try") || s().equals("loop")
                || s().equals("if") || s().equals("switch") || s().equals("new") || s().equals("final")) {
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

    private boolean throwT() {
        if (s().equals("throw")) {
            i++;
            if (k()) {
                return true;
            }
        }
        return false;
    }

    private boolean inst_of() {
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
        if (s().equals("ID")) {
            i++;
            if (s().equals(";")) {
                i++;
                return true;
            }
        } else if (s().equals("new")) {
            i++;
            if (s().equals("ID")) {
                String N = vp();
                MT pt = Semantic.lookupMT(N);
                if (pt == null) {
                    System.out.println("class " + N + " " + "is not declared! at" + line());
                    System.exit(0);
                }
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

    private boolean dec(String A, String F, String S, String N) {
        String id = UUID.randomUUID().toString();
        if (s().equals("[") || s().equals("=") || s().equals(";")
                || s().equals(",")) {
            if (s().equals("[")) {
                Attributes.fn_ret += vp();
                i++;
                if (s().equals("]")) {
                    Attributes.fn_ret += vp();
                    i++;
                    if (D()) {
                        if (Attributes.FDT_flag) {
                            if (!Semantic.insertFDT(new FDT(N, Attributes.fn_ret, Semantic.peekScope()))) {
                                System.out.println("Redeclaration error at " + line());
                                System.exit(0);
                            }
                            Attributes.FDT_flag = false;

                        } else {
                            if (!Semantic.insertDT(new DT(id, N, Attributes.fn_ret, A, S, F, "", ref.getID(), ref.getName()))) {
                                System.out.println("Redeclaration error at " + line());
                                System.exit(0);
                            }
                        }
                        if (list(A, F, S)) {
                            return true;
                        }
                    }
                }
            } else if ((s().equals(";")) || (s().equals(",")) || (s().equals("="))) {
                if (Attributes.FDT_flag) {
                    //System.out.println("dec ");
                    if (!Semantic.insertFDT(new FDT(N, Attributes.fn_ret, Semantic.peekScope()))) {
                        System.out.println("Redeclaration error at " + line());
                        System.exit(0);
                    }
                    Attributes.FDT_flag = false;
                } else {
                    if (!Semantic.insertDT(new DT(id, N, Attributes.fn_ret, A, S, F, "", ref.getID(), ref.getName()))) {
                        System.out.println("Redeclaration error at " + line());
                        System.exit(0);
                    }
                }
                if (init_d(N)) {
                    if (list(A, F, S)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean init(String N, String t) {
        String t1 = "";
        Attributes.FDT_flag = false;
        //System.out.println("init    " + Attributes.exp_R);
        //System.out.println("init    " + t);
        if (t.equals("")) {
            scop = (ArrayList) stack.clone();
            count = 0;
            FDT f = fdtcheck2(N, Semantic.peekScope());
            if (Attributes.fff == null) {
                DT rct = Semantic.lookupDT(N, ref.getID());
                if (rct == null) {
                    System.out.println(N + " " + "is not declared! at " + line());
                    System.exit(0);
                } else {
                    t1 = rct.getType();
                }
            } else {
                t1 = Attributes.fff.getType();
                Attributes.fff = null;
            }
        } else {
            t1 = t;
        }

        if (s().equals("new") || s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const")
                || s().equals("bool_const") || s().equals("str_const")) {
            if (s().equals("new")) {
                i++;
                if (s().equals("ID")) {
                    String N1 = vp();
                    if (!N1.equals(Attributes.fn_ret)) {
                        st_fn(Attributes.fn_ret, N1);
                        if (!Attributes.inherit_chain_flag) {
                            System.out.println("Type mismatch at " + line());
                            System.exit(0);
                        }
                    }
                    i++;
                    if (s().equals("(")) {
                        i++;
                        if (exp_d()) {
                            if (s().equals(")")) {
                                i++;
                                if (h("")) {
                                    return true;
                                }
                            }
                        }

                    }
                }

            } else {
                if (exp()) {
                    //System.out.println("6");
                    //System.out.println(t1);
                    //System.out.println(Attributes.exp_T1);
                    //System.out.println(Attributes.exp_op);

                    Attributes.exp_T1 = Semantic.compatibilityByLRO(t1, Attributes.exp_T1, Attributes.exp_op);

                    if (Attributes.exp_T1.equals("")) {
                        System.out.println("Type mismatch at " + line());
                        System.exit(0);
                    }
                    if (expCompliment()) {
                        return true;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private void st_fn(String N, String N1) {
        MT pt = Semantic.lookupMT(N1);
        if (pt == null) {
            System.out.println("class " + N1 + " " + "is not declared! at" + line());
            System.exit(0);
        } else {
            ArrayList<String> in = pt.getInherit();

            if (in == null) {
                return;
            } else {
                for (int j = 0; j < in.size(); j++) {
                    String N2 = in.get(j);
                    if (!N2.equals(N)) {
                        st_fn(N, N2);
                    } else {
                        Attributes.inherit_chain_flag = true;
                    }
                }
            }

        }
    }

    private boolean init_d(String N) { // N1 is variable name to do lookup
        if (s().equals("=")) {
            Attributes.exp_op = "=";
            i++;
            if (init(N, "")) {
                return true;
            }
        } else if ((s().equals(";")) || (s().equals(","))) {
            return true;

        }
        return false;
    }

    private boolean list(String A, String F, String S) {
        String id = UUID.randomUUID().toString();
        if (s().equals(";") || s().equals(",")) {
            if (s().equals(";")) {
                i++;
                return true;
            } else if (s().equals(",")) {
                i++;
                if (s().equals("ID")) {
                    String N = vp();
                    i++;
                    if (dec(A, F, S, N)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean D() {
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
        Attributes.exp_pl = new ArrayList<String>();
        if (s().equals("this") || s().equals("super") || s().equals("ID") || s().equals("(")
                || s().equals("int_const") || s().equals("flt_const") || s().equals("char_const") || s().equals("bool_const")
                || s().equals("str_const")) {
            if (exp()) {
                Attributes.exp_pl.add(Attributes.exp_T1);
                System.out.println("exp_d "+Attributes.exp_pl);
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
                Semantic.createScope();
                i++;
                if (exp_d()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean br_size() {
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
        if (s().equals(",")) {
            i++;
            if (exp()) {
                Attributes.exp_pl.add(Attributes.exp_T1);
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
                Semantic.createScope();
                i++;
                if (data2()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean init3Compliment() {
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
                Semantic.createScope();
                i++;
                if (data3()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
                        i++;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean data2() {
        if (s().equals("{")) {
            Semantic.createScope();
            i++;
            if (exp_d()) {
                if (s().equals("}")) {
                    Semantic.destroyScope();
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
        if (s().equals("{")) {
            Semantic.createScope();
            i++;
            if (data2()) {
                if (s().equals("}")) {
                    Semantic.destroyScope();
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
        if (s().equals(",")) {
            i++;
            if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (exp_d()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
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
        if (s().equals(",")) {
            i++;
            if (s().equals("{")) {
                Semantic.createScope();
                i++;
                if (data2()) {
                    if (s().equals("}")) {
                        Semantic.destroyScope();
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

    private void exp_1(String N) {
        //System.out.println("EXP1_ " + N);
        if (Attributes.exp_R.equals("")) {
            scop = (ArrayList) stack.clone();
            count = 0;
            FDT t1 = fdtcheck2(N, Semantic.peekScope());
            if (Attributes.fff == null) {
                DT rct = Semantic.lookupDT(N, ref.getID());
                if (rct == null) {
                    System.out.println(N + " " + "is not declared! at" + line());
                    System.exit(0);
                } else {
                    Attributes.exp_R = rct.getRef();
                }

            } else {
                //System.out.println("2 " + Attributes.exp_R);
                Attributes.exp_R = ref.getID();
            }
            Attributes.fff = null;
        } else if (Attributes.exp_R.equals("super")) {
            MT pt = Semantic.lookupMT(ref.getName());
            if (pt == null) {
                System.out.println("class " + ref.getName() + " " + "is not declared! at" + line());
                System.exit(0);
            } else if (pt.getInherit() == null) {
                System.out.println("class " + ref.getName() + " " + "is not extending anything! at" + line());
                System.exit(0);
            } else {
                ArrayList<String> in = pt.getInherit();
                for (int i = 0; i < in.size(); i++) {                    
                    st_fn3(N, in.get(i));
                    if (Attributes.inherit_chain_flag3) {
                        break;
                    }
                }
                if (!Attributes.inherit_chain_flag3) {
                    System.out.println(N + " " + "is not declared! at" + line());
                    System.exit(0);

                }

            }
        } else if (Attributes.exp_R.equals("this")) {
            DT rct = Semantic.lookupDT(N, ref.getID());
            if (rct == null) {
                System.out.println(N + " " + "is not declared! at" + line());
                System.exit(0);
            } else {
                Attributes.exp_R = rct.getRef();
            }
        } else {
            DT rct = Semantic.lookupDT(N, Attributes.exp_R);
            if (rct == null) {
                //Attributes.inherit_chain_flag3 = false;
                //if (check(Attributes.exp_T1)) {
                    st_fn3(N, Attributes.exp_T1);
                    if (!Attributes.inherit_chain_flag3) {

                        System.out.println(N + " " + "is not declared! at" + line());
                        System.exit(0);
                    }
                //}
            } else if (rct.getAm().equals("private") && !ref.getID().equals(Attributes.exp_R)) {
                System.out.println("Not Accessable! at" + line());
                System.exit(0);
            } else {
                Attributes.exp_R = rct.getRef();
            }
        }
    }

    private void exp_2(String N) {
        //System.out.println("EXP2_ " + N);
        if (Attributes.exp_R.equals("")) {
            scop = (ArrayList) stack.clone();
            count = 0;
            FDT t1 = fdtcheck2(N, Semantic.peekScope());

            if (Attributes.fff == null) {
                DT rct = Semantic.lookupDT(N, ref.getID());
                if (rct == null) {
                    System.out.println(N + " " + "is not declared! at1 " + line());
                    System.exit(0);
                } else {
                    Attributes.exp_T1 = rct.getType();
                }

            } else {
                Attributes.exp_T1 = Attributes.fff.getType();
            }
            Attributes.fff = null;
        } else if (Attributes.exp_R.equals("super")) {
            MT pt = Semantic.lookupMT(ref.getName());
            if (pt == null) {
                System.out.println("class " + ref.getName() + " " + "is not declared! at2 " + line());
                System.exit(0);
            } else if (pt.getInherit() == null) {
                System.out.println("class " + ref.getName() + " " + "is not extending anything! at50 " + line());
                System.exit(0);
            } else {
                ArrayList<String> in = pt.getInherit();
                for (int i = 0; i < in.size(); i++) {
                    if (check(Attributes.exp_T1)) {
                        st_fn2(N, in.get(i));
                        if (Attributes.inherit_chain_flag2) {
                            break;
                        }
                    }
                }
                if (!Attributes.inherit_chain_flag2) {
                    System.out.println(N + " " + "is not declared! at3 " + line());
                    System.exit(0);
                }
            }
        } else if (Attributes.exp_R.equals("this")) {
            DT rct = Semantic.lookupDT(N, ref.getID());
            if (rct == null) {
                System.out.println(N + " " + "is not declared! at4 " + line());
                System.exit(0);
            } else {
                Attributes.exp_R = rct.getRef();
            }
        } else {
            DT rct = Semantic.lookupDT(N, Attributes.exp_R, Attributes.exp_pl);
            if (rct == null) {
                //Attributes.inherit_chain_flag2 = false;
                //if (check(Attributes.exp_T1)) {
                    st_fn2(N, Attributes.exp_T1);
                    if (!Attributes.inherit_chain_flag2) {
                        //exp_4(N,Attributes.exp_pl);
                        System.out.println(N + " " + "is not declared! at5 " + line());
                        System.exit(0);
                    }
                //}
            } else if (rct.getAm().equals("private") && !ref.getID().equals(Attributes.exp_R)) {
                System.out.println("Not Accessable! at " + line());
                System.exit(0);
            } else {
                Attributes.exp_T1 = rct.getType();
            }
        }
        
    }

    private void st_fn2(String N, String N1) {
        //System.out.println("fn2 " + N + "  " + N1);
        MT pt = Semantic.lookupMT(N1);
        if (pt == null) {
            System.out.println("class " + N1 + " " + "is not declared! at" + line());
            System.exit(0);
        } else {
            String r = pt.getID();
            ArrayList<String> in = pt.getInherit();

            DT rct = Semantic.lookupDT(N, r);
            if (rct == null) {
                if (in != null) {
                    for (int j = 0; j < in.size(); j++) {
                        String N2 = in.get(j);
                        st_fn2(N, N2);
                    }
                }
            } else {
                Attributes.inherit_chain_flag2 = true;
                Attributes.exp_T1 = rct.getType();
            }
        }
    }

    private void st_fn3(String N, String N1) {
        //System.out.println("fn3 " + N + "  " + N1);
        MT pt = Semantic.lookupMT(N1);
        if (pt == null) {
            System.out.println("class " + N1 + " " + "is not declared! at " + line());
            System.exit(0);
        } else {
            String r = pt.getID();
            ArrayList<String> in = pt.getInherit();
            DT rct = Semantic.lookupDT(N, r);
            if (rct == null) {
                if (in != null) {
                    for (int j = 0; j < in.size(); j++) {
                        String N2 = in.get(j);
                        st_fn3(N, N2);
                    }
                }
            } else {
                //System.out.println("fn3 " + N + "  " + N1);
                Attributes.inherit_chain_flag3 = true;
                Attributes.exp_R = rct.getRef();
            }
        }
    }

    private void st_fn4(String N, String N1) {
        MT pt = Semantic.lookupMT(N1);
        if (pt == null) {
            System.out.println("class " + N1 + " " + "is not declared! at" + line());
            System.exit(0);
        } else {
            Attributes.exp_R = pt.getID();
        }
    }

    private void exp_4(String N, ArrayList<String> PL) {
        //System.out.println("444 " + Attributes.exp_R);
        //System.out.println("444 " + N);
        //System.out.println("444 " + PL);
        DT rct = Semantic.lookupDT(N, Attributes.exp_R, PL);
        if (rct == null) {
            System.out.println(N + " " + "not found! at" + line());
            System.exit(0);
        } else if (rct.getAm().equals("private") && !ref.getID().equals(Attributes.exp_R)) {
            System.out.println("Not callable (private)! at" + line());
            System.exit(0);
        } else if (rct.getAbstractt().equals("abstract")) {
            System.out.println("Not callable (abstract)! at" + line());
            System.exit(0);
        } else {
            Attributes.exp_T1 = rct.getType();
        }
    }

    FDT fdtcheck2(String N, String sc) {
        FDT f = Semantic.lookupFDT(N, sc);

        if (f != null && count == 0) {
            //System.out.println(f.toString());
            Attributes.fff = f;
            count++;
            return f;
        }
       
        //System.out.println(stack);
        //System.out.println(scop.size());
        if (f == null) {
            if (scop.size() == 0) {
                return f;
            } else {
                if (scop.size() > 1) {
                    int s = scop.remove(scop.size() - 1);
                    //System.out.println(" jjj " + s);
                    fdtcheck2(N, Integer.toString(s));
                } else if (scop.size() == 1) {
                    int s = scop.remove(0);
                    //System.out.println(" jjj " + s);
                    fdtcheck2(N, Integer.toString(s));
                }
            }
        } 
        return f;
    }

    boolean check(String N) {
        if (N.equals("int") || N.equals("int[]") || N.equals("int[][]") || N.equals("int[][][]")
                || N.equals("String") || N.equals("String[]") || N.equals("String[][]") || N.equals("String[][][]")
                || N.equals("char") || N.equals("char[]") || N.equals("char[][]") || N.equals("char[][][]")
                || N.equals("float") || N.equals("float[]") || N.equals("float[][]") || N.equals("float[][][]")
                || N.equals("bool") || N.equals("bool[]") || N.equals("bool[][]") || N.equals("bool[][][]")) {
            return false;
        }
        return true;
    }

    boolean plcheck(ArrayList<String> ll) {
        boolean f = false;
        if (ll != null && Attributes.exp_pl != null) {
            if (Integer.toString(Attributes.exp_pl.size()).equals(Integer.toString(ll.size()))) {
                for (int i = 0; i < Attributes.exp_pl.size(); i++) {
                    if (!ll.get(i).equals(Attributes.exp_pl.get(i))) {
                        break;
                    } else {
                        f = true;
                    }
                }
            } 

        }
        return f;
    }

}
