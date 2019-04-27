package finals;



// اینترفیس ارتباط Sip Servlet با Media Replicator
public interface MediaServerFinals {
	// آی پی Media Replicator
	public static final String MEDIA_REPLICATOR_IP = "10.10.5.32";
	// پورت media replicator مخصوص کنفرانس
	public static final int MEDIA_REPLICATOR_CONF_PORT = 50001;
	// پورت midia_replicator مخصوص پیام‌های RTP
	public static final int MEDIA_REPLICATOR_RTP_PORT = 50000;

	public static final char NON_VALID_CHAR = '0'; 
}
