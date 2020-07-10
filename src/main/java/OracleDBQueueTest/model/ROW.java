package OracleDBQueueTest.model;

public class ROW
{
    private T_SUB_EVN T_SUB_EVN;

    private String XML_VERSION;

    public T_SUB_EVN getT_SUB_EVN ()
    {
        return T_SUB_EVN;
    }

    public void setT_SUB_EVN (T_SUB_EVN T_SUB_EVN)
    {
        this.T_SUB_EVN = T_SUB_EVN;
    }

    public String getXML_VERSION ()
    {
        return XML_VERSION;
    }

    public void setXML_VERSION (String XML_VERSION)
    {
        this.XML_VERSION = XML_VERSION;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [T_SUB_EVN = "+T_SUB_EVN+", XML_VERSION = "+XML_VERSION+"]";
    }
}