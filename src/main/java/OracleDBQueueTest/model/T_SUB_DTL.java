package OracleDBQueueTest.model;

public class T_SUB_DTL
{
    private String INITIALS;

    private SUB_ADR_LIST SUB_ADR_LIST;

    private String TITLE_ID;

    private String SURNAME;

    private SUB_CNT_LIST SUB_CNT_LIST;

    private String BIRTH_DATE;

    private String LISTED_YN;

    private SUB_ID_LIST SUB_ID_LIST;

    private String GENDER;

    private SUB_ROL_LIST SUB_ROL_LIST;

    private String LANG_CODE;

    private String FIRST_NAME;

    public String getINITIALS ()
    {
        return INITIALS;
    }

    public void setINITIALS (String INITIALS)
    {
        this.INITIALS = INITIALS;
    }

    public SUB_ADR_LIST getSUB_ADR_LIST ()
    {
        return SUB_ADR_LIST;
    }

    public void setSUB_ADR_LIST (SUB_ADR_LIST SUB_ADR_LIST)
    {
        this.SUB_ADR_LIST = SUB_ADR_LIST;
    }

    public String getTITLE_ID ()
    {
        return TITLE_ID;
    }

    public void setTITLE_ID (String TITLE_ID)
    {
        this.TITLE_ID = TITLE_ID;
    }

    public String getSURNAME ()
    {
        return SURNAME;
    }

    public void setSURNAME (String SURNAME)
    {
        this.SURNAME = SURNAME;
    }

    public SUB_CNT_LIST getSUB_CNT_LIST ()
    {
        return SUB_CNT_LIST;
    }

    public void setSUB_CNT_LIST (SUB_CNT_LIST SUB_CNT_LIST)
    {
        this.SUB_CNT_LIST = SUB_CNT_LIST;
    }

    public String getBIRTH_DATE ()
    {
        return BIRTH_DATE;
    }

    public void setBIRTH_DATE (String BIRTH_DATE)
    {
        this.BIRTH_DATE = BIRTH_DATE;
    }

    public String getLISTED_YN ()
    {
        return LISTED_YN;
    }

    public void setLISTED_YN (String LISTED_YN)
    {
        this.LISTED_YN = LISTED_YN;
    }

    public SUB_ID_LIST getSUB_ID_LIST ()
    {
        return SUB_ID_LIST;
    }

    public void setSUB_ID_LIST (SUB_ID_LIST SUB_ID_LIST)
    {
        this.SUB_ID_LIST = SUB_ID_LIST;
    }

    public String getGENDER ()
    {
        return GENDER;
    }

    public void setGENDER (String GENDER)
    {
        this.GENDER = GENDER;
    }

    public SUB_ROL_LIST getSUB_ROL_LIST ()
    {
        return SUB_ROL_LIST;
    }

    public void setSUB_ROL_LIST (SUB_ROL_LIST SUB_ROL_LIST)
    {
        this.SUB_ROL_LIST = SUB_ROL_LIST;
    }

    public String getLANG_CODE ()
    {
        return LANG_CODE;
    }

    public void setLANG_CODE (String LANG_CODE)
    {
        this.LANG_CODE = LANG_CODE;
    }

    public String getFIRST_NAME ()
    {
        return FIRST_NAME;
    }

    public void setFIRST_NAME (String FIRST_NAME)
    {
        this.FIRST_NAME = FIRST_NAME;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [INITIALS = "+INITIALS+", SUB_ADR_LIST = "+SUB_ADR_LIST+", TITLE_ID = "+TITLE_ID+", SURNAME = "+SURNAME+", SUB_CNT_LIST = "+SUB_CNT_LIST+", BIRTH_DATE = "+BIRTH_DATE+", LISTED_YN = "+LISTED_YN+", SUB_ID_LIST = "+SUB_ID_LIST+", GENDER = "+GENDER+", SUB_ROL_LIST = "+SUB_ROL_LIST+", LANG_CODE = "+LANG_CODE+", FIRST_NAME = "+FIRST_NAME+"]";
    }
}
