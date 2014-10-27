/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.geekchic.common.log;

import java.io.File;

import org.slf4j.LoggerFactory;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.LogcatAppender;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

import com.android.geekchic.Config;
import com.android.geekchic.jphoto.BuildConfig;

public class LogUtils
{
    private static final String SDCARD_LOG_PATH = "/%packagename%/log/file/";
    
    private static final String LOG_PREFIX = "iosched_";
    
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    
    private static final int MAX_LOG_TAG_LENGTH = 23;
    
    /**
     * 配置Log策略
     * @param context
     */
    public static void configureLogbackDirectly(Context context)
    {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        
        //配置LogcatAppender
        PatternLayoutEncoder logcatEncoder = new PatternLayoutEncoder();
        logcatEncoder.setContext(lc);
        logcatEncoder.setPattern("[%thread] %msg%n");
        logcatEncoder.start();
        
        LogcatAppender logcatAppender = new LogcatAppender();
        logcatAppender.setContext(lc);
        logcatAppender.setEncoder(logcatEncoder);
        logcatAppender.start();
        
        //设置RollFileAppender
        RollingFileAppender<ILoggingEvent> rollingFileAppender = new RollingFileAppender<ILoggingEvent>();
        
        TimeBasedRollingPolicy<ILoggingEvent> timeBasedRollingPolicy = new TimeBasedRollingPolicy<ILoggingEvent>();
        timeBasedRollingPolicy.setContext(lc);
        timeBasedRollingPolicy.setFileNamePattern(getLogPath(context)
                + "/log-%d{yyyy-MM-dd}");
        timeBasedRollingPolicy.setMaxHistory(5);
        timeBasedRollingPolicy.setParent(rollingFileAppender);
        timeBasedRollingPolicy.start();
        
        PatternLayoutEncoder rollFileEncoder = new PatternLayoutEncoder();
        rollFileEncoder.setContext(lc);
        rollFileEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        rollFileEncoder.start();
        
        rollingFileAppender.setRollingPolicy(timeBasedRollingPolicy);
        rollingFileAppender.setContext(lc);
        rollingFileAppender.setEncoder(rollFileEncoder);
        rollingFileAppender.setPrudent(true);
        rollingFileAppender.start();
        
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.addAppender(rollingFileAppender);
        root.addAppender(logcatAppender);
        //root.setLevel(isDebug() ? Level.DEBUG : Level.INFO);
    }
    
    /**
     * 获取Log本地地址
     * @param context
     * @return
     */
    private static String getLogPath(Context context)
    {
        String logPath;
        if (isDebug())
        {
            logPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + SDCARD_LOG_PATH.replaceFirst("%packagename%",
                            context.getPackageName());
        }
        else
        {
            logPath = new File(context.getFilesDir().getPath(), "log").getAbsolutePath();
        }
        return logPath;
    }
    
    public static boolean isDebug()
    {
        return BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD;
    }
    
    public static String makeLogTag(String str)
    {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH)
        {
            return LOG_PREFIX
                    + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH
                            - 1);
        }
        
        return LOG_PREFIX + str;
    }
    
    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls)
    {
        return makeLogTag(cls.getSimpleName());
    }
    
    public static void LOGD(final String tag, String message)
    {
        if (BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD
                || Log.isLoggable(tag, Log.DEBUG))
        {
            Log.d(tag, message);
        }
    }
    
    public static void LOGD(final String tag, String message, Throwable cause)
    {
        if (BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD
                || Log.isLoggable(tag, Log.DEBUG))
        {
            Log.d(tag, message, cause);
        }
    }
    
    public static void LOGV(final String tag, String message)
    {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE))
        {
            Log.v(tag, message);
        }
    }
    
    public static void LOGV(final String tag, String message, Throwable cause)
    {
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE))
        {
            Log.v(tag, message, cause);
        }
    }
    
    public static void LOGI(final String tag, String message)
    {
        Log.i(tag, message);
    }
    
    public static void LOGI(final String tag, String message, Throwable cause)
    {
        Log.i(tag, message, cause);
    }
    
    public static void LOGW(final String tag, String message)
    {
        Log.w(tag, message);
    }
    
    public static void LOGW(final String tag, String message, Throwable cause)
    {
        Log.w(tag, message, cause);
    }
    
    public static void LOGE(final String tag, String message)
    {
        Log.e(tag, message);
    }
    
    public static void LOGE(final String tag, String message, Throwable cause)
    {
        Log.e(tag, message, cause);
    }
    
    private LogUtils()
    {
    }
}
