package OracleDBQueueTest.model;

public class SUB_CTG_LIST
{
    private T_SUB_CTG T_SUB_CTG;

    public T_SUB_CTG getT_SUB_CTG ()
    {
        return T_SUB_CTG;
    }

    public void setT_SUB_CTG (T_SUB_CTG T_SUB_CTG)
    {
        this.T_SUB_CTG = T_SUB_CTG;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T_SUB_CTG = "+T_SUB_CTG+"]";
    }
}
	