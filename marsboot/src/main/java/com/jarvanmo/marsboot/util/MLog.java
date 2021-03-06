package com.jarvanmo.marsboot.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created by mo on 17-3-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public final class MLog {
    public static boolean isLoggable = true;
    public static int logLevel = Log.VERBOSE;
    public static String unknownTag = "MLog";

    private static final String THIS_CLASS_NAME = MLog.class.getName();

    private MLog() {
        throw new AssertionError("No instances.");
    }


    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }


        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(THIS_CLASS_NAME)) {
                continue;
            }

            return "[" + Thread.currentThread().getId() + ": " + st.getFileName() + ":" + st.getLineNumber() + "]\n";
        }

        return "MLog->unknown lines";


    }


    private static String getDefaultTag() {
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();

        if (sts == null) {
            return null;
        }


        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName().equals(THIS_CLASS_NAME)) {
                continue;
            }

            String t = st.getClassName();
            t = t.substring(t.lastIndexOf(".") + 1, t.length());
            return t;
        }

        return unknownTag;

    }


    public static void v(String message) {
        v(getDefaultTag(), message);
    }

    public static void v(String tag, String message) {
        v(tag, message, null);
    }

    public static void v(String tag, String message, Throwable tr) {
        showLog(Log.VERBOSE, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    public static void i(String message) {
        i(getDefaultTag(), message);
    }

    public static void i(String tag, String message) {
        i(tag, message, null);
    }

    public static void i(String tag, String message, Throwable tr) {
        showLog(Log.INFO, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    public static void e(String message) {
        e(getDefaultTag(), message);
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    public static void e(String message, Throwable tr) {
        e(getDefaultTag(), message, tr);
    }

    public static void e(String tag, String message, Throwable tr) {
        showLog(Log.ERROR, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    public static void d(String message) {
        d(getDefaultTag(), message);
    }

    public static void d(String tag, String message) {
        d(tag, message, null);
    }

    public static void d(String message, Throwable tr) {
        d(getDefaultTag(), message, tr);
    }

    public static void d(String tag, String message, Throwable tr) {
        showLog(Log.DEBUG, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    public static void w(String message) {
        w(getDefaultTag(), message);
    }

    public static void w(String tag, String message) {
        w(tag, message, null);
    }

    public static void w(String message, Throwable tr) {
        w(getDefaultTag(), message, tr);
    }

    public static void w(String tag, String message, Throwable tr) {
        showLog(Log.WARN, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    public static void wtf(String message) {
        wtf(getDefaultTag(), message);
    }

    public static void wtf(String tag, String message) {
        wtf(tag, message, null);
    }

    public static void wtf(String message, Throwable tr) {
        wtf(getDefaultTag(), message, tr);
    }

    public static void wtf(String tag, String message, Throwable tr) {
        showLog(Log.ASSERT, tag, getFunctionName() + message + "\n" + getStackTraceString(tr));
    }


    /**
     * Handy function to get a loggable stack trace from a Throwable
     *
     * @param tr An exception to log
     */
    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }

        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }


    private static void showLog(int priority, String tag, String message) {
        if (isLoggable && logLevel <= priority) {
            if (priority == Log.ASSERT) {
                Log.wtf(tag, message);
            } else {
                Log.println(priority, tag, message);
            }
        }
    }

}
