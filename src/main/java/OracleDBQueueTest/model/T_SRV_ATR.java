package OracleDBQueueTest.model;
public class T_SRV_ATR
{
    private String VALUE;

    private String NAME;

    public String getVALUE ()
    {
        return VALUE;
    }

    public void setVALUE (String VALUE)
    {
        this.VALUE = VALUE;
    }

    public String getNAME ()
    {
        return NAME;
    }

    public void setNAME (String NAME)
    {
        this.NAME = NAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VALUE = "+VALUE+", NAME = "+NAME+"]";
    }
}
			
	