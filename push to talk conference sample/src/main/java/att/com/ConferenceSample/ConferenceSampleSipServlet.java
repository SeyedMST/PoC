/**************************************************************
 *           Template EChartsSipServlet subclass              *
 **************************************************************/

// $Name:  $ $Id: $

package att.com.ConferenceSample;

import java.util.Set;
import java.util.HashSet;
import javax.servlet.ServletConfig;
import javax.servlet.sip.*;
import javax.servlet.sip.SipServletRequest;


import org.echarts.servlet.sip.EChartsSipServlet;

/*

کلاس اصلی که قراره SipServlet ما باشه. به طور خلاصه مدیریت پیام‌های کنترلی و ارسال
اونها برای کاربران.

 */
public class ConferenceSampleSipServlet extends EChartsSipServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String rcsid = "$Id: $ $Name:  $";
    public final Set<String> set = new HashSet<String>();


    // این تابع قراره مقداردهی اولیه سرور و تنظیمات اولیه کار و وقتی سرور بالا میاد و بر
    // اساس کانفیگ ورودی بهش رو انچام بده
    // ولی هنوز هیچ پیاده سازی وجود نداره!
    //
    @Override
    public void servletInit(ServletConfig sc) throws Exception {
    	
    }

    //
    // قراره تمام نشست‌های موجود روی سرور رو آزاد کنه.
    //
    // برای وقتی خوبه که بخوایم کلا بندازیم دور همه رو.
    // همچنین همه نشست‌ها تو set هستن.
    @Override
    public void destroy() {
    	for (String session: set)
    	{
    		SipApplicationSession sess = getApplicationSession(session);
    		if (sess != null) 
    			sess.invalidate();
    	}

    	set.clear();  
   
    	//getApplicationSession()
    }

    // Implement custom session key calculation if desired
    // (bound box only)
    //

    // یه درخواست sip request به سرور رو دریافت میکنه.
    // فرضش بر اینه که فقط درخواست‌های ایجاد نشست با نام یک نشست خاص وجود داره.
    //
    @Override
    protected String sessionKeyFromRequest(SipServletRequest req) {
    	System.out.println("Servlet sessionKetFromRquest "+ req);
        // این کلاس میتونه یه درخواست sip که در قالب sipURI مشخص میشه رو بگیره.
        SipURI uri = ((SipURI)req.getTo().getURI());
        // تو بخش user قراره اسم نشستی که میره رو بدیم که تو این پروژه با IP مشخص میشه.
        String sessionkey = uri.getUser();
        System.out.println(sessionkey);
        set.add(sessionkey);
        //the session key is the conference name      
        return sessionkey;
    }
}
