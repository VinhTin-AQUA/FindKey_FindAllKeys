
package Algorithms;

import Models.KeyModel;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class FindKey {
    private static TreeSet<String> k;
    private static LinkedList<String> list;
    private static String prop;

    public static String Find(KeyModel model) {
        list = new LinkedList<>(); // lưu những PTH đã được sử dụng
        k = new TreeSet<>();
        for (var i : model.getProps()) {
            k.add(i);
        }
        String concusion = "";
        int count = 0;
        int index = 0;
        int n = model.getProps().size();
        boolean check = true;
        while (count < n) {
            // thử loại 1 thuộc tính đầu tiên trong U để xem tập bao đóng có phải Q+ không
            TreeSet<String> lastTemp = new TreeSet<>(k);
            TreeSet<String> tempK = new TreeSet<>(createSubK(k, index));
            TreeSet<String> result = new TreeSet<>(tempK);
            String key = "";

            while (true) {
                key = findKey(model.getListF().keySet(), tempK);
                // tìm tập bao đóng
                TreeSet<String> QPlus = null;
                if (key.equals("")) {
                    QPlus = new TreeSet<>(tempK);
                } else {
                    QPlus = findKPlus(model.getListF().get(key), tempK);
                }
                if (QPlus.equals(model.getProps())) { // loại thuộc tính nếu K = Q+
                    k.clear();
                    k.addAll(result);
                    check = true;
                    key = "";
                    list.clear();
                    count++;
//                    System.out.println("Loai " + prop + " do (K-" + prop + ") = Q+ nen K=" + k);
                    concusion += "Loai " + prop + " do (K-" + prop + ") = Q+ nen K=" + k + " \n";
                    break;
                } else {            // tiếp tục tìm tập bao đóng
                    tempK.clear();
                    tempK.addAll(QPlus);
                    check = false;
                }
                if (key.equals("")) { // không thể tìm tập bao đóng được nữa và (K - Xi)+ != Q+
                    list.clear();
                    count++;
//                    System.out.println("Khong the loai " + prop + " do (K-" + prop + ") != Q+ nen K=" + k);
                    concusion += "Khong the loai " + prop + " do (K-" + prop + ") != Q+ nen K=" + k + "\n";
                    break;
                }
            }
            if (check == false) {
                k.clear();
                k.addAll(lastTemp);
                index++;
            }
        }
//        System.out.println("Vay khoa cua Q la:" + k);
        concusion += "Vay khoa cua Q la: " + k;
        return concusion;
    }

    // tìm vế trái của phụ thuộc hàm phù hợp
    private static String findKey(Set<String> keySet, TreeSet<String> tempK) {
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
    private static boolean checkContains(String keyModel, TreeSet<String> k) {
        int length = keyModel.length();
        char[] charArray = keyModel.toCharArray();
        for (int i = 0; i < length; i++) {
            if (k.contains(Character.toString(charArray[i])) == false) {
                return false;
            }
        }
        return true;
    }

    // tìm bao đóng của tập thuộc tính
    private static TreeSet<String> findKPlus(String value, TreeSet<String> tempK) {
        char[] charArray = value.toCharArray();
        int length = charArray.length;
        TreeSet<String> result = new TreeSet<>(tempK);
        for (int i = 0; i < length; i++) {
            result.add(Character.toString(charArray[i]));
        }
        return result;
    }

    // loại bỏ thuộc tính thứ index
    private static TreeSet<String> createSubK(TreeSet<String> k, int index) {
        TreeSet<String> temp = new TreeSet<>();
        int count = 0;
        for (var i : k) {
            if (count == index) {
                prop = i;
                count++;
                continue;
            }
            temp.add(i);
            count++;
        }
        return temp;
    }
}
