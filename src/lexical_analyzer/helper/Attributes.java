/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexical_analyzer.helper;

import java.util.ArrayList;
import lexical_analyzer.FDT;

/**
 *
 * @author Sualeha
 */
public class Attributes {

    // all extra attributes will go here
    public static ArrayList<String> pl;
    public static String pl_ret;  // arraybr for parameter list ret type
    public static String fn_ret; // arraybr2 return type or constructor name
    public static String abs_modifier;
    public static boolean return_st_flag = false;

    public static ArrayList<String> class_in;
    public static String class_tm = "";
    public static String class_t;
    public static String class_N;
    public static boolean FDT_flag = false;
    public static String[] T = new String[4];
    
    public static boolean rom_flag = false;
    public static boolean rom2_flag = false;
    
    
    public static FDT fff=null;
    
    
    public static String exp_R="";
    public static String exp_T1;
    public static String exp_op;
    
    public static boolean exp_obj = false;
    public static ArrayList<String> exp_pl = new ArrayList<String>();
    public static boolean inherit_chain_flag = false;
    public static boolean inherit_chain_flag2 = false;
    public static boolean inherit_chain_flag3 = false;
}
