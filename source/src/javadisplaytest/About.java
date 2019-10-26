/*
Java Display Test and Benchmark Utility. (C)2019 IC Book Labs
--------------------------------------------------------------
Application info and path to resources in the application JAR archive.
 */

package javadisplaytest;

public class About 
{
private final static String VERSION_NAME = "v0.01.00";
private final static String VENDOR_NAME  = "(C)2019 IC Book Labs";
private final static String SHORT_NAME   = "Display Test " + VERSION_NAME;
private final static String LONG_NAME    = "Java " + SHORT_NAME;
private final static String WEB_SITE     = "http://icbook.com.ua";
private final static String VENDOR_ICON  = 
                                "/javadisplaytest/resources/icbook.jpg";

public static String getVersionName() { return VERSION_NAME; }
public static String getVendorName()  { return VENDOR_NAME;  }
public static String getShortName()   { return SHORT_NAME;   }
public static String getLongName()    { return LONG_NAME;    }
public static String getWebSite()     { return WEB_SITE;     }
public static String getVendorIcon()  { return VENDOR_ICON;  }
    
}
