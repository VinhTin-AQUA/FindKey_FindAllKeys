
package Models;

import java.util.HashMap;
import java.util.TreeSet;

public class KeyModel {
    private TreeSet<String> props;
    private HashMap<String, String> listF;

    public KeyModel() {
        props = new TreeSet<>();
        listF = new HashMap<>();
    }

    
    public KeyModel(TreeSet<String> listProps, HashMap<String, String> listF) {
        this.props = listProps;
        this.listF = listF;
    }

    public TreeSet<String> getProps() {
        return props;
    }

    public void setProps(TreeSet<String> listProps) {
        this.props = listProps;
    }

    public HashMap<String, String> getListF() {
        return listF;
    }

    public void setListF(HashMap<String, String> listF) {
        this.listF = listF;
    }
    
    public void addKeyValue(String key, String value) {
        listF.put(key,value);
    }
    
    public void clear() {
        props.clear();
        listF.clear();
    }
}
