package edu.curtin.emergencysim;

public class AssTwoException extends Exception {
    public AssTwoException(String msg)
    {
        super(msg);
    }

    public AssTwoException(String msg, Throwable causeMsg)
    {
        super(msg, causeMsg);
    }
}
