package OracleDBQueueTest.model;

public class T_SRV_ID
{
    private String END_DATE;

    private String START_DATE;

    private String ID;

    private String ID_TYPE;

    public String getEND_DATE ()
    {
        return END_DATE;
    }

    public void setEND_DATE (String END_DATE)
    {
        this.END_DATE = END_DATE;
    }

    public String getSTART_DATE ()
    {
        return START_DATE;
    }

    public void setSTART_DATE (String START_DATE)
    {
        this.START_DATE = START_DATE;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getID_TYPE ()
    {
        return ID_TYPE;
    }

    public void setID_TYPE (String ID_TYPE)
    {
        this.ID_TYPE = ID_TYPE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [END_DATE = "+END_DATE+", START_DATE = "+START_DATE+", ID = "+ID+", ID_TYPE = "+ID_TYPE+"]";
    }
}
		