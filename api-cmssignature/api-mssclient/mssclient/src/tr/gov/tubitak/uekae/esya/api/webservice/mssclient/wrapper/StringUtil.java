package tr.gov.tubitak.uekae.esya.api.webservice.mssclient.wrapper;

public class StringUtil
{
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    protected static final byte hexValTable[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13,
            14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };

    /**
     * <p>Returns a string of hexadecimal digits from a byte array. Each byte is
     * converted to 2 hex symbols; zero(es) included.</p>
     *
     * <p>This method calls the method with same name and three arguments as:</p>
     *
     * <pre>
     *    toString(ba, 0, ba.length);
     * </pre>
     *
     * @param ba the byte array to convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte array.
     */
    public static String toString(byte[] ba) {
        return toString(ba, 0, ba.length);
    }

    /**
     * <p>Returns a string of hexadecimal digits from a byte array, starting at
     * <code>offset</code> and consisting of <code>length</code> bytes. Each byte
     * is converted to 2 hex symbols; zero(es) included.</p>
     *
     * @param ba the byte array to convert.
     * @param offset the index from which to start considering the bytes to
     * convert.
     * @param length the count of bytes, starting from the designated offset to
     * convert.
     * @return a string of hexadecimal characters (two for each byte)
     * representing the designated input byte sub-array.
     */
    public static final String toString(byte[] ba, int offset, int length) {
        char[] buf = new char[length * 2];
        for (int i = 0, j = 0, k; i < length; ) {
            k = ba[offset + i++];
            buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
            buf[j++] = HEX_DIGITS[ k        & 0x0F];
        }
        return new String(buf);
    }



    public static final byte [] toByteArray(String s){
        if(s.length() % 2 != 0)
            throw new IllegalArgumentException("odd length string");

        byte [] hexByteArray = new byte[s.length() / 2];
        byte [] array = s.getBytes();

        byte firstNibble;		byte secondNibble;

        if (array == null)
            return null;
        if (array.length == 0)
            return new byte[0];

        int i=0;
        for (int k = 0; k < array.length;)
        {
            firstNibble = hexValTable[array[k++]];
            secondNibble = hexValTable[array[k++]];
            if (firstNibble == -1 || secondNibble == -1)
            {
                throw new IllegalArgumentException("not hex string");
            }
            hexByteArray[i++] = (byte) ((firstNibble << 4) | secondNibble & 0xff);
        }

        return hexByteArray;

    }

}