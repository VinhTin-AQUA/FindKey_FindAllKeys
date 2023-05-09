
package Algorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;


public class FindAllKeys {
    private String TN; // tập nguồn
    private LinkedList<String> TG; // tập trung gian
    private LinkedList<String> Xi;// tập hợp con của tập trung gian
    private LinkedList<String> XiUSrc; // (Xi U Src)
    private LinkedList<String> XiUSrcPlus; // Tập bao đóng của (Xi U Src)+
    private LinkedList<String> SuperKeys; // danh sách siêu khóa
    private LinkedList<String> Keys; // danh sách các khóa
    private int minOfSPK; // độ dài thuộc tính của siêu khóa
    private static LinkedList<String> list;
    private final String NULL = " ";

    public FindAllKeys(TreeSet<String> props, HashMap<String, String> listF) {
        TN = "";
        TG = new LinkedList<>(); //
        Xi = new LinkedList<>(); //
        XiUSrc = new LinkedList<>(); // 
        XiUSrcPlus = new LinkedList<>(); //
        SuperKeys = new LinkedList<>(); // 
        Keys = new LinkedList<>(); // tim khoa
        list = new LinkedList<>();
        minOfSPK = props.size();

        findPropTN(props, listF);
        findPropTG(props, listF);
        findSubSet();
        findXiUTN();
        findXiUTNPlus(listF);
        findSuperKeys(props);
        findKeys();
    }

    public String getTN() {
        return TN;
    }

    public void setTN(String TN) {
        this.TN = TN;
    }

    public LinkedList<String> getTG() {
        return TG;
    }

    public void setTG(LinkedList<String> TG) {
        this.TG = TG;
    }

    public LinkedList<String> getXi() {
        return Xi;
    }

    public void setXi(LinkedList<String> Xi) {
        this.Xi = Xi;
    }

    public LinkedList<String> getXiUSrc() {
        return XiUSrc;
    }

    public void setXiUSrc(LinkedList<String> XiUSrc) {
        this.XiUSrc = XiUSrc;
    }

    public LinkedList<String> getXiUSrcPlus() {
        return XiUSrcPlus;
    }

    public void setXiUSrcPlus(LinkedList<String> XiUSrcPlus) {
        this.XiUSrcPlus = XiUSrcPlus;
    }

    public LinkedList<String> getSuperKeys() {
        return SuperKeys;
    }

    public void setSuperKeys(LinkedList<String> SuperKeys) {
        this.SuperKeys = SuperKeys;
    }

    public LinkedList<String> getKeys() {
        return Keys;
    }

    public void setKeys(LinkedList<String> Keys) {
        this.Keys = Keys;
    }

    // ===============================================================
    // tìm thuộc tính tập nguồn
    private void findPropTN(TreeSet<String> props, HashMap<String, String> listF) {
        for (var prop : props) {
            if (checkLeft(prop, listF) == true && checkRight(prop, listF) == false) {
                TN += prop;
            }
        }
        if (TN.equals("")) {
            TN = NULL;
        }
    }

    // tìm thuộc tính tập trung gian
    private void findPropTG(TreeSet<String> props, HashMap<String, String> listF) {
        for (var prop : props) {
            if (checkLeft(prop, listF) == true && checkRight(prop, listF) == true) {
                TG.add(prop);
            }
        }
    }

    // kiểm tra thuộc tính bên trái
    private boolean checkLeft(String prop, HashMap<String, String> listF) { // kiểm tra thuộc tính bên trái
        for (var key : listF.keySet()) {
            if (key.contains(prop)) {
                return true;
            }
        }
        return false;
    }

    // kiểm tra thuộc tính bên phải
    private boolean checkRight(String prop, HashMap<String, String> listF) {
        for (var value : listF.values()) {
            if (value.contains(prop)) {
                return true;
            }
        }
        return false;
    }

