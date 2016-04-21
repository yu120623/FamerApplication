## Add project specific ProGuard rules here.
## By default, the flags in this file are appended to flags specified
## in D:\android\sdk\adt-bundle-windows-x86_64-20131030/tools/proguard/proguard-android.txt
## You can edit the include path and order by changing the proguardFiles
## directive in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## Add any project specific keep options here:
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#-optimizationpasses 5
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-verbose
#-dontoptimize
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-dontwarn cn.jpush.**
#-dontwarn com.alipay.**
#-dontwarn com.android.volley.**
#-dontwarn com.mob.**
#-dontwarn com.unionpay.**
#-dontwarn com.zxinsight.**
#-dontwarn com.alibaba.**
#-dontwarn com.amap.**
#-dontwarn com.google.**
#-dontwarn com.iflytek.**
#-dontwarn de.greenrobot.**
#-dontwarn okio.**
#-dontwarn org.joda.**
##-keep class cn.jpush.** { *; }
##-keep class com.alipay.**
##-keep class com.android.volley.**
##-keep class com.mob.**
#-keep class com.unionpay.**
##-keep class com.zxinsight.**
##-keep class com.alibaba.**
##-keep class com.amap.**
##-keep class com.google.**
##-keep class com.iflytek.**
##-keep class de.greenrobot.**
##-keep class okio.**
##-keep class org.joda.**
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#
#-dontwarn android.support.v4.**
#
#-keep class android.support.v4.** { *; }
#
#-keep interface android.support.v4.app.** { *; }
#
#-keep public class * extends android.support.v4.**
#-keep class com.egceo.app.myfarm.entity.** {*;}
#
#-keep public class * extends android.app.Fragment
#
#-dontwarn com.amap.api.**
#
#-dontwarn com.a.a.**
#
#-dontwarn com.autonavi.**
#
#-keep class com.amap.api.**  {*;}
#
#-keep class com.autonavi.**  {*;}
#
#-keep class com.a.a.**  {*;}
#
#-keep class de.greenrobot.** {*;}
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static final java.lang.String TABLENAME;
#}
#-keep class **$Properties
#
#-keep class com.nostra13.universalimageloader.** { *; }
#
#-keepclassmembers class com.nostra13.universalimageloader.** {*;}
#
#-keep class cn.sharesdk.**{*;}
#
#-keep class com.sina.**{*;}
#
#-keep class **.R$* {*;}
#
#-keep class **.R{*;}
#
#-dontwarn cn.sharesdk.**
#
#-dontwarn **.R$*
#
#-keep class com.android.volley.** {*;}
#
#-keep class com.android.volley.toolbox.** {*;}
#
#-keep class com.android.volley.Response$* { *; }
#
#-keep class com.android.volley.Request$* { *; }
#
#-keep class com.android.volley.RequestQueue$* { *;}
#
#-keep class com.android.volley.toolbox.HurlStack$* { *; }
#
#-keep class com.android.volley.toolbox.ImageLoader$* { *; }
#
# -keepclassmembers class ** {
#
#    public void onEvent*(**);
#
#}
#
#-keepattributes Signature
#
#-keep class sun.misc.Unsafe { *; }
#
#-keep class com.google.gson.examples.Android.model.** { *;}
#
#-keepclassmembers class ** {
#    public void onEvent(**);
#}
#
#-dontwarn com.iflytek.**
#-keep class com.iflytek.** {*;}
#
#-dontwarn com.google.zxing.**
#-keep  class com.google.zxing.**{*;}
#
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#
#
#-keep  public class com.unionpay.uppay.net.HttpConnection {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.net.HttpParameters {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.BankCardInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.PAAInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.ResponseInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.model.PurchaseInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.util.DeviceInfo {
#	public <methods>;
#}
#-keep  public class com.unionpay.uppay.util.PayEngine {
#	public <methods>;
#	native <methods>;
#}
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#-keep  public class com.unionpay.utils.UPUtils {
#	native <methods>;
#}
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#-keep  public class java.util.HashMap {
#	public <methods>;
#}
#-keep  public class java.lang.String {
#	public <methods>;
#}
#-keep  public class java.util.List {
#	public <methods>;
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#}
#
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#
#-keepclassmembers class com.unionpay.uppay.PayActivity {
#   public static java.lang.String a;
#}