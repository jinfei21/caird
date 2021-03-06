package com.ctrip.caird.common;
import java.io.Serializable;
import java.util.Comparator;

import com.ctrip.caird.util.Utils;
import com.google.common.base.Objects;

/**
 * A wrapper for an object that adds a Version.
 * 
 * 
 */
public final class Versioned<T> implements Serializable {

    private static final long serialVersionUID = 1;

    private final VectorClock version;
    private volatile T object;

    public Versioned(T object) {
        this(object, new VectorClock());
    }

    public Versioned(T object, Version version) {
        this.version = version == null ? new VectorClock() : (VectorClock) version;
        this.object = object;
    }

    public Version getVersion() {
        return version;
    }

    public T getValue() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if(o == this)
            return true;
        else if(!(o instanceof Versioned<?>))
            return false;

        Versioned<?> versioned = (Versioned<?>) o;
        return Objects.equal(getVersion(), versioned.getVersion())
               && Utils.deepEquals(getValue(), versioned.getValue());
    }

    @Override
    public int hashCode() {
        int value = 31 + version.hashCode();
        if(object != null) {
            value += 31 * object.hashCode();
        }
        return value;
    }

    @Override
    public String toString() {
        return "[" + object + ", " + version + "]";
    }

    /**
     * Create a clone of this Versioned object such that the object pointed to
     * is the same, but the VectorClock and Versioned wrapper is a shallow copy.
     */
    public Versioned<T> cloneVersioned() {
        return new Versioned<T>(this.getValue(), this.version.clone());
    }

    public static <S> Versioned<S> value(S s) {
        return new Versioned<S>(s, new VectorClock());
    }

    public static <S> Versioned<S> value(S s, Version v) {
        return new Versioned<S>(s, v);
    }

    public static final class HappenedBeforeComparator<S> implements Comparator<Versioned<S>> {

        public int compare(Versioned<S> v1, Versioned<S> v2) {
            Occurred occurred = v1.getVersion().compare(v2.getVersion());
            if(occurred == Occurred.BEFORE)
                return -1;
            else if(occurred == Occurred.AFTER)
                return 1;
            else
                return 0;
        }
    }

}
