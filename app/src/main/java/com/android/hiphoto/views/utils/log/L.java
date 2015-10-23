/*
 * 文件名: L
 * 描    述: [该类的简要描述]
 * 创建人: jp
 * 创建时间: 9/9/15
 */
package com.android.hiphoto.views.utils.log;

import android.support.v4.util.Pair;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.android.hiphoto.BuildConfig;

/**
 * Log日志<BR>
 *
 * @author jp
 * @version [V20150528, 9/9/15]
 */
public final class L {

    private static final Printer printer = new FLoggerPrinter();
    private static final String DEFAULT_TAG = "FLOGGER";

    private L() {
    }

    public static void initLog() {
        if (BuildConfig.DEBUG) {
            init().setLogLevel(LogLevel.FULL);
        } else {
            init().setLogLevel(LogLevel.NONE);
        }
    }

    public static Settings init() {
        return printer.init(DEFAULT_TAG);
    }

    public static Settings init(String tag) {
        return printer.init(tag);
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return t(getClassName()).t((String) null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return t(getClassName()).t(tag, methodCount);
    }

    public static void d(String message, Object... args) {
        t(getClassName()).d(message, args);
    }

    public static void e(String message, Object... args) {
        t(getClassName()).e((Throwable) null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        t(getClassName()).e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        t(getClassName()).i(message, args);
    }

    public static void v(String message, Object... args) {
        t(getClassName()).v(message, args);
    }

    public static void w(String message, Object... args) {
        t(getClassName()).w(message, args);
    }

    public static void wtf(String message, Object... args) {
        t(getClassName()).wtf(message, args);
    }

    public static void json(String json) {
        t(getClassName()).json(json);
    }

    public static void xml(String xml) {
        t(getClassName()).xml(xml);
    }


//    private static Settings init() {
//        return Logger.init();
//    }
//
//    private static Settings init(String tag) {
//        return FLogger.init(tag);
//    }

//    public static void initLog() {
//        //init timber
//        Timber.uprootAll();
//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//            FragmentManager.enableDebugLogging(true);
//            LoaderManager.enableDebugLogging(true);
//            init().setLogLevel(LogLevel.FULL);
//        } else {
//            Timber.plant(new CrashReportingTree());
//            init(TAG).setLogLevel(LogLevel.NONE);
//        }
//    }

//    public static Printer t(String tag) {
//        return Logger.t(tag);
//    }
//
//    public static Printer t(int methodCount) {
//        return Logger.t(methodCount);
//    }
//
//    public static Printer t(String tag, int methodCount) {
//        return Logger.t(tag, methodCount);
//    }
//
//    public static void d(String message, Object... args) {
//        Timber.d(message, args);
//    }
//
//    public static void dd(String message, Object... args) {
//        Logger.t(getClassName()).d(message, args);
//    }
//
//    public static void e(String message, Object... args) {
//        Timber.e(message, args);
//    }
//
//    public static void ee(String message, Object... args) {
//        Logger.t(getClassName()).e(message, args);
//    }
//
//    public static void e(Throwable throwable, String message, Object... args) {
//        Logger.t(getClassName()).e(throwable, message, args);
//    }
//
//    public static void i(String message, Object... args) {
//        Timber.i(message, args);
//    }
//
//    public static void ii(String message, Object... args) {
//        Logger.t(getClassName()).i(message, args);
//    }
//
//    public static void v(String message, Object... args) {
//        Timber.v(message, args);
//    }
//
//    public static void vv(String message, Object... args) {
//        Logger.t(getClassName()).v(message, args);
//    }
//
//    public static void w(String message, Object... args) {
//        Timber.w(message, args);
//    }
//
//    public static void ww(String message, Object... args) {
//        Logger.t(getClassName()).w(message, args);
//    }
//
//    public static void wtf(String message, Object... args) {
//        Timber.wtf(message, args);
//    }
//
//    public static void wtfaf(String message, Object... args) {
//        Logger.t(getClassName()).wtf(message, args);
//    }
//
//    public static void json(String json) {
//        FLogger.t(getClassName()).json(json);
//    }
//
//    public static void xml(String xml) {
//        Logger.t(getClassName()).xml(xml);
//    }

    /**
     * support list、map、array
     *
     * @see "https://github.com/pengwei1024/LogUtils"
     */
    public static void object(Object object) {
        if (object != null) {
            final String simpleName = object.getClass().getSimpleName();
            if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtil.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtil.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtil.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                t(getClassName()).d(msg + "}");
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %d [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator<Object> iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%d]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, SystemUtil.objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                t(getClassName()).d(msg + "\n]");
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map<Object, Object> map = (Map<Object, Object>) object;
                Set<Object> keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, SystemUtil.objectToString(key),
                            SystemUtil.objectToString(value));
                }
                t(getClassName()).d(msg + "}");
            } else {
                t(getClassName()).d(SystemUtil.objectToString(object));
            }
        } else {
            t(getClassName()).d(SystemUtil.objectToString(object));
        }
    }

    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {
        String result;
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

//    /**
//     * A tree which logs important information for crash reporting.
//     */
//    private static class CrashReportingTree extends Timber.Tree {
//        @Override
//        protected void log(int priority, String tag, String message, Throwable t) {
//            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
//                return;
//            }
//
//            FakeCrashLibrary.log(priority, tag, message);
//
//            if (t != null) {
//                if (priority == Log.ERROR) {
//                    FakeCrashLibrary.logError(t);
//                } else if (priority == Log.WARN) {
//                    FakeCrashLibrary.logWarning(t);
//                }
//            }
//        }
//    }

}
