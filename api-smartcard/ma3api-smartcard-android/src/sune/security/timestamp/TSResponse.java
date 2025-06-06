/*
 * Copyright 2003-2004 Sun Microsystems, Inc.  All Rights Reserved.
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

package sune.security.timestamp;

import java.io.IOException;

import sune.security.pkcs.android.PKCS7;
import sune.security.util.DerValue;

/**
 * This class provides the response corresponding to a timestamp request,
 * as defined in
 * <a href="http://www.ietf.org/rfc/rfc3161.txt">RFC 3161</a>.
 *
 * The TimeStampResp ASN.1 type has the following definition:
 * <pre>
 *
 *     TimeStampResp ::= SEQUENCE {
 *         status            PKIStatusInfo,
 *         timeStampToken    TimeStampToken OPTIONAL ]
 *
 *     PKIStatusInfo ::= SEQUENCE {
 *         status        PKIStatus,
 *         statusString  PKIFreeText OPTIONAL,
 *         failInfo      PKIFailureInfo OPTIONAL }
 *
 *     PKIStatus ::= INTEGER {
 *         granted                (0),
 *           -- when the PKIStatus contains the value zero a TimeStampToken, as
 *           -- requested, is present.
 *         grantedWithMods        (1),
 *           -- when the PKIStatus contains the value one a TimeStampToken,
 *           -- with modifications, is present.
 *         rejection              (2),
 *         waiting                (3),
 *         revocationWarning      (4),
 *           -- this message contains a warning that a revocation is
 *           -- imminent
 *         revocationNotification (5)
 *           -- notification that a revocation has occurred }
 *
 *     PKIFreeText ::= SEQUENCE SIZE (1..MAX) OF UTF8String
 *           -- text encoded as UTF-8 String (note:  each UTF8String SHOULD
 *           -- include an RFC 1766 language tag to indicate the language
 *           -- of the contained text)
 *
 *     PKIFailureInfo ::= BIT STRING {
 *         badAlg              (0),
 *           -- unrecognized or unsupported Algorithm Identifier
 *         badRequest          (2),
 *           -- transaction not permitted or supported
 *         badDataFormat       (5),
 *           -- the data submitted has the wrong format
 *         timeNotAvailable    (14),
 *           -- the TSA's time source is not available
 *         unacceptedPolicy    (15),
 *           -- the requested TSA policy is not supported by the TSA
 *         unacceptedExtension (16),
 *           -- the requested extension is not supported by the TSA
 *         addInfoNotAvailable (17)
 *           -- the additional information requested could not be understood
 *           -- or is not available
 *         systemFailure       (25)
 *           -- the request cannot be handled due to system failure }
 *
 *     TimeStampToken ::= ContentInfo
 *         -- contentType is id-signedData
 *         -- content is SignedData
 *         -- eContentType within SignedData is id-ct-TSTInfo
 *         -- eContent within SignedData is TSTInfo
 *
 * </pre>
 *
 * @since 1.5
 * @author Vincent Ryan
 * @see Timestamper
 */

public class TSResponse {

    // Status codes (from RFC 3161)

    /**
     * The requested timestamp was granted.
     */
    public static final int GRANTED = 0;

    /**
     * The requested timestamp was granted with some modifications.
     */
    public static final int GRANTED_WITH_MODS = 1;

    /**
     * The requested timestamp was not granted.
     */
    public static final int REJECTION = 2;

    /**
     * The requested timestamp has not yet been processed.
     */
    public static final int WAITING = 3;

    /**
     * A warning that a certificate revocation is imminent.
     */
    public static final int REVOCATION_WARNING = 4;

    /**
     * Notification that a certificate revocation has occurred.
     */
    public static final int REVOCATION_NOTIFICATION = 5;

    // Failure codes (from RFC 3161)

    /**
     * Unrecognized or unsupported algorithm identifier.
     */
    public static final int BAD_ALG = 0;

    /**
     * The requested transaction is not permitted or supported.
     */
    public static final int BAD_REQUEST = 2;

    /**
     * The data submitted has the wrong format.
     */
    public static final int BAD_DATA_FORMAT = 5;

    /**
     * The TSA's time source is not available.
     */
    public static final int TIME_NOT_AVAILABLE = 14;

    /**
     * The requested TSA policy is not supported by the TSA.
     */
    public static final int UNACCEPTED_POLICY = 15;

    /**
     * The requested extension is not supported by the TSA.
     */
    public static final int UNACCEPTED_EXTENSION = 16;

    /**
     * The additional information requested could not be understood or is not
     * available.
     */
    public static final int ADD_INFO_NOT_AVAILABLE = 17;

    /**
     * The request cannot be handled due to system failure.
     */
    public static final int SYSTEM_FAILURE = 25;

    private static final boolean DEBUG = false;

    private int status;

    private String[] statusString = null;

    private int failureInfo = -1;

    private byte[] encodedTsToken = null;

