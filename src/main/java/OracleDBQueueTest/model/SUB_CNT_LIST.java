package OracleDBQueueTest.model;

public class SUB_CNT_LIST
{
    private T_SUB_CNT T_SUB_CNT;

    public T_SUB_CNT getT_SUB_CNT ()
    {
        return T_SUB_CNT;
    }

    public void setT_SUB_CNT (T_SUB_CNT T_SUB_CNT)
    {
        this.T_SUB_CNT = T_SUB_CNT;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T_SUB_CNT = "+T_SUB_CNT+"]";
    }
}
			
