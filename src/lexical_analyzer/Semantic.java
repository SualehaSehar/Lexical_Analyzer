package lexical_analyzer;

import java.sql.*;
import java.util.*;
import java.util.Stack;
import static lexical_analyzer.helper.TokenHelper.identifier;

public class Semantic {

    //static Stack stack = new Stack();
    public static int scop = 1;
    public static ArrayList<Integer> stack = new ArrayList<Integer>();

    public static MT lookupMT(String n) {
        String query = "select * from MainTable where name = '" + n + "'";
        ArrayList<String> inherit = new ArrayList<>();
        MT MTrow = null;
        try {
            ResultSet rs = SQL.execute(query);
            if (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                String tm = rs.getString("tm");
                String id = rs.getString("ID");

                String query2 = "select * from MInherit where mtid = '" + id + "'";
                ResultSet rs2 = SQL.execute(query2);
                if (rs2.next()) {
                    do {
                        String typee = rs2.getString("name");
                        inherit.add(typee);
                    } while (rs2.next());
                } else {
                    inherit = null;
                }

                MTrow = new MT(id, name, type, tm, inherit);
            } else {
                MTrow = null;
                return MTrow;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return MTrow;
    }

    public static DT lookupDT(String n, String reff) {
        String query = "select * from DataTable where name = '" + n + "' and ref = '" + reff + "'";
        DT DTrow = null;
        try {
            ResultSet rs = SQL.execute(query);

            if (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("ID");
                String type = rs.getString("type");
                String am = rs.getString("am");
                String staticc = rs.getString("static");
                String finall = rs.getString("final");
                String abstractt = rs.getString("abstract");
                String ref = rs.getString("ref");
                String ClassName = rs.getString("ClassName");

                String query2 = "select * from PL where dtid = '" + id + "'";
                ResultSet rs2 = SQL.execute(query2);
                if (rs2.next()) {
                    return DTrow;
                }
                DTrow = new DT(id, name, type, am, staticc, finall, abstractt, ref, ClassName);
            } else {
                return DTrow;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return DTrow;
    }

    public static DT lookupDT(String n, String reff, ArrayList<String> PList) {
        String query = "select * from DataTable where name = '" + n + "' and ref = '" + reff + "'";
        DT DTrow = null;
        try {
            int count = 0;
            boolean fl = false;
            ResultSet rs = SQL.execute(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String id = rs.getString("ID");
                String type = rs.getString("type");
                String am = rs.getString("am");
                String staticc = rs.getString("static");
                String finall = rs.getString("final");
                String abstractt = rs.getString("abstract");
                String ref = rs.getString("ref");
                String ClassName = rs.getString("ClassName");
                ArrayList<String> ll = new ArrayList<>();

                String query2 = "select * from PL where dtid = '" + id + "'";
                ResultSet rs2 = SQL.execute(query2);
                if (rs2.next()) {
                    do {
                        String typee = rs2.getString("type");
                        ll.add(typee);
                    } while (rs2.next());
                } else {
                    if (PList.isEmpty()) {
                        ll = null;
                    } else {
                        continue;
                    }
                }

                //System.out.println("xsnkln " + name + " " + ll);
                if (ll != null && PList != null) {
                    if (Integer.toString(PList.size()).equals(Integer.toString(ll.size()))) {
                        for (int i = 0; i < PList.size(); i++) {
                            if (!ll.get(i).equals(PList.get(i))) {
                                fl = false;
                                break;
                            } else {
                                fl = true;
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    fl = true;
                }

                //System.out.println(fl);
                if (fl) {
                    DTrow = new DT(id, name, ll, type, am, staticc, finall, abstractt, ref, ClassName);
                    break;
                } else {
                    DTrow = null;
                }

            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return DTrow;
    }

    public static FDT lookupFDT(String n, String sc) {
        String query = "select * from FunctionDataTable where name = '" + n + "' and scope = '" + sc + "'";
        FDT FDTrow = null;
        try {
            ResultSet rs = SQL.execute(query);
            if (rs.next()) {
                String name = rs.getString("name");
                String type = rs.getString("type");
                String scope = rs.getString("scope");

                FDTrow = new FDT(name, type, scope);

            } else {
                FDTrow = null;
                return FDTrow;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return FDTrow;
    }

    public static boolean insertMT(MT m) {
        boolean flag = false;

        String id = m.getID();
        String name = m.getName();
        String type = m.getType();
        String tm = m.getTm();
        ArrayList<String> inherit = m.getInherit();

        if (lookupMT(name) == null) {

            String query = "insert into MainTable(ID,name,type,tm)"
                    + "values ('" + id + "','" + name + "','" + type + "','" + tm + "')";

            try {
                SQL.executeUpdate(query);

                if (inherit != null) {
                    for (int i = 0; i < inherit.size(); i++) {
                        String query2 = "insert into MInherit(mtid,name)"
                                + "values ('" + id + "','" + inherit.get(i) + "')";
                        SQL.executeUpdate(query2);
                    }
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean insertDT(DT d) {
        boolean flag = false;

        String id = d.getID();
        String name = d.getName();
        String type = d.getType();
        String am = d.getAm();
        ArrayList<String> pl = d.getPL();
        String staticc = d.getStaticc();
        String finall = d.getFinall();
        String abstractt = d.getAbstractt();
        String ref = d.getRef();
        String cn = d.getClassName();

        if (lookupDT(name, ref, pl) == null && lookupDT(name, ref) == null) {

            String query = "insert into DataTable(ID,name,type,am,static,final,abstract,ref,ClassName)"
                    + "values ('" + id + "','" + name + "','" + type + "','" + am + "','" + staticc + "','" + finall + "','" + abstractt + "','" + ref + "','" + cn + "')";

            try {
                SQL.executeUpdate(query);

                if (pl != null) {
                    for (int i = 0; i < pl.size(); i++) {
                        String query2 = "insert into PL(dtid,type)"
                                + "values ('" + id + "','" + pl.get(i) + "')";
                        SQL.executeUpdate(query2);
                    }
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static boolean insertFDT(FDT f) {
        boolean flag = false;
        String name = f.getName();
        String type = f.getType();
        String scope = f.getScope();

        if (lookupFDT(name, scope) == null) {

            String query = "insert into FunctionDataTable(name,type,scope)"
                    + "values ('" + name + "','" + type + "','" + scope + "')";

            try {
                SQL.executeUpdate(query);

                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    public static void createScope() {
        stack.add(scop);
        scop++;
    }

    public static void destroyScope() {
        //System.out.println("destroyeeddd");
        if (stack.size() > 1) {
            int s = stack.remove(stack.size() - 1);
        }else if(stack.size() == 1){
            int s = stack.remove(0);
        }
    }

    public static String peekScope() {
        String s = null;
        if (stack.size() > 1) {
            s = Integer.toString(stack.get(stack.size() - 1));
        }else if(stack.size() == 1){
            s = Integer.toString(stack.get(0));
        }
        
        //String s = Integer.toString(stack.get(stack.size() - 1));
        return s;
    }

    public static String compatibilityByLRO(String leftT, String rightT, String op) {
        String rt = "";
        if (leftT.equals("int") && rightT.equals("int")) {
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%") || op.equals("=")) {
                rt = "int";
            }
        } else if (leftT.equals("float") && rightT.equals("float")) {
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("=")) {
                rt = "float";
            }
        } else if (leftT.equals("float") && rightT.equals("int")) {
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("=")) {
                rt = "float";
            }
        } else if (leftT.equals("int") && rightT.equals("float")) {
            if (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")) {
                rt = "float";
            }
        } else if (leftT.equals("bool") && rightT.equals("bool")) {
            if (op.equals("#") || op.equals("||") || op.equals("=")) {
                rt = "bool";
            }
        } else if (leftT.equals("char") && rightT.equals("char")) {
            if (op.equals("+")) {
                rt = "char";
            }
        } else if (leftT.equals("String") && rightT.equals("String")) {
            if (op.equals("+") || op.equals("=")) {
                rt = "String";
            }
        } else if (leftT.equals("char") && rightT.equals("String") || leftT.equals("String") && rightT.equals("char")) {
            if (op.equals("+")) {
                rt = "String";
            }
        }
        if (!leftT.equals("char") || !leftT.equals("String") || !leftT.equals("bool") || !leftT.equals("float") || !leftT.equals("int")
                || !rightT.equals("String") || !rightT.equals("char") || !rightT.equals("bool") || !rightT.equals("float") || !rightT.equals("int")) {
            //if (identifier(leftT) && identifier(rightT)) {
            if (leftT.equals(rightT)) {
                if (op.equals("=")) {
                    rt = leftT;
                }
            }
            //}
        } else {
            System.out.println("comp " + leftT);
            System.out.println("comp " + rightT);
            System.out.println("comp " + rt);
        }
        return rt;
    }

    public static boolean compatibilityBy(String leftT, String rightT) {
        boolean rt = false;
        if (leftT.equals("int") && rightT.equals("int") || leftT.equals("float") && rightT.equals("float") || leftT.equals("float") && rightT.equals("int")
                || leftT.equals("bool") && rightT.equals("bool") || leftT.equals("char") && rightT.equals("char")
                || leftT.equals("String") && rightT.equals("String")) {
            rt = true;

        } else {
            System.out.println("comp " + leftT);
            System.out.println("comp " + rightT);
            System.out.println("comp " + rt);
        }
        return rt;
    }

    public static void deleteData() {
        String qu = "delete from MainTable";
        String qu1 = "delete from DataTable";
        String qu2 = "delete from FunctionDataTable";
        String qu3 = "delete from PL";
        String qu4 = "delete from MInherit";
        try {
            SQL.delete(qu);
            SQL.delete(qu1);
            SQL.delete(qu2);
            SQL.delete(qu3);
            SQL.delete(qu4);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
