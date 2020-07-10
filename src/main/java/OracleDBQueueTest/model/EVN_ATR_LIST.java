package OracleDBQueueTest.model;

public class EVN_ATR_LIST
{
    private T_EVN_ATR T_EVN_ATR;

    public T_EVN_ATR getT_EVN_ATR ()
    {
        return T_EVN_ATR;
    }

    public void setT_EVN_ATR (T_EVN_ATR T_EVN_ATR)
    {
        this.T_EVN_ATR = T_EVN_ATR;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T_EVN_ATR = "+T_EVN_ATR+"]";
    }
}
