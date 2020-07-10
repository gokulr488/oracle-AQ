package OracleDBQueueTest.model;

public class T_SUB_CNT
{
    private String DATE_FROM;

    private String TYPE;

    private String AREA_NETWORK_CODE;

    private String DIAL_NUMBER;

    private String COUNTRY_CODE;

    public String getDATE_FROM ()
    {
        return DATE_FROM;
    }

    public void setDATE_FROM (String DATE_FROM)
    {
        this.DATE_FROM = DATE_FROM;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

    public String getAREA_NETWORK_CODE ()
    {
        return AREA_NETWORK_CODE;
    }

    public void setAREA_NETWORK_CODE (String AREA_NETWORK_CODE)
    {
        this.AREA_NETWORK_CODE = AREA_NETWORK_CODE;
    }

    public String getDIAL_NUMBER ()
    {
        return DIAL_NUMBER;
    }

    public void setDIAL_NUMBER (String DIAL_NUMBER)
    {
        this.DIAL_NUMBER = DIAL_NUMBER;
    }

    public String getCOUNTRY_CODE ()
    {
        return COUNTRY_CODE;
    }

    public void setCOUNTRY_CODE (String COUNTRY_CODE)
    {
        this.COUNTRY_CODE = COUNTRY_CODE;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DATE_FROM = "+DATE_FROM+", TYPE = "+TYPE+", AREA_NETWORK_CODE = "+AREA_NETWORK_CODE+", DIAL_NUMBER = "+DIAL_NUMBER+", COUNTRY_CODE = "+COUNTRY_CODE+"]";
    }
}
			
		