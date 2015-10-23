/*
 * 文件名: LoggerPrinter
 * 版    权：  Copyright Daman Tech. Co. Ltd. All Rights Reserved.
 * 描    述: [该类的简要描述]
 * 创建人: evil
 * 创建时间: 14/10/15
 *
 * 修改人：
 * 修改时间:
 * 修改内容：[修改内容]
 */
package com.android.hiphoto.views.utils.log;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Printer;
import com.orhanobut.logger.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 *
 * @author evil
 * @version [Daman Client V20150420, 14/10/15]
 */
final class FLoggerPrinter implements Printer {
    private static final int CHUNK_SIZE = 4000;
    private static final int JSON_INDENT = 4;
    private static final int MIN_STACK_OFFSET = 3;
    private static final Settings settings = new Settings();
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = "╔════════════════════════════════════════════════════════════════════════════════════════";
    private static final String BOTTOM_BORDER = "╚════════════════════════════════════════════════════════════════════════════════════════";
    private static final String MIDDLE_BORDER = "╟────────────────────────────────────────────────────────────────────────────────────────";
    private static String TAG = "PRETTYLOGGER";
    public static String MARK = "FLOGGER";
    private static final ThreadLocal<String> LOCAL_TAG = new ThreadLocal();
    private static final ThreadLocal<Integer> LOCAL_METHOD_COUNT = new ThreadLocal();

    FLoggerPrinter() {
        settings.setMethodCount(1);
        settings.setLogLevel(LogLevel.FULL);
    }

    public Settings init(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag may not be null");
        } else if (tag.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        } else {
            TAG = tag;
            return settings;
        }
    }

    public Settings init(String tag, String mark) {
        this.MARK = mark;
        return this.init(tag);
    }

    public Settings getSettings() {
        return settings;
    }

    public Printer t(String tag, int methodCount) {
        if (tag != null) {
            LOCAL_TAG.set(tag);
        }

        LOCAL_METHOD_COUNT.set(Integer.valueOf(methodCount));
        return this;
    }

    public void d(String message, Object... args) {
        this.log(3, message, args);
    }

    public void e(String message, Object... args) {
        this.e((Throwable) null, message, args);
    }

    public void e(Throwable throwable, String message, Object... args) {
        if (throwable != null && message != null) {
            message = message + " : " + throwable.toString();
        }

        if (throwable != null && message == null) {
            message = throwable.toString();
        }

        if (message == null) {
            message = "No message/exception is set";
        }

        this.log(6, message, args);
    }

    public void w(String message, Object... args) {
        this.log(5, message, args);
    }

    public void i(String message, Object... args) {
        this.log(4, message, args);
    }

    public void v(String message, Object... args) {
        this.log(2, message, args);
    }

    public void wtf(String message, Object... args) {
        this.log(7, message, args);
    }

    public void json(String json) {
        if (TextUtils.isEmpty(json)) {
            this.d("Empty/Null json content", new Object[0]);
        } else {
            try {
                String message;
                if (json.startsWith("{")) {
                    JSONObject e1 = new JSONObject(json);
                    message = e1.toString(4);
                    this.d(message, new Object[0]);
                    return;
                }

                if (json.startsWith("[")) {
                    JSONArray e = new JSONArray(json);
                    message = e.toString(4);
                    this.d(message, new Object[0]);
                }
            } catch (JSONException var4) {
                this.e(var4.getCause().getMessage() + "\n" + json, new Object[0]);
            }

        }
    }

    public void xml(String xml) {
        if (TextUtils.isEmpty(xml)) {
            this.d("Empty/Null xml content", new Object[0]);
        } else {
            try {
                StreamSource e = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty("indent", "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(e, xmlOutput);
                this.d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"), new Object[0]);
            } catch (TransformerException var5) {
                this.e(var5.getCause().getMessage() + "\n" + xml, new Object[0]);
            }

        }
    }

    private synchronized void log(int logType, String msg, Object... args) {
        if (settings.getLogLevel() != LogLevel.NONE) {
            String tag = this.getTag();
            String message = this.createMessage(msg, args);
            int methodCount = this.getMethodCount();
//            this.logTopBorder(logType, tag);
            this.logHeaderContent(logType, tag, methodCount);
            byte[] bytes = message.getBytes();
            int length = bytes.length;
            if (length <= 4000) {
//                if(methodCount > 0) {
//                    this.logDivider(logType, tag);
//                }

                this.logContent(logType, tag, message);
//                this.logBottomBorder(logType, tag);
            } else {
//                if(methodCount > 0) {
//                    this.logDivider(logType, tag);
//                }

                for (int i = 0; i < length; i += 4000) {
                    int count = Math.min(length - i, 4000);
                    this.logContent(logType, tag, new String(bytes, i, count));
                }

//                this.logBottomBorder(logType, tag);
            }
        }
    }

//    private void logTopBorder(int logType, String tag) {
//        this.logChunk(logType, tag, "╔════════════════════════════════════════════════════════════════════════════════════════");
//    }

    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (settings.isShowThreadInfo()) {
            this.logChunk(logType, tag, "  Thread: " + Thread.currentThread().getName());
//            this.logDivider(logType, tag);
        }

        String level = "";
        int stackOffset = this.getStackOffset(trace)+ settings.getMethodOffset()+1;
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; --i) {
            int stackIndex = i + stackOffset;
            if (stackIndex < trace.length) {
                StringBuilder builder = new StringBuilder();
                builder.append("|  ").append(level).append(this.getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
                level = level + "   ";
                this.logChunk(logType, getTag(tag), builder.toString());
            }
        }

    }

