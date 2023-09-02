/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;

import java.util.List;
import lexical_analyzer.helper.SyntaxHelper;

import lexical_analyzer.helper.TokenHelper;

public class Lexical_Analyzer {

    
    public static void main(String[] args) throws Exception {
        String arr[];
        File code = new File("E:\\Lexical_Analyzer\\src\\lexical_analyzer\\code.txt");
        BufferedReader br;
        FileWriter tokens = new FileWriter("E:\\Lexical_Analyzer\\src\\lexical_analyzer\\TokenSet.txt");
        BufferedWriter brw;
        List<Token> TS = TokenHelper.genrateToken(code,tokens); //generating tokens
        
        // for syntax checkiing
        SyntaxHelper SA = new SyntaxHelper(TS);
        SA.analyseSyntax();
        
    }
}
