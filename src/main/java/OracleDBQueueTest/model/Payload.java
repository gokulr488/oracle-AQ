package OracleDBQueueTest.model;

public class Payload
{
    private ROWSET ROWSET;

    public ROWSET getROWSET ()
    {
        return ROWSET;
    }

    public void setROWSET (ROWSET ROWSET)
    {
        this.ROWSET = ROWSET;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ROWSET = "+ROWSET+"]";
    }
}