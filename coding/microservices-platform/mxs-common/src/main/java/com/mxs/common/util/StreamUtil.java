package com.mxs.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by j.yang on 2019-06-17.
 */
public class StreamUtil {
    public static <T> Stream<T> getStream(List<T> list) {
        return Optional.ofNullable(list).orElse(new ArrayList<T>()).stream();
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        return Optional.ofNullable(list).orElse(new ArrayList<T>()).stream().map(function).collect(Collectors.toList());
    }
}
