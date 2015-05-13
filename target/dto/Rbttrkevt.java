package org.openafp.ibm.cmod.dto;

public class Rbttrkevt {

    @DBField(value="RbtTrkEvtId", nullable=false)
    private Long rbttrkevtid = null;
	
    @DBField(value="UserId", nullable=false)
    private Long userid = null;
	
    @DBField(value="UserName", nullable=false)
    private String username = null;
	
    @DBField(value="IpAddr", nullable=false)
    private String ipaddr = null;
	
    @DBField(value="Browser", nullable=false)
    private String browser = null;
	
    @DBField(value="ServerIp", nullable=false)
    private String serverip = null;
	
    @DBField(value="EventType", nullable=false)
    private Integer eventtype = null;
	
    @DBField(value="EventValue", nullable=false)
    private String eventvalue = null;
	
    @DBField(value="EventLogDateTime", nullable=false, translator=SqlTypedValue.Timestamp)
    private java.sql.Timestamp eventlogdatetime = null;
	

    public Long getRbttrkevtid() {
        return rbttrkevtid;
    }
    
    public void setRbttrkevtid(Long rbttrkevtid) {
        this.rbttrkevtid = rbttrkevtid;
    }
    public Long getUserid() {
        return userid;
    }
    
    public void setUserid(Long userid) {
        this.userid = userid;
    }
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getIpaddr() {
        return ipaddr;
    }
    
    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }
    public String getBrowser() {
        return browser;
    }
    
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    public String getServerip() {
        return serverip;
    }
    
    public void setServerip(String serverip) {
        this.serverip = serverip;
    }
    public Integer getEventtype() {
        return eventtype;
    }
    
    public void setEventtype(Integer eventtype) {
        this.eventtype = eventtype;
    }
    public String getEventvalue() {
        return eventvalue;
    }
    
    public void setEventvalue(String eventvalue) {
        this.eventvalue = eventvalue;
    }
    public java.sql.Timestamp getEventlogdatetime() {
        return eventlogdatetime;
    }
    
    public void setEventlogdatetime(java.sql.Timestamp eventlogdatetime) {
        this.eventlogdatetime = eventlogdatetime;
    }
}