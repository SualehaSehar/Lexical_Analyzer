
package lexical_analyzer;

import java.util.ArrayList;

public class MT {

    @Override
    public String toString() {
        return "MT{" + "ID=" + ID + ", name=" + name + ", type=" + type + ", tm=" + tm + ", inherit=" + inherit + '}';
    }
    
    private String ID;
    private String name;
    private String type;  
    private String tm;   // final or abstract
    private ArrayList<String> inherit;   

    public MT(String ID, String name, String type, String tm, ArrayList<String> inherit) {
        this.name = name;
        this.type = type;
        this.tm = tm;
        this.inherit = inherit;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTm() {
        return tm;
    }

    public ArrayList<String> getInherit() {
        return inherit;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTm(String tm) {
        this.tm = tm;
    }

    public void setInherit(ArrayList<String> inherit) {
        this.inherit = inherit;
    }

    

    
}