    // tìm tập con của tập trung gian
    private void findSubSet() {
        int n = TG.size();
        String Str = "";
        for (int i = 0; i < (1 << n); i++) {
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    Str += TG.get(j);
                }
            }
            if (Str.equals("")) {
                Xi.add(NULL);
            } else {
                Xi.add(Str);
            }
            Str = "";
        }
        Str = "";
        for (String element : TG) {
            Str += element;
        }
        Xi.add(Str);
    }

    // tim Xi U TN
    private void findXiUTN() {
        for (var xi : Xi) {
            String str = "";
            if (xi.equals(NULL) && TN.equals(NULL)) {
                str = NULL;
            } else if (xi.equals(NULL)) {
                str = TN;
            } else if (TN.equals(NULL)) {
                str = xi;
            } else {
                str = xi + TN;
            }
            XiUSrc.add(str);
        }
    }

    // tìm (Xi U TN)+
    private void findXiUTNPlus(HashMap<String, String> listF) {
        String QPlus = "";
        int count = listF.size();
        for (var item : XiUSrc) {
            int i = 0;
            String tempK = item;
            if (item.equals(NULL)) {
                XiUSrcPlus.add(NULL);
            } else {
                while (i <= count) {
                    String key = findKey(listF.keySet(), tempK);
                    if (key.equals("")) {
                        if (QPlus.equals("")) {
                            XiUSrcPlus.add(item);
                        } else {
                            if (QPlus.length() == minOfSPK) {
                                XiUSrcPlus.add("Q+");
                            } else {
                                XiUSrcPlus.add(QPlus);
                            }

                            QPlus = "";
                        }
                        break;
                    } else {
                        QPlus = findKPlus(listF.get(key), item, QPlus);
                        tempK = QPlus;
                    }
                    i++;
                }
            }
            list.clear();
        }
//        for(int i = 0; i < result.size(); i++) {
//            System.out.println(XiUSrc.get(i) + " : " + result.get((i)));
//        }
    }

    // tìm bao đóng của tập thuộc tính
    private String findKPlus(String value, String tempK, String QPlus) {
        char[] charArray = value.toCharArray();
        for (int i = 0; i < value.length(); i++) {
            if (QPlus.contains(Character.toString(charArray[i])) == false) {
                QPlus += Character.toString(charArray[i]);
            }
        }
        charArray = tempK.toCharArray();
        for (int i = 0; i < tempK.length(); i++) {
            if (QPlus.contains(Character.toString(charArray[i])) == false) {
                QPlus += Character.toString(charArray[i]);
            }
        }
        return QPlus;
    }

    // tìm vế trái của phụ thuộc hàm phù hợp
    private String findKey(Set<String> keySet, String tempK) {
        for (var i : keySet) {
            if (list.contains(i)) {
                continue;
            }
            if (checkContains(i, tempK)) {
                list.add(i);
                return i;
            }
        }
        return "";
    }

    // kiểm tra bên trái của phụ thuộc hàm có nằm trong K không
    private boolean checkContains(String keyModel, String k) {
        int length = keyModel.length();
        char[] charArray = keyModel.toCharArray();
        for (int i = 0; i < length; i++) {
            if (k.contains(Character.toString(charArray[i])) == false) {
                return false;
            }
        }
        return true;
    }

    // tìm siêu khóa
    private void findSuperKeys(TreeSet<String> props) {
        int n = XiUSrcPlus.size();
        for (int i = 0; i < n; i++) {

            if (XiUSrcPlus.get(i).equals("Q+")) {
                if (XiUSrc.get(i).length() < minOfSPK) {
                    minOfSPK = XiUSrc.get(i).length();
                }
                SuperKeys.add(XiUSrc.get(i));
            } else {
                SuperKeys.add(NULL);
            }
        }
    }

    // tìm khóa
    private void findKeys() {
        for (var item : SuperKeys) {
            if (item.length() == minOfSPK) {
                Keys.add(item);
            } else {
                Keys.add(NULL);
            }
        }
    }
}