    private PKCS7 tsToken = null;

    /**
     * Constructs an object to store the response to a timestamp request.
     *
     * @param status A buffer containing the ASN.1 BER encoded response.
     * @throws IOException The exception is thrown if a problem is encountered
     *         parsing the timestamp response.
     */
    TSResponse(byte[] tsReply) throws IOException {
        parse(tsReply);
    }

    /**
     * Retrieve the status code returned by the TSA.
     */
    public int getStatusCode() {
        return status;
    }

    /**
     * Retrieve the status messages returned by the TSA.
     *
     * @return If null then no status messages were received.
     */
    public String[] getStatusMessages() {
        return statusString;
    }

    /**
     * Retrieve the failure code returned by the TSA.
     *
     * @return If -1 then no failure code was received.
     */
    public int getFailureCode() {
        return failureInfo;
    }

    public String getStatusCodeAsText() {

        switch (status)  {
        case GRANTED:
            return "the timestamp request was granted.";

        case GRANTED_WITH_MODS:
            return
                "the timestamp request was granted with some modifications.";

        case REJECTION:
            return "the timestamp request was rejected.";

        case WAITING:
            return "the timestamp request has not yet been processed.";

        case REVOCATION_WARNING:
            return "warning: a certificate revocation is imminent.";

        case REVOCATION_NOTIFICATION:
            return "notification: a certificate revocation has occurred.";

        default:
            return ("unknown status code " + status + ".");
        }
    }

    public String getFailureCodeAsText() {

        if (failureInfo == -1) {
            return null;
        }

        switch (failureInfo)  {

        case BAD_ALG:
            return "Unrecognized or unsupported alrorithm identifier.";

        case BAD_REQUEST:
            return "The requested transaction is not permitted or supported.";

        case BAD_DATA_FORMAT:
            return "The data submitted has the wrong format.";

        case TIME_NOT_AVAILABLE:
            return "The TSA's time source is not available.";

        case UNACCEPTED_POLICY:
            return "The requested TSA policy is not supported by the TSA.";

        case UNACCEPTED_EXTENSION:
            return "The requested extension is not supported by the TSA.";

        case ADD_INFO_NOT_AVAILABLE:
            return "The additional information requested could not be " +
                "understood or is not available.";

        case SYSTEM_FAILURE:
            return "The request cannot be handled due to system failure.";

        default:
            return ("unknown status code " + status);
        }
    }

    /**
     * Retrieve the timestamp token returned by the TSA.
     *
     * @return If null then no token was received.
     */
    public PKCS7 getToken() {
        return tsToken;
    }

    /**
     * Retrieve the ASN.1 BER encoded timestamp token returned by the TSA.
     *
     * @return If null then no token was received.
     */
    public byte[] getEncodedToken() {
        return encodedTsToken;
    }

    /*
     * Parses the timestamp response.
     *
     * @param status A buffer containing the ASN.1 BER encoded response.
     * @throws IOException The exception is thrown if a problem is encountered
     *         parsing the timestamp response.
     */
    private void parse(byte[] tsReply) throws IOException {
        // Decode TimeStampResp

        DerValue derValue = new DerValue(tsReply);
        if (derValue.tag != DerValue.tag_Sequence) {
            throw new IOException("Bad encoding for timestamp response");
        }

        // Parse status

        DerValue status = derValue.data.getDerValue();
        // Parse status
        this.status = status.data.getInteger();
        if (DEBUG) {
            System.out.println("timestamp response: status=" + this.status);
        }
        // Parse statusString, if present
        if (status.data.available() > 0) {
            DerValue[] strings = status.data.getSequence(1);
            statusString = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                statusString[i] = strings[i].data.getUTF8String();
            }
        }
        // Parse failInfo, if present
        if (status.data.available() > 0) {
            byte[] failInfo = status.data.getBitString();
            int failureInfo = (new Byte(failInfo[0])).intValue();
            if (failureInfo < 0 || failureInfo > 25 || failInfo.length != 1) {
                throw new IOException("Bad encoding for timestamp response: " +
                    "unrecognized value for the failInfo element");
            }
            this.failureInfo = failureInfo;
        }

        // Parse timeStampToken, if present
        if (derValue.data.available() > 0) {
            DerValue timestampToken = derValue.data.getDerValue();
            encodedTsToken = timestampToken.toByteArray();
            tsToken = new PKCS7(encodedTsToken);
        }

        // Check the format of the timestamp response
        if (this.status == 0 || this.status == 1) {
            if (tsToken == null) {
                throw new TimestampException(
                    "Bad encoding for timestamp response: " +
                    "expected a timeStampToken element to be present");
            }
        } else if (tsToken != null) {
            throw new TimestampException(
                "Bad encoding for timestamp response: " +
                "expected no timeStampToken element to be present");
        }
    }

final static class TimestampException extends IOException {
    TimestampException(String message) {
        super(message);
    }
}
}
