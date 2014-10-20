package com.ctrip.caird.common;
public interface Version {

    /**
     * Return whether or not the given version preceeded this one, succeeded it,
     * or is concurrant with it
     * 
     * @param v The other version
     */
    public Occurred compare(Version v);

}