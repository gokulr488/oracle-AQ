package OracleDBQueueTest.model;

public class ROWSET
{
    private ROW ROW;

    public ROW getROW ()
    {
        return ROW;
    }

    public void setROW (ROW ROW)
    {
        this.ROW = ROW;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ROW = "+ROW+"]";
    }
}
	