//    private void logBottomBorder(int logType, String tag) {
//        this.logChunk(logType, tag, "╚════════════════════════════════════════════════════════════════════════════════════════");
//    }

//    private void logDivider(int logType, String tag) {
//        this.logChunk(logType, tag, "╟────────────────────────────────────────────────────────────────────────────────────────");
//    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        String[] arr$ = lines;
        int len$ = lines.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String line = arr$[i$];
            this.logChunk(logType, getTag(tag), "|  " + line);
        }

    }

    private String getTag(String tag) {
        return new StringBuilder(tag).append("[").append(MARK).append("]").append("[").append(Thread.currentThread().getName()).append("]").toString();
    }

    private void logChunk(int logType, String tag, String chunk) {
        String finalTag = this.formatTag(tag);
        switch (logType) {
            case 2:
                Log.v(finalTag, chunk);
                break;
            case 3:
            default:
                Log.d(finalTag, chunk);
                break;
            case 4:
                Log.i(finalTag, chunk);
                break;
            case 5:
                Log.w(finalTag, chunk);
                break;
            case 6:
                Log.e(finalTag, chunk);
                break;
            case 7:
                Log.wtf(finalTag, chunk);
        }

    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        return !TextUtils.isEmpty(tag) && !TextUtils.equals(TAG, tag) ? TAG + "-" + tag : TAG;
    }

    private String getTag() {
        String tag = (String) LOCAL_TAG.get();
        if (tag != null) {
            LOCAL_TAG.remove();
            return tag;
        } else {
            return TAG;
        }
    }

    private String createMessage(String message, Object... args) {
        return args.length == 0 ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count = (Integer) LOCAL_METHOD_COUNT.get();
        int result = settings.getMethodCount();
        if (count != null) {
            LOCAL_METHOD_COUNT.remove();
            result = count.intValue();
        }

        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        } else {
            return result;
        }
    }

    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = 3; i < trace.length; ++i) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(FLoggerPrinter.class.getName()) && !name.equals(com.orhanobut.logger.Logger.class.getName())) {
                --i;
                return i;
            }
        }

        return -1;
    }
}

