/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical_Analyzer {


    public static boolean comments = false;

    public static boolean isDigit(String input) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSymbols(String input) {
        Pattern pattern = Pattern.compile("^[+-/*%#{}():;,]?$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static String[] wordBreaker(String str, int index_no) {

        String arr[] = new String[2];
        int i = index_no;
        char ch;
        String temp = "";
        //System.out.println("ind "+index_no);

        while (i < str.length()) {
            ch = str.charAt(i);

            if (i < str.length() && comments) {
                while (i < str.length() && comments) {
                    ch = str.charAt(i);
                    if (ch == '-') {
                        i++;
                        if (i < str.length() && str.charAt(i) == '>') {
                            comments = false;
                        }
                    } else {
                        i++;
                    }
                }
            } else if (ch == ' ') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    break;
                } else {
                    i++;
                    continue;
                }
            } else if (ch == '!') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                if (i + 1 < str.length() && str.charAt(i + 1) == '=') {
                    temp = "!=";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 2);
                    temp = "";
                    break;
                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '>') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                if (i + 1 < str.length() && str.charAt(i + 1) == '=') {
                    temp = ">=";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 2);
                    temp = "";
                    break;
                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '<') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                if (i + 1 < str.length() && str.charAt(i + 1) == '=') {
                    temp = "<=";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 2);
                    temp = "";
                    break;

                } else if (i + 1 < str.length() && str.charAt(i + 1) == '-') {
                    temp = "";
                    comments = true;
                    i++;
                    while (i < str.length() && comments) {
                        ch = str.charAt(i);
                        if (ch == '-') {
                            i++;
                            if (i < str.length() && str.charAt(i) == '>') {
                                comments = false;
//                                arr[0] = "-1";
//                                arr[1] = String.valueOf(i);
//                                temp = "";
//                                break;
                            }
                        } else {
                            i++;
                        }
                    }
                    arr[0] = "-1";
                    arr[1] = String.valueOf(i);
                    temp = "";
                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '=') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                if (i + 1 < str.length() && str.charAt(i + 1) == '=') {
                    temp = "==";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 2);
                    temp = "";
                    break;
                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '|') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                if (i + 1 < str.length() && str.charAt(i + 1) == '|') {
                    temp = "||";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 2);
                    temp = "";
                    break;
                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '%' || ch == '#' || ch == '(' || ch == ')' || ch == '{'
                    || ch == '}' || ch == '[' || ch == ']' || ch == ';' || ch == ':' || ch == ',') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;

                } else {
                    temp = temp + ch;
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";

                    break;
                }
            } else if (ch == '\'') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                temp = temp + ch;

                i++;

                if (i < str.length()) {
                    ch = str.charAt(i);
                    temp = temp + ch;
                    if (ch == '\\') {
                        for (int j = 0; j < 2; j++) {
                            i++;
                            if (i < str.length()) {
                                ch = str.charAt(i);
                                temp = temp + ch;

                            }

                        }
                        arr[0] = temp;
                        arr[1] = String.valueOf(i + 1);
                        temp = "";
                        break;

                    } else {
                        i++;
                        if (i < str.length()) {
                            ch = str.charAt(i);
                            temp = temp + ch;
                            arr[0] = temp;
                            arr[1] = String.valueOf(i + 1);
                            temp = "";
                            break;
                        }
                    }

                }
            } else if (ch == '"') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }
                do {
                    ch = str.charAt(i);
                    if (ch == '\\' && i + 1 < str.length()) {
                        temp = temp + ch + str.charAt(i + 1);
                        i++;
                    } else {
                        temp = temp + ch;
                    }
                    i++;
                } while (i < str.length() && str.charAt(i) != '"');

                if (i < str.length() && str.charAt(i) == '"') {
                    temp = temp + "\"";
                    arr[0] = temp;
                    arr[1] = String.valueOf(i + 1);
                    temp = "";
                    break;
                }
            } else if (ch == '`') {
                if (!temp.isEmpty()) {
                    arr[0] = temp;
                    arr[1] = String.valueOf(i);
                    temp = "";
                    break;
                }

                i = str.length();
            } else if (ch == '.') {
                if (!temp.isEmpty()) {
                    if (isDigit(temp)) {

                        i++;
                        if (i < str.length()) {
                            ch = str.charAt(i);

                            if (isDigit(Character.toString(ch))) {
                                temp = temp + "." + ch;
                            } else {
                                arr[0] = temp;
                                arr[1] = String.valueOf(i);
                                temp = "";
                                break;
                            }
                        } else {
                            i--;
                            arr[0] = temp;
                            arr[1] = String.valueOf(i);
                            temp = "";
                            break;
                        }
                    } else {
                        arr[0] = temp;
                        arr[1] = String.valueOf(i);
                        temp = "";
                        break;
                    }
                } else {
                    temp = temp + ".";
                    i++;
                    if (i < str.length()) {
                        ch = str.charAt(i);

                        if (isDigit(Character.toString(ch))) {
                            temp = temp + ch;

                        } else {
                            arr[0] = temp;
                            arr[1] = String.valueOf(i);
                            temp = "";
                            break;
                        }
                    } else {
                        arr[0] = temp;
                        arr[1] = String.valueOf(i);
                        temp = "";
                        break;
                    }
                }
            } else {
                temp = temp + ch;
            }
            i++;
        }

        if (!temp.isEmpty()) {
            arr[0] = temp;
            arr[1] = String.valueOf(i);
            temp = "";
        } else if (arr[0] == null && arr[1] == null) {
            arr[0] = "-1";
            arr[1] = String.valueOf(i + 1);
        }
        return arr;
    }

    public static String isKeyword(String input) {
        String[][] keywords
                = {
                    {"int", "DT"}, {"float", "DT"}, {"bool", "DT"}, {"char", "DT"},
                    {"void", "void"}, {"null", "Null"}, {"true", "Boolean"}, {"false", "Boolean"},
                    {"String", "DT"}, {"loop", "Loop"}, {"if", "If"}, {"else", "Else"},
                    {"Switch", "Switch"}, {"case", "Case"}, {"end", "End"}, {"default", " Default"},
                    {"func", "Func"}, {"return", "Return"}, {"contine", "Continue"}, {"class", "Class"},
                    {"abstract", "Abstract"}, {"new", "New"}, {"super", "Super"}, {"this", "This"},
                    {"static", "Static"}, {"private", "AM"}, {"public", "AM"}, {"final", "Final"},
                    {"try", "Try"}, {"catch", "Catch"}, {"finally", "Finally"}, {"inherit", "Inherit"},
                    {"instanceof", "InstanceOf"}, {"throw", "Throw"}
                };

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i][0].equals(input)) {
                return keywords[i][1];
            }
        }
        return null;
    }

    public static String isOpr(String input) {
        String[][] keywords
                = {
                    {"+", "MOLP"}, {"-", "MOLP"}, {"/", "MOHP"}, {"*", "MOHP"},
                    {"%", "MOHP"}, {"=", "AO"}, {"<", "RO"}, {">", "RO"},
                    {"!=", "RO"}, {">=", "RO"}, {"<=", "RO"}, {"==", "RO"},
                    {"#", "LOH"}, {"||", "LOL"}
                };

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i][0].equals(input)) {
                return keywords[i][1];
            }
        }
        return null;
    }

    public static String isPunc(String input) {
        String[][] keywords
                = {
                    {";", "SemiColon"}, {",", "Comma"}, {":", "Colon"}, {"{", "OCB"},
                    {"}", "CCB"}, {"(", "OP"}, {")", "CP"}, {"[", "OSQB"},
                    {"]", "CSQB"}, {".", "Dot"}
                };

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i][0].equals(input)) {
                return keywords[i][1];
            }
        }
        return null;
    }

    public static boolean stringConst(String input) {
        //System.out.println(input);
        Pattern pattern = Pattern.compile("^[\"]([\\\\][nrtbo\"\'\\\\]|[^(\"\'\\\\)]|[()])*[\"]$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean charConst(String input) {
        Pattern pattern = Pattern.compile("^[']([\\\\][nrtbo\"\'\\\\]|[^(\"\'\\\\)])[']$");

        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;

        }
    }

    public static boolean floatConst(String input) {
        Pattern pattern = Pattern.compile("^[+-]?[0-9]*[.][0-9]+$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean intConst(String input) {
        Pattern pattern = Pattern.compile("^[+-]?[0-9]+$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean identifier(String input) {
        Pattern pattern = Pattern.compile("^([$][_]+[A-Za-z0-9][A-Za-z0-9_]*)$|" + "^([$][A-Za-z][A-Za-z0-9_]*)$");
        Matcher matcher = pattern.matcher(input);
        boolean flag = matcher.find();
        if (flag) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String arr[];
        File code = new File("E:\\Lexical_Analyzer\\src\\lexical_analyzer\\code1.txt");
        BufferedReader br;
        FileWriter tokens = new FileWriter("E:\\Lexical_Analyzer\\src\\lexical_analyzer\\TokenSet.txt");
        BufferedWriter brw;
        List<Token> Tlist = new ArrayList<Token>();
        String input;
        int lineno = 1;
        int index;
        String word;
        try {
            br = new BufferedReader(new FileReader(code));
            brw = new BufferedWriter(tokens);
            input = br.readLine();
            boolean flag;
            while (input != null) {
                System.out.println(input);
                index = 0;
                System.out.println(input.length());
                while (index < input.length()) {
                    arr = wordBreaker(input, index);
                    System.out.println("------ " + arr[0] + " " + arr[1]);
                    index = Integer.valueOf(arr[1]);
                    word = arr[0];
                    char ch = word.charAt(0);
                    if(word!="-1"){
                    if (ch == '$') {
                        flag = identifier(word);
                        if (flag) {
                            Tlist.add(new Token("ID", word, lineno));
                        } else {
                            Tlist.add(new Token("Invalid Lexeme", word, lineno));
                        }
                    }
                    else if (Character.isLetter(ch)) {
                        String CP = isKeyword(word);
                        if (CP != null) {
                            Tlist.add(new Token(CP, word, lineno));
                        } else {
                            Tlist.add(new Token("Invalid Lexeme", word, lineno));
                        }
                    } else if (Character.isDigit(ch)) {
                        flag = intConst(word);
                        if (flag) {
                            Tlist.add(new Token("Int_Const", word, lineno));
                        } else {
                            flag = floatConst(word);
                            if (flag) {
                                Tlist.add(new Token("Flt_Const", word, lineno));
                            } else {
                                Tlist.add(new Token("Invalid Lexeme", word, lineno));

                            }
                        }
                    } else if (ch == '.') {
                        if (word.length() == 1) {
                            Tlist.add(new Token("Dot", word, lineno));
                        } else {
                            flag = floatConst(word);
                            if (flag) {
                                Tlist.add(new Token("Flt_Const", word, lineno));
                            } else {
                                Tlist.add(new Token("Invalid Lexeme", word, lineno));
                            }
                        }
                    } else if (ch == '\'') {
                        flag = charConst(word);
                        if (flag) {
                            String new_word = word.substring(1, word.length() - 1);
                            Tlist.add(new Token("Char_Const", new_word, lineno));
                        } else {
                            Tlist.add(new Token("Invalid Lexeme", word, lineno));
                        }
                    } else if (ch == '"') {
                        flag = stringConst(word);
                        if (flag) {
                            String new_word = word.substring(1, word.length() - 1);
                            Tlist.add(new Token("Str_Const", new_word, lineno));
                        } else {
                            Tlist.add(new Token("Invalid Lexeme", word, lineno));
                        }
                    } else if (isOpr(word) != null) {
                            String CP = isOpr(word);
                            Tlist.add(new Token(CP, word, lineno));
                        } else if (isPunc(word) != null) {
                            String CP = isPunc(word);
                            Tlist.add(new Token(CP, word, lineno));
                        } else {
                            Tlist.add(new Token("Invalid Lexeme", word, lineno));
                        }
                    
                }
                }
                lineno++;
                input = br.readLine();
            }

            for (int j = 0; j < Tlist.size(); j++) {
                brw.write("(" + Tlist.get(j).getClassPart() + "," + " " + Tlist.get(j).getValuePart() + "," + " " + Tlist.get(j).getLineNo() + ")" + "\n" + "\n");
            }

            brw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
