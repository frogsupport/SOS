package com.sos;

// Class to manage line coordinates
public class Triplet<T, T1, T2> {
    private T First;
    private T1 Second;
    private T2 Third;

    Triplet(T first, T1 second, T2 third) {
        First = first;
        Second = second;
        Third = third;
    }

    public T getFirst() {
        return First;
    }

    public T1 getSecond() {
        return Second;
    }

    public T2 getThird() {return Third;}
}
