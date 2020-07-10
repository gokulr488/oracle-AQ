package OracleDBQueueTest.model;

public class T_SRV_NE
{
    private String NE_TYPE;

    private String NE_ID;

    public String getNE_TYPE ()
    {
        return NE_TYPE;
    }

    public void setNE_TYPE (String NE_TYPE)
    {
        this.NE_TYPE = NE_TYPE;
    }

    public String getNE_ID ()
    {
        return NE_ID;
    }

    public void setNE_ID (String NE_ID)
    {
        this.NE_ID = NE_ID;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [NE_TYPE = "+NE_TYPE+", NE_ID = "+NE_ID+"]";
    }
}
