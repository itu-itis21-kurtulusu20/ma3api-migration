/*
 * Copyright (c) 1997-2014 Objective Systems, Inc.
 *
 * This software is furnished under a license and may be used and copied
 * only in accordance with the terms of such license and with the
 * inclusion of the above copyright notice. This software or any other
 * copies thereof may not be provided or otherwise made available to any
 * other person. No title to and ownership of the software is hereby
 * transferred.
 *
 * The information in this software is subject to change without notice
 * and should not be construed as a commitment by Objective Systems, Inc.
 *
 * PROPRIETARY NOTICE
 *
 * This software is an unpublished work subject to a confidentiality agreement
 * and is protected by copyright and trade secret law.  Unauthorized copying,
 * redistribution or other use of this work is prohibited.
 *
 * The above notice of copyright on this source code product does not indicate
 * any actual or intended publication of such source code.
 *
 *****************************************************************************/

/**
 * @file rtCompare.h  Functions for comparing the
 *                    values of primitive ASN.1 types.
 */

#ifndef _RTCOMPARE_H_
#define _RTCOMPARE_H_
#include "asn1type.h"
#include "rtconv.h"

#ifdef __cplusplus
extern "C" {
#endif

/**
 * @defgroup cmp Comparison Functions
 * @ingroup cruntime
 * @{
 *
 * The group of functions allows comparing the values of primitive ASN.1 types.
 * These functions are used in the comparison routines that are generated by
 * the ASN1C complier when the <i>-gencompare</i> option is used.
 *
 * Information on elements that do not match is written to the given error
 * buffer for each function. This makes it possible to compare complex
 * structures and get back specific information as to what elements within
 * those structures are different.
 */
/* Compare function to Buffer */

/**
 * The rtCmpBoolean function compares two ASN.1 Boolean values. The return
 * value is TRUE (matched) or FALSE (unmatched).
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpBoolean (const char* name, OSBOOL value,
                              OSBOOL compValue, char* errBuff,
                              OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpInt8 (const char* name, OSINT8 value,
                           OSINT8 compValue, char* errBuff,
                           OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpSInt (const char* name, OSINT16 value,
                           OSINT16 compValue, char* errBuff,
                           OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpUInt8 (const char* name, OSUINT8 value,
                            OSUINT8 compValue, char* errBuff,
                            OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpUSInt (const char* name, OSUINT16 value,
                            OSUINT16 compValue, char* errBuff,
                            OSSIZE errBuffSize);

/**
 * The rtCmpInteger function compars two ASN.1 INTEGER values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpInteger (const char* name, OSINT32 value,
                              OSINT32 compValue, char* errBuff,
                              OSSIZE errBuffSize);

/**
 * The rtCmpUnsigned function compares two ASN.1 unsigned INTEGER values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpUnsigned (const char* name, OSUINT32 value,
                               OSUINT32 compValue, char* errBuff,
                               OSSIZE errBuffSize);

/**
 * The rtCompInt64 function compares two 64-bit ASN.1 INTEGER values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpInt64 (const char* name, OSINT64 value,
                            OSINT64 compValue, char* errBuff,
                            OSSIZE errBuffSize);

/**
 * The rtCmpUInt64 function compares two 64-bit ASN.1 unsigned INTEGER values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpUInt64 (const char* name, OSUINT64 value,
                             OSUINT64 compValue, char* errBuff,
                             OSSIZE errBuffSize);

/**
 * The rtCmpBitStr function compares two ASN.1 BIT STRING values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param numbits      The number of bits in the first value to compare.
 * @param data         The pointer to the data of the first value to compare.
 * @param compNumbits  The number of bits in the second value to compare.
 * @param compData     The pointer to the data of the second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpBitStr (const char* name, OSSIZE numbits,
                             const OSOCTET* data, OSSIZE compNumbits,
                             const OSOCTET* compData, char* errBuff,
                             OSSIZE errBuffSize);

/**
 * The rtCmpOctStr function compares two ASN.1 OCTET STRING values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param numocts      The number of the octets in the first value to compare.
 * @param data         The pointer to the data of the first value to compare.
 * @param compNumocts  The number of the octets in the second value to compare.
 * @param compData     The pointer to the data of the second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpOctStr (const char* name, OSSIZE numocts,
                               const OSOCTET* data, OSSIZE compNumocts,
                               const OSOCTET* compData, char* errBuff,
                               OSSIZE errBuffSize);

/**
 * The rtCmpCharStr function compares two ASN.1 8-bit character sting values
 * (including IA5String, VisibleString, PrintableString, NumericString, etc.)
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param cstring      The first standard null-terminated string to compare.
 * @param compCstring  The second standard null-terminated string to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpCharStr (const char* name, const char* cstring,
                                const char* compCstring, char* errBuff,
                                OSSIZE errBuffSize);

/**
 * The rtCmp16BitCharStr function compares two ASN.1 16-bit character string
 * values (including BMPString).
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param bstring      The pointer to the first 16-bit character string
 *                       structure to compare.
 * @param compBstring  The pointer to the second 16-bit character string
 *                       structure to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmp16BitCharStr (const char* name,
                                     Asn116BitCharString* bstring,
                                     Asn116BitCharString* compBstring,
                                     char* errBuff, OSSIZE errBuffSize);

/**
 * The rtCmp32BitCharStr function compares two 32-bit character string values
 * (including UniversalString).
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param bstring      The pointer to the first 32-bit character string
 *                       structure to compare.
 * @param compBstring  The pointer to the second 32-bit character string
 *                       structure to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmp32BitCharStr (const char* name,
                                     Asn132BitCharString* bstring,
                                     Asn132BitCharString* compBstring,
                                     char* errBuff, OSSIZE errBuffSize);

/**
 * The rtCmpReal function compares two ASN.1 REAL values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param value        First value to compare.
 * @param compValue    Second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpReal (const char* name, OSREAL value,
                             OSREAL compValue, char* errBuff,
                             OSSIZE errBuffSize);

/**
 * The rtCmpOID function compares two ASN.1 OBJECT IDENTIFIER or REALTIVE-OID
 * values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param pOID         The pointer to the first value to compare.
 * @param pcompOID     The pointer to the second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpOID (const char* name, ASN1OBJID* pOID,
                            ASN1OBJID* pcompOID, char* errBuff,
                            OSSIZE errBuffSize);
EXTERNRT OSBOOL rtCmpOIDValue (const char* name, ASN1OBJID* pOID,
                                 ASN1OBJID* pcompOID, char* errBuff,
                                 OSSIZE errBuffSize);

/**
 * The rtCmpOID64 function compares two 64-bit ASN.1 OBJECT IDENTIFIER or
 * RELATIVE-OID values.
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param pOID         The pointer to the first value to compare.
 * @param pcompOID     The pointer to the second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpOID64 (const char* name, ASN1OID64* pOID,
                              ASN1OID64* pcompOID, char* errBuff,
                              OSSIZE errBuffSize);
EXTERNRT OSBOOL rtCmpOID64Value (const char* name, ASN1OID64* pOID,
                                   ASN1OID64* pcompOID, char* errBuff,
                                   OSSIZE errBuffSize);

/**
 * The rtCmpOpenType function compares two ASN.1 values of the old (pre- 1994)
 * ASN.1 ANY type or other elements defined in the later standards to be Open
 * Types (for example, a variable type declaration in a CLASS construct defined
 * in X.681).
 *
 * @param name         The name of value. Used for constructing errBuff if
 *                       values are not matching.
 * @param numocts      The number of octets in the first value to compare.
 * @param data         The pointer to the data of the first value to compare.
 * @param compNumocts  The number of octets in the second value to compare.
 * @param compData     The pointer to the data of the second value to compare.
 * @param errBuff      The pointer to the buffer to receive the null-terminated
 *                       string if the comparison fails. If the comparison
 *                       failed, this buffer will contain the null-terminated
 *                       string that explains the reason of comparison failure.
 * @param errBuffSize  The size of the errBuff buffer.
 * @return             The comparison result. TRUE, if values matched,
 *                       otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpOpenType (const char* name,
                               OSSIZE numocts, const OSOCTET* data,
                               OSSIZE compNumocts, const OSOCTET* compData,
                               char* errBuff, OSSIZE errBuffSize);

/**
 * The rtCmpOpenTypeExt function compares two ASN.1 open type extension values.
 *
 * An open type extension is defined as an extensibility marker on a
 * constructed type without any extension elements defined (for example,
 * SEQUENCE {a INTEGER, ...}). The difference is that this is an implicit field
 * that can span one or more elements whereas the standard Open Type is assumed
 * to be a single tagged field.
 *
 * @param name             The name of value. Used for constructing errBuff if
 *                           values are not matching.
 * @param pElemList        The first pointer to a linked list structure. The
 *                           list should consist of ASN1OpenType elements.
 * @param pCompElemList    The second pointer to a linked list structure. The
 *                           list should consist of ASN1OpenType elements.
 * @param errBuff          The pointer to the buffer to receive the
 *                           null-terminated string if the comparison fails. If
 *                           the comparison failed, this buffer will contain
 *                           the null-terminated string that explains the
 *                           reason of comparison failure.
 * @param errBuffSize      The size of the errBuff buffer.
 * @return                 The comparison result. TRUE, if values matched,
 *                           otherwise FALSE.
 */
EXTERNRT OSBOOL rtCmpOpenTypeExt (const char* name,
                                  OSRTDList* pElemList,
                                  OSRTDList* pCompElemList,
                                  char* errBuff, OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpTag (const char* name, int tag, int compTag,
                          char* errBuff, OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpSeqOfElements (const char* name, OSSIZE noOfElems,
                                    OSSIZE compNoOfElems, char* errBuff,
                                    OSSIZE errBuffSize);

EXTERNRT OSBOOL rtCmpOptional (const char* name, unsigned presentBit,
                                 unsigned compPresentBit, char* errBuff,
                                 OSSIZE errBuffSize);
/**
 * @} cmp
 */

/* Compare function To StdOut */

/**@defgroup cmpStdout Comparison to Standard Output Functions
 * @ingroup cruntime
 * @{
 * The rtCmpToStdout<type> functions do the same actions as the rtCmp<type>
 * ones, but they print the comparison results to stdout instead of putting it
 * into the buffer. The prototypes of these functions are almost the same as
 * for the rtCmp<type> except the last two parameters - they re absent in the
 * rtCmpToStdout<type> functions.
 *

 */
/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutBoolean (const char* name, OSBOOL value,
                                        OSBOOL compValue);

/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutInteger (const char* name, OSINT32 value,
                                        OSINT32 compValue);

/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutInt64 (const char* name, OSINT64 value,
                                      OSINT64 compValue);

/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutUnsigned (const char* name, OSUINT32 value,
                                         OSUINT32 compValue);

/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutUInt64 (const char* name, OSUINT64 value,
                                       OSUINT64 compValue);

/**
 * @param name         The name of value.
 * @param numbits      The first value to compare.
 * @param data         The pointer to the first value.
 * @param compNumbits  The second value to compare.
 * @param compData     The pointer to the second value.
 */
EXTERNRT OSBOOL rtCmpToStdoutBitStr (const char* name, OSSIZE numbits,
                                     const OSOCTET* data,
                                     OSSIZE compNumbits,
                                     const OSOCTET* compData);
/**
 * @param name         The name of value.
 * @param numocts      The first value to compare.
 * @param data         The pointer to the data of the first value.
 * @param compNumocts  The second value to compare.
 * @param compData     The pointer to the data of the second value.
 */
EXTERNRT OSBOOL rtCmpToStdoutOctStr (const char* name, OSSIZE numocts,
                                       const OSOCTET* data,
                                       OSSIZE compNumocts,
                                       const OSOCTET* compData);
/**
 * @param name         The name of value.
 * @param cstring      The first value to compare.
 * @param compCstring  The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutCharStr (const char* name,
                                        const char* cstring,
                                        const char* compCstring);
/**
 * @param name         The name of value.
 * @param bstring      The first value to compare.
 * @param compBstring  The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdout16BitCharStr (const char* name,
                                             Asn116BitCharString* bstring,
                                             Asn116BitCharString* compBstring);
/**
 * @param name         The name of value.
 * @param bstring      The first value to compare.
 * @param compBstring  The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdout32BitCharStr (const char* name,
                                             Asn132BitCharString* bstring,
                                             Asn132BitCharString* compBstring);
/**
 * @param name         The name of value.
 * @param value        The first value to compare.
 * @param compValue    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutReal (const char* name,
                                     OSREAL value, OSREAL compValue);
/**
 * @param name         The name of value.
 * @param pOID         The first value to compare.
 * @param pcompOID     The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOID (const char* name,
                                    ASN1OBJID* pOID, ASN1OBJID* pcompOID);
/**
 * @param name         The name of value.
 * @param pOID         The first value to compare.
 * @param pcompOID     The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOIDValue (const char* name,
                                         ASN1OBJID* pOID, ASN1OBJID* pcompOID);
/**
 * @param name         The name of value.
 * @param pOID         The first value to compare.
 * @param pcompOID     The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOID64 (const char* name,
                                      ASN1OID64* pOID, ASN1OID64* pcompOID);
/**
 * @param name         The name of value.
 * @param pOID         The first value to compare.
 * @param pcompOID     The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOID64Value (const char* name,
                                           ASN1OID64* pOID,
                                           ASN1OID64* pcompOID);
/**
 * @param name         The name of value.
 * @param numocts      The number of octets in the first value to compare.
 * @param data         The pointer to the data in the frist value to compare.
 * @param compNumocts  The number of octets in the second value to compare.
 * @param compData     The pointer to the data in the second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOpenType (const char* name,
                                         OSSIZE numocts,
                                         const OSOCTET* data,
                                         OSSIZE compNumocts,
                                         const OSOCTET* compData);
/**
 * @param name		The name of value.
 * @param pElemList 	The first value to compare.
 * @param pCompElemList	The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOpenTypeExt (const char* name,
                                            OSRTDList* pElemList,
                                            OSRTDList* pCompElemList);
/**
 * @param name		The name of value.
 * @param tag		The first value to compare.
 * @param compTag	The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutTag (const char* name, int tag,
                                    int compTag);
/**
 * @param name             The name of value.
 * @param noOfElems        The first value to compare.
 * @param compNoOfElems    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutSeqOfElements (const char* name,
                                            OSSIZE noOfElems,
                                            OSSIZE compNoOfElems);
/**
 * @param name              The name of value.
 * @param presentBit        The first value to compare.
 * @param compPresentBit    The second value to compare.
 */
EXTERNRT OSBOOL rtCmpToStdoutOptional (const char* name,
                                       unsigned presentBit,
                                       unsigned compPresentBit);
/**
 * @} cmpStdout
 */


#ifdef __cplusplus
}
#endif
#endif

