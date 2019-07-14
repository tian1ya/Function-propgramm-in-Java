package chap4_reverse_recursive_and_memorization;

import java.util.List;

import static common.CollectionUtilities.head;
import static common.CollectionUtilities.tail;

public class recurrent {
    static Integer sumV1(List<Integer> list){
        return list.isEmpty()
                ? 0
                : head(list) + sumV1(tail(list));
    }

    static Integer sumV2(List<Integer> list, int acc){
        return list.isEmpty()
                ? acc
                : sumV2(tail(list), acc + head(list));
    }


}
