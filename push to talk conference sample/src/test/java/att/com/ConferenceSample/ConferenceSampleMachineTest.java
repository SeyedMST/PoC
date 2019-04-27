
// $Name:  $ $Id: $

package att.com.ConferenceSample;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Formatter;
import java.util.Locale;
import java.util.Properties;

import org.echarts.test.sip.*;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test; //Import Before, Test, After annotations
import org.junit.Ignore;

import static org.echarts.test.sip.CATMatchers.*;
import static org.hamcrest.Matchers.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;



// این کلاس از کلاسهای خود E4ss ارث بری کرده و قابلیتهای اون رو هم اکستند کرده.
public final class ConferenceSampleMachineTest extends org.echarts.test.sip.CATTestCase {
    /** Version control identifier strings. */
    public static final String[] RCS_ID = {
        "$URL$",
        "$Id$",
    };

	String appServer;
	String httpServer;
	String appName;
	int sipListenPort;
	String sipListenAddress;
	String outputDir;
	Logger logger;
	int remoteListenPort;

	// کانستراکتور کلاس.
	public ConferenceSampleMachineTest() throws Exception {
		logger = this.getLogger(); //این تابع getLogger برای خود E4SS هستش.
		Properties props = new Properties(); // java.util.properties برای خود جاوا هستش.
		try {
			// Using getResourceAsStream(...) will read the properties file
			// copied from src/test/resources
			//
			// props.load(new FileInputStream("test.properties"));
			//
			File propertiesFile = new File("test.properties"); //تو test/resources تعریف شده
			if (propertiesFile.exists()) {
				props.load(new FileInputStream(propertiesFile)); // فایل properties رو لود میکنه.
			}
			else {
				props.load(
					ConferenceSampleMachineTest.class.getResourceAsStream(
					"/test.properties"));
			}

			// Allow properties to be overriden by system properties. This
			// is primarily to simplify configuration from a maven pom.xml
			// file.
			props.putAll(System.getProperties());

			// اینها همه تو test.properties باید ست بشن:
			appServer           = props.getProperty("SipAS");
			httpServer          = props.getProperty("HttpAS");
			sipListenPort       = Integer.parseInt(props.getProperty("SipStackListenPort"));
			// اولین آرگومان ورودی میگه اگه این تو فایل بود مقدارش رو بده و دومی میگه اگه نبود این مقدار دیفالته
			sipListenAddress    = props.getProperty("SipStackListenIP", InetAddress.getLocalHost().getHostAddress());
			outputDir           = props.getProperty("OutputDir", "out");
			appName             = props.getProperty("AppName", "ConferenceSampleTest");
			// تابع داخلی همین کلاسه.
			init("ConferenceSampleTest");
		}
		catch (Exception e) {
			logger.error("error loading/reading test configuration file", e);
			throw e;
		}
	}

	private void init(String testName) throws CATException {
		//این CATCOonfig تو خود E4ss و پکیج org.echarts.test.sipتعریف شده.
		CATConfig config = new CATConfig();
		config.setListenIP(this.sipListenAddress);
		config.setListenPort(this.sipListenPort);
		config.setTestName(testName);
		config.setOutputDir(outputDir);

		// این init تابع کلاس پدره(واسه E4SS) که CATConfig ورودی میگیره و کانفیگ رو ست میکنه.
		this.init(config);
	}


	// ‌این @BeforeClass , @Before, @test همه تو org.junit هستن.
	// Before -> test -> After

	// این علامت @BeforeClass نشون میده که این تابع runOnceBofreAllTests همیشه باید
	// قبل از اینکه یه نمونه از کلاس ساخته بشه،‌اجرا بشه.
	@BeforeClass static public void runOnceBeforeAllTests() {
	}
	// این هم مثل بالایی فقط بعد از اینکه کلاس نال شد و گاربج کالکتور، صدا زده شد.
	@AfterClass static public void runOnceAfterAllTests() {
	}

	// فقط تو لاگ مینیوسه که داریم تست میگیریم.
	@Before public void runBeforeEachTest() {
		try {
			// دستور logger.info تو لاگ فایل مینویسه.
			logger.info("running test setup");
		}
		catch (Exception e) {
			logger.error("test setup failed", e);
		}
	}

	// میگه کار تموم شد و بعد منابع رو آزاد میکنه.
	@After public void runAfterEachTest() {
		try {
			logger.info("running test cleanup");
			this.release();
		}
		catch (Exception e) {
			logger.error("test cleanup failed", e);
		}
		logger.info("====================");
	}

	// اینم تابع تست که قراره یه تماس رو به صورت کامل تست کنه. البته پیام صوتی منتقل نمیشه.
	// و به محض اینکه طرف جواب بده، تلفن قطع میشه. یعنی فقط SIP فقط بررسی شده و RTP و  هنوز تست نشده.
	/** Test of a completed call from caller to callee.  
	 *  Caller ends call after callee answers.  RTP is
	 *  not tested.
	 * 
	 * @throws Throwable
	 */
	@Test public void testCompletedCall() throws Throwable {
		try {
			// مقدار دهی اولیه پارامترها!‌ البته نیازی نیست چون تو کانستراکتور انجام شده بود.
			this.init("testCompletedCall");

			
			//  دو SIPAgent به اسمهای alice و bob درست میکنیم. تابع createAgent تو کلاس پدر هستش.
			SIPAgent alice = createAgent("alice");
			SIPAgent bob = createAgent("bob");
			
			
			alice.setProxy(appServer);
			bob.setProxy(appServer);
			alice.call("sip:conf@10.10.5.32:5060");			
			pause(2000);
			bob.call("sip:conf@10.10.5.32:5060");
			
			

//			assertThat(alice, recvdNewSDP())); 
			//assertThat(callee, has(recvdRequest("INVITE")));


			
			/*pause(2000);
			assertThat(alice, is(connected()));

			pause(2000);*/
			
			
			
			
			pause(20000);
			
	//		assertThat(bob, recvdNewSDP()));
			assertThat(bob, is(connected()));
			pause(2000);
			
			//alice.end();
			

			pause(2000);
		/*	assertThat(alice, is(disconnected()));*/

			//bob.end();
			pause(2000);
			/*assertThat(bob, is(disconnected()));*/
 			
		}
		catch (Throwable e) {
			logger.error("test failed", e);
			throw e;
		}
	}
}
