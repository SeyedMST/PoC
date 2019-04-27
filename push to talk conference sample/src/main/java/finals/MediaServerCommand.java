package finals;


// یک اینام
// این فقط انواع کامندهایی هست که به مدیاسرور داده میشه.
public enum MediaServerCommand {
	
	 
	
	ADD,REMOVE,PTTON,PTTOFF,ILLEGAL;
	
/*	private char dtmfMapping;
	
	private MediaServerCommand(char dtmfMapping) {
		this.dtmfMapping = dtmfMapping;
	}*/
	
	public boolean isLegal() {
		return this != ILLEGAL;		
	}
	

	// این قسمت هم برای جداسازی پیام‌هایSIP استفاده میشه.
	public static final String HEADER_SEPERATOR = "@";
	public static final String IP_PORT_SEPERATOR = ":";
}
