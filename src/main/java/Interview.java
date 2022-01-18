import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class Interview {

    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> ar = new ArrayList<>();

        for (Object o : elements) {
            try{
                ar.add((T)o);
            }catch (ClassCastException e){}
        }
        return ar;
    }


    public static void main(String[] args) {

    }
}
 class Algorithm {
    public static <T> void max(T ... ts) {
        T temp = ts[0];
        ts[0] = ts[1];
        ts[1] = temp;
    }
}

