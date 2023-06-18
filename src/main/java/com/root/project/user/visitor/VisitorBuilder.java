package com.root.project.user.visitor;

import java.util.function.Function;

public interface VisitorBuilder<R>{
    void register(Class<?> label, Function<Object,R> function);
}
