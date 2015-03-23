package com.safira.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by francisco on 21/03/15.
 */
public class SafiraUtils {
    public static <E> Collection<E> toCollection(Iterable<E> iterable) {
        Collection<E> list = new ArrayList<>();
        for (E item : iterable) {
            list.add(item);
        }
        return list;
    }

    public static <E> List<E> toList(Iterable<E> iterable) {
        if (iterable instanceof List) {
            return (List<E>) iterable;
        }
        ArrayList<E> list = new ArrayList<>();
        if (iterable != null) {
            for (E e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
}
