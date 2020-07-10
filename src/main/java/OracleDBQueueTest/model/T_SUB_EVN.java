package OracleDBQueueTest.model;

public class T_SUB_EVN
{
    private String BUS_EVENT_TYPE;

    private String EVENT_DATE;

    private SUB_ATR_LIST SUB_ATR_LIST;

    private String ICAP_ID;

    private String EVENT_TYPE;

    private SUB_DTL_LIST SUB_DTL_LIST;

    private String CUSTOMER_ID;

    private SUB_CLL_LIST SUB_CLL_LIST;

    private String ICAP_EVENT_SEQ;

    private String EVENT_SEQ;

    private String REF_NO;

    private SUB_SRV_LIST SUB_SRV_LIST;

    private SUB_CTG_LIST SUB_CTG_LIST;

    private String REQUEST_SEQ;

    private EVN_ATR_LIST EVN_ATR_LIST;

    public String getBUS_EVENT_TYPE ()
    {
        return BUS_EVENT_TYPE;
    }

    public void setBUS_EVENT_TYPE (String BUS_EVENT_TYPE)
    {
        this.BUS_EVENT_TYPE = BUS_EVENT_TYPE;
    }

    public String getEVENT_DATE ()
    {
        return EVENT_DATE;
    }

    public void setEVENT_DATE (String EVENT_DATE)
    {
        this.EVENT_DATE = EVENT_DATE;
    }

    public SUB_ATR_LIST getSUB_ATR_LIST ()
    {
        return SUB_ATR_LIST;
    }

    public void setSUB_ATR_LIST (SUB_ATR_LIST SUB_ATR_LIST)
    {
        this.SUB_ATR_LIST = SUB_ATR_LIST;
    }

    public String getICAP_ID ()
    {
        return ICAP_ID;
    }

    public void setICAP_ID (String ICAP_ID)
    {
        this.ICAP_ID = ICAP_ID;
    }

    public String getEVENT_TYPE ()
    {
        return EVENT_TYPE;
    }

    public void setEVENT_TYPE (String EVENT_TYPE)
    {
        this.EVENT_TYPE = EVENT_TYPE;
    }

    public SUB_DTL_LIST getSUB_DTL_LIST ()
    {
        return SUB_DTL_LIST;
    }

    public void setSUB_DTL_LIST (SUB_DTL_LIST SUB_DTL_LIST)
    {
        this.SUB_DTL_LIST = SUB_DTL_LIST;
    }

    public String getCUSTOMER_ID ()
    {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID (String CUSTOMER_ID)
    {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public SUB_CLL_LIST getSUB_CLL_LIST ()
    {
        return SUB_CLL_LIST;
    }

    public void setSUB_CLL_LIST (SUB_CLL_LIST SUB_CLL_LIST)
    {
        this.SUB_CLL_LIST = SUB_CLL_LIST;
    }

    public String getICAP_EVENT_SEQ ()
    {
        return ICAP_EVENT_SEQ;
    }

    public void setICAP_EVENT_SEQ (String ICAP_EVENT_SEQ)
    {
        this.ICAP_EVENT_SEQ = ICAP_EVENT_SEQ;
    }

    public String getEVENT_SEQ ()
    {
        return EVENT_SEQ;
    }

    public void setEVENT_SEQ (String EVENT_SEQ)
    {
        this.EVENT_SEQ = EVENT_SEQ;
    }

    public String getREF_NO ()
    {
        return REF_NO;
    }

    public void setREF_NO (String REF_NO)
    {
        this.REF_NO = REF_NO;
    }

    public SUB_SRV_LIST getSUB_SRV_LIST ()
    {
        return SUB_SRV_LIST;
    }

    public void setSUB_SRV_LIST (SUB_SRV_LIST SUB_SRV_LIST)
    {
        this.SUB_SRV_LIST = SUB_SRV_LIST;
    }

    public SUB_CTG_LIST getSUB_CTG_LIST ()
    {
        return SUB_CTG_LIST;
    }

    public void setSUB_CTG_LIST (SUB_CTG_LIST SUB_CTG_LIST)
    {
        this.SUB_CTG_LIST = SUB_CTG_LIST;
    }

    public String getREQUEST_SEQ ()
    {
        return REQUEST_SEQ;
    }

    public void setREQUEST_SEQ (String REQUEST_SEQ)
    {
        this.REQUEST_SEQ = REQUEST_SEQ;
    }

    public EVN_ATR_LIST getEVN_ATR_LIST ()
    {
        return EVN_ATR_LIST;
    }

    public void setEVN_ATR_LIST (EVN_ATR_LIST EVN_ATR_LIST)
    {
        this.EVN_ATR_LIST = EVN_ATR_LIST;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [BUS_EVENT_TYPE = "+BUS_EVENT_TYPE+", EVENT_DATE = "+EVENT_DATE+", SUB_ATR_LIST = "+SUB_ATR_LIST+", ICAP_ID = "+ICAP_ID+", EVENT_TYPE = "+EVENT_TYPE+", SUB_DTL_LIST = "+SUB_DTL_LIST+", CUSTOMER_ID = "+CUSTOMER_ID+", SUB_CLL_LIST = "+SUB_CLL_LIST+", ICAP_EVENT_SEQ = "+ICAP_EVENT_SEQ+", EVENT_SEQ = "+EVENT_SEQ+", REF_NO = "+REF_NO+", SUB_SRV_LIST = "+SUB_SRV_LIST+", SUB_CTG_LIST = "+SUB_CTG_LIST+", REQUEST_SEQ = "+REQUEST_SEQ+", EVN_ATR_LIST = "+EVN_ATR_LIST+"]";
    }
}