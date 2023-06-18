package com.root.project.user.visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Visitor<R> {
    R visit(Object o);

    static <R> Visitor<R> of(Consumer<VisitorBuilder<R>> consumer){
        Map<Class<?>, Function<Object,R>> regsitry = new HashMap<>();
        consumer.accept((type, function) -> regsitry.put(type,function));  // visitor builder:: register
        return o-> regsitry.get(o.getClass()).apply(o);
    }

    static <R> X<R> forType(Class<?> type){
        return ()-> type;
    }

    interface  X<R> {
        Class<?> type();
        default  Consumer<VisitorBuilder<R>> execute(Function<Object, R> function){
            return  visitorBuilder -> visitorBuilder.register(type(),function);
        }
    }

    interface Y<R> extends Consumer<VisitorBuilder<R>>{
        default X<R> forType(Class<?> type){
            return ()-> type;
        }
    }
}
