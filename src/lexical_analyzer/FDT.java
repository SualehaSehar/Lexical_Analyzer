
package lexical_analyzer;

public class FDT {

    private String name;
    private String type;
    private String scope;

    public FDT(String name, String type, String scope) {
        this.name = name;
        this.type = type;
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "FDT{" + "name=" + name + ", type=" + type + ", scope=" + scope + '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getScope() {
        return scope;
    }

    
}


