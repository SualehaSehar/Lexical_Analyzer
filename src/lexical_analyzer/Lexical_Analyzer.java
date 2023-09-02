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
import java.util.ArrayList;

import java.util.List;
import java.util.UUID;
import lexical_analyzer.helper.SyntaxHelper;

import lexical_analyzer.helper.TokenHelper;

public class Lexical_Analyzer {

    
    public static void main(String[] args) throws Exception {
        String arr[];
        File code = new File("E:\\netbeans\\Lexical_Analyzer\\src\\lexical_analyzer\\test.txt");
        BufferedReader br;
        FileWriter tokens = new FileWriter("E:\\netbeans\\Lexical_Analyzer\\src\\lexical_analyzer\\TokenSet.txt");
        BufferedWriter brw;
        List<Token> TS = TokenHelper.genrateToken(code,tokens); //generating tokens
        
        Semantic.deleteData(); // delete all saved data in tables
        // for syntax checkiing
        SyntaxHelper SA = new SyntaxHelper(TS);
        SA.analyseSyntax();
        
        
//        String id = UUID.randomUUID().toString();
//        ArrayList<String> inherit = new ArrayList<>();
//        inherit.add("B");
//        inherit.add("C");
//        
//        ArrayList<String> pl = new ArrayList<>();
//        pl.add("int");
//        pl.add("float");
//        
//        ArrayList<String> pl2 = new ArrayList<>();
//        pl2.add("int");
//                
//        MT ref = new MT(id,"A","class","final",inherit);
//        System.out.println(Semantic.insertMT(ref));
//        System.out.println(ref);  //how to get reference?
//        String id2 = UUID.randomUUID().toString();
//        String id3 = UUID.randomUUID().toString();
//        String id4 = UUID.randomUUID().toString();
//        String id5 = UUID.randomUUID().toString();
//        System.out.println(Semantic.insertDT(new DT(id2,"a",null,"flt","public",false,false,false,ref.getID())));
//        System.out.println(Semantic.insertDT(new DT(id3,"b",null,"flt","public",false,false,false,ref.getID())));
//        System.out.println(Semantic.insertDT(new DT(id4,"c",pl,"flt","public",false,false,false,ref.getID())));
//        System.out.println(Semantic.insertDT(new DT(id5,"c",pl2,"flt","public",false,false,false,ref.getID())));
//        System.out.println(Semantic.lookupDT("a", "6a233fb9-9dc2-42a1-b04c-5d01a8395c1d"));
//        System.out.println(Semantic.lookupDT("c", "6a233fb9-9dc2-42a1-b04c-5d01a8395c1d",pl));
//        System.out.println("===========================================");
//        System.out.println(Semantic.lookupDT("c", "6a233fb9-9dc2-42a1-b04c-5d01a8395c1d",pl2));
    }
}
