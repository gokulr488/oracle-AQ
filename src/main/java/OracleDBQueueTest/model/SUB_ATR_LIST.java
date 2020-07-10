package OracleDBQueueTest.model;

public class SUB_ATR_LIST
{
    private T_SUB_ATR T_SUB_ATR;

    public T_SUB_ATR getT_SUB_ATR ()
    {
        return T_SUB_ATR;
    }

    public void setT_SUB_ATR (T_SUB_ATR T_SUB_ATR)
    {
        this.T_SUB_ATR = T_SUB_ATR;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T_SUB_ATR = "+T_SUB_ATR+"]";
    }
}
