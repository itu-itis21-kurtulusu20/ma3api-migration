/*
 * Copyright 1998-2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package sune.security.util;

import sune.security.action.GetPropertyAction;

import java.math.BigInteger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * A utility class for debuging.
 *
 * @author Roland Schemers
 */
public class Debug {

    private String prefix;

    private static String args;

    static {
        args = java.security.AccessController.doPrivileged
                (new GetPropertyAction
                ("java.security.debug"));

        String args2 = java.security.AccessController.doPrivileged
                (new GetPropertyAction
                ("java.security.auth.debug"));

        if (args == null) {
            args = args2;
        } else {
            if (args2 != null)
               args = args + "," + args2;
        }

        if (args != null) {
            args = marshal(args);
            if (args.equals("help")) {
                Help();
            }
        }
    }

    public static void Help()
    {
        System.err.println();
        System.err.println("all           turn on all debugging");
        System.err.println("access        print all checkPermission results");
        System.err.println("combiner      SubjectDomainCombiner debugging");
        System.err.println("gssloginconfig");
        System.err.println("configfile    JAAS ConfigFile loading");
        System.err.println("configparser  JAAS ConfigFile parsing");
        System.err.println("              GSS LoginConfigImpl debugging");
        System.err.println("jar           jar verification");
        System.err.println("logincontext  login context results");
        System.err.println("policy        loading and granting");
        System.err.println("provider      security provider debugging");
        System.err.println("scl           permissions SecureClassLoader assigns");
        System.err.println();
        System.err.println("The following can be used with access:");
        System.err.println();
        System.err.println("stack         include stack trace");
        System.err.println("domain        dump all domains in context");
        System.err.println("failure       before throwing exception, dump stack");
        System.err.println("              and domain that didn't have permission");
        System.err.println();
        System.err.println("The following can be used with stack and domain:");
        System.err.println();
        System.err.println("permission=<classname>");
        System.err.println("              only dump output if specified permission");
        System.err.println("              is being checked");
        System.err.println("codebase=<URL>");
        System.err.println("              only dump output if specified codebase");
        System.err.println("              is being checked");

        System.err.println();
        System.err.println("Note: Separate multiple options with a comma");
        System.exit(0);
    }


    /**
     * Get a Debug object corresponding to whether or not the given
     * option is set. Set the prefix to be the same as option.
     */

    public static Debug getInstance(String option)
    {
        return getInstance(option, option);
    }

    /**
     * Get a Debug object corresponding to whether or not the given
     * option is set. Set the prefix to be prefix.
     */
    public static Debug getInstance(String option, String prefix)
    {
        if (isOn(option)) {
            Debug d = new Debug();
            d.prefix = prefix;
            return d;
        } else {
            return null;
        }
    }

    /**
     * True if the system property "security.debug" contains the
     * string "option".
     */
    public static boolean isOn(String option)
    {
        if (args == null)
            return false;
        else {
            if (args.indexOf("all") != -1)
                return true;
            else
                return (args.indexOf(option) != -1);
        }
    }

    /**
     * print a message to stderr that is prefixed with the prefix
     * created from the call to getInstance.
     */

    public void println(String message)
    {
        System.err.println(prefix + ": "+message);
    }

    /**
     * print a blank line to stderr that is prefixed with the prefix.
     */

    public void println()
    {
        System.err.println(prefix + ":");
    }

    /**
     * print a message to stderr that is prefixed with the prefix.
     */

    public static void println(String prefix, String message)
    {
        System.err.println(prefix + ": "+message);
    }

    /**
     * return a hexadecimal printed representation of the specified
     * BigInteger object. the value is formatted to fit on lines of
     * at least 75 characters, with embedded newlines. Words are
     * separated for readability, with eight words (32 bytes) per line.
     */
    public static String toHexString(BigInteger b) {
        String hexValue = b.toString(16);
        StringBuffer buf = new StringBuffer(hexValue.length()*2);

        if (hexValue.startsWith("-")) {
            buf.append("   -");
            hexValue = hexValue.substring(1);
        } else {
            buf.append("    ");     // four spaces
        }
        if ((hexValue.length()%2) != 0) {
            // add back the leading 0
            hexValue = "0" + hexValue;
        }
        int i=0;
        while (i < hexValue.length()) {
            // one byte at a time
            buf.append(hexValue.substring(i, i+2));
            i+=2;
            if (i!= hexValue.length()) {
                if ((i%64) == 0) {
                    buf.append("\n    ");     // line after eight words
                } else if (i%8 == 0) {
                    buf.append(" ");     // space between words
                }
            }
        }
        return buf.toString();
    }

    /**
     * change a string into lower case except permission classes and URLs.
     */
    private static String marshal(String args) {
        if (args != null) {
            StringBuffer target = new StringBuffer();
            StringBuffer source = new StringBuffer(args);

            // obtain the "permission=<classname>" options
            // the syntax of classname: IDENTIFIER.IDENTIFIER
            // the regular express to match a class name:
            // "[a-zA-Z_$][a-zA-Z0-9_$]*([.][a-zA-Z_$][a-zA-Z0-9_$]*)*"
            String keyReg = "[Pp][Ee][Rr][Mm][Ii][Ss][Ss][Ii][Oo][Nn]=";
            String keyStr = "permission=";
            String reg = keyReg +
                "[a-zA-Z_$][a-zA-Z0-9_$]*([.][a-zA-Z_$][a-zA-Z0-9_$]*)*";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(source);
            StringBuffer left = new StringBuffer();
            while (matcher.find()) {
                String matched = matcher.group();
                target.append(matched.replaceFirst(keyReg, keyStr));
                target.append("  ");

                // delete the matched sequence
                matcher.appendReplacement(left, "");
            }
            matcher.appendTail(left);
            source = left;

            // obtain the "codebase=<URL>" options
            // the syntax of URL is too flexible, and here assumes that the
            // URL contains no space, comma(','), and semicolon(';'). That
            // also means those characters also could be used as separator
            // after codebase option.
            // However, the assumption is incorrect in some special situation
            // when the URL contains comma or semicolon
            keyReg = "[Cc][Oo][Dd][Ee][Bb][Aa][Ss][Ee]=";
            keyStr = "codebase=";
            reg = keyReg + "[^, ;]*";
            pattern = Pattern.compile(reg);
            matcher = pattern.matcher(source);
            left = new StringBuffer();
            while (matcher.find()) {
                String matched = matcher.group();
                target.append(matched.replaceFirst(keyReg, keyStr));
                target.append("  ");

                // delete the matched sequence
                matcher.appendReplacement(left, "");
            }
            matcher.appendTail(left);
            source = left;

            // convert the rest to lower-case characters
            target.append(source.toString().toLowerCase());

            return target.toString();
        }

        return null;
    }

    private final static char[] hexDigits = "0123456789abcdef".toCharArray();

    public static String toString(byte[] b) {
        if (b == null) {
            return "(null)";
        }
        StringBuilder sb = new StringBuilder(b.length * 3);
        for (int i = 0; i < b.length; i++) {
            int k = b[i] & 0xff;
            if (i != 0) {
                sb.append(':');
            }
            sb.append(hexDigits[k >>> 4]);
            sb.append(hexDigits[k & 0xf]);
        }
        return sb.toString();
    }

}
