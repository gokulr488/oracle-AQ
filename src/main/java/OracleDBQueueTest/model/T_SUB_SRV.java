package OracleDBQueueTest.model;

public class T_SUB_SRV
{
    private String ST_ID;

    private String DATE_FROM;

    private SUB_SRV_ATR_LIST SUB_SRV_ATR_LIST;

    private String LOCK_YN;

    private String ADSL_ENABLED_YN;

    private String SO_ID;

    private SUB_SRV_ID_LIST SUB_SRV_ID_LIST;

    private SUB_SRV_NE_LIST SUB_SRV_NE_LIST;

    private String O_ID;

    private String DATE_TO;

    public String getST_ID ()
    {
        return ST_ID;
    }

    public void setST_ID (String ST_ID)
    {
        this.ST_ID = ST_ID;
    }

    public String getDATE_FROM ()
    {
        return DATE_FROM;
    }

    public void setDATE_FROM (String DATE_FROM)
    {
        this.DATE_FROM = DATE_FROM;
    }

    public SUB_SRV_ATR_LIST getSUB_SRV_ATR_LIST ()
    {
        return SUB_SRV_ATR_LIST;
    }

    public void setSUB_SRV_ATR_LIST (SUB_SRV_ATR_LIST SUB_SRV_ATR_LIST)
    {
        this.SUB_SRV_ATR_LIST = SUB_SRV_ATR_LIST;
    }

    public String getLOCK_YN ()
    {
        return LOCK_YN;
    }

    public void setLOCK_YN (String LOCK_YN)
    {
        this.LOCK_YN = LOCK_YN;
    }

    public String getADSL_ENABLED_YN ()
    {
        return ADSL_ENABLED_YN;
    }

    public void setADSL_ENABLED_YN (String ADSL_ENABLED_YN)
    {
        this.ADSL_ENABLED_YN = ADSL_ENABLED_YN;
    }

    public String getSO_ID ()
    {
        return SO_ID;
    }

    public void setSO_ID (String SO_ID)
    {
        this.SO_ID = SO_ID;
    }

    public SUB_SRV_ID_LIST getSUB_SRV_ID_LIST ()
    {
        return SUB_SRV_ID_LIST;
    }

    public void setSUB_SRV_ID_LIST (SUB_SRV_ID_LIST SUB_SRV_ID_LIST)
    {
        this.SUB_SRV_ID_LIST = SUB_SRV_ID_LIST;
    }

    public SUB_SRV_NE_LIST getSUB_SRV_NE_LIST ()
    {
        return SUB_SRV_NE_LIST;
    }

    public void setSUB_SRV_NE_LIST (SUB_SRV_NE_LIST SUB_SRV_NE_LIST)
    {
        this.SUB_SRV_NE_LIST = SUB_SRV_NE_LIST;
    }

    public String getO_ID ()
    {
        return O_ID;
    }

    public void setO_ID (String O_ID)
    {
        this.O_ID = O_ID;
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
        return "ClassPojo [ST_ID = "+ST_ID+", DATE_FROM = "+DATE_FROM+", SUB_SRV_ATR_LIST = "+SUB_SRV_ATR_LIST+", LOCK_YN = "+LOCK_YN+", ADSL_ENABLED_YN = "+ADSL_ENABLED_YN+", SO_ID = "+SO_ID+", SUB_SRV_ID_LIST = "+SUB_SRV_ID_LIST+", SUB_SRV_NE_LIST = "+SUB_SRV_NE_LIST+", O_ID = "+O_ID+", DATE_TO = "+DATE_TO+"]";
    }
}