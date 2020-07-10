package OracleDBQueueTest.model;

public class T_SUB_CTG
{
    private String DATE_FROM;

    private String CATEGORY;

    private String CATEGORY_TYPE;

    private String DATE_TO;

    public String getDATE_FROM ()
    {
        return DATE_FROM;
    }

    public void setDATE_FROM (String DATE_FROM)
    {
        this.DATE_FROM = DATE_FROM;
    }

    public String getCATEGORY ()
    {
        return CATEGORY;
    }

    public void setCATEGORY (String CATEGORY)
    {
        this.CATEGORY = CATEGORY;
    }

    public String getCATEGORY_TYPE ()
    {
        return CATEGORY_TYPE;
    }

    public void setCATEGORY_TYPE (String CATEGORY_TYPE)
    {
        this.CATEGORY_TYPE = CATEGORY_TYPE;
    }

    public String getDATE_TO ()
    {
        return DATE_TO;
    }

    public void setDATE_TO (String DATE_TO)
    {
        this.DATE_TO = DATE_TO;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DATE_FROM = "+DATE_FROM+", CATEGORY = "+CATEGORY+", CATEGORY_TYPE = "+CATEGORY_TYPE+", DATE_TO = "+DATE_TO+"]";
    }
}