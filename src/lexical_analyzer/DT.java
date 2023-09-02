
package lexical_analyzer;

import java.util.ArrayList;

public class DT {

    private String ID;
    private String name;
    private ArrayList<String> PL;
    private String type;
    private String am;
    private String staticc;
    private String finall;
    private String abstractt;
    private String ref;   
    private String className; 

    public DT(String ID, String name, ArrayList<String> PL,String type, String am, String staticc,
    String finall, String abstractt, String ref,String className) {
        this.name = name;
        this.type = type;
        this.PL = PL;
        this.am = am;
        this.staticc = staticc;
        this.finall = finall;
        this.abstractt = abstractt;
        this.ref = ref;
        this.ID = ID;
        this.className =className;
    }
    
    public DT(String ID, String name, String type, String am, String staticc,
    String finall, String abstractt, String ref,String className) {
        this.name = name;
        this.type = type;
        this.am = am;
        this.staticc = staticc;
        this.finall = finall;
        this.abstractt = abstractt;
        this.ref = ref;
        this.ID = ID;
        this.className =className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }


    @Override
    public String toString() {
        return "DT{" + "ID=" + ID + ", name=" + name + ", PL=" + PL + ", type=" + type + ", am=" + am + ", staticc=" + staticc + ", finall=" + finall + ", abstractt=" + abstractt + ", ref=" + ref + '}';
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPL() {
        return PL;
    }

    public String getType() {
        return type;
    }

    public String getAm() {
        return am;
    }

    public String getStaticc() {
        return staticc;
    }

    public String getFinall() {
        return finall;
    }

    public String getAbstractt() {
        return abstractt;
    }

    public String getRef() {
        return ref;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPL(ArrayList<String> PL) {
        this.PL = PL;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public void setStaticc(String staticc) {
        this.staticc = staticc;
    }

    public void setFinall(String finall) {
        this.finall = finall;
    }

    public void setAbstractt(String abstractt) {
        this.abstractt = abstractt;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    

    
     

    
}


