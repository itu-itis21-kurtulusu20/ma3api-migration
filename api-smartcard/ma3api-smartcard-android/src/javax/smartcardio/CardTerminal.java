/*
 * Copyright 2005-2006 Sun Microsystems, Inc.  All Rights Reserved.
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

package javax.smartcardio;

/**
 * A Smart Card terminal, sometimes refered to as a Smart Card Reader.
 * A CardTerminal object can be obtained by calling
 * {@linkplain javax.smartcardio.CardTerminals#list}
 * or {@linkplain javax.smartcardio.CardTerminals#getTerminal CardTerminals.getTerminal()}.
 *
 * <p>Note that physical card readers with slots for multiple cards are
 * represented by one <code>CardTerminal</code> object per such slot.
 *
 * @see javax.smartcardio.CardTerminals
 * @see TerminalFactory
 *
 * @since   1.6
 * @author  Andreas Sterbenz
 * @author  JSR 268 Expert Group
 */
public abstract class CardTerminal {

    /**
     * Constructs a new CardTerminal object.
     *
     * <p>This constructor is called by subclasses only. Application should
     * call {@linkplain javax.smartcardio.CardTerminals#list list()}
     * or {@linkplain CardTerminals#getTerminal getTerminal()}
     * to obtain a CardTerminal object.
     */
    protected CardTerminal() {
        // empty
    }

    /**
     * Returns the unique name of this terminal.
     *
     * @return the unique name of this terminal.
     */
    public abstract String getName();

    /**
     * Establishes a connection to the card.
     * If a connection has previously established using
     * the specified protocol, this method returns the same Card object as
     * the previous call.
     *
     * @param protocol the protocol to use ("T=0", "T=1", or "T=CL"), or "*" to
     *   connect using any available protocol.
     *
     * @throws NullPointerException if protocol is null
     * @throws IllegalArgumentException if protocol is an invalid protocol
     *   specification
     * @throws CardNotPresentException if no card is present in this terminal
     * @throws javax.smartcardio.CardException if a connection could not be established
     *   using the specified protocol or if a connection has previously been
     *   established using a different protocol
     * @throws SecurityException if a SecurityManager exists and the
     *   caller does not have the required
     *   {@linkplain CardPermission permission}
     */
    public abstract Card connect(String protocol) throws javax.smartcardio.CardException;

    /**
     * Returns whether a card is present in this terminal.
     *
     * @return whether a card is present in this terminal.
     *
     * @throws javax.smartcardio.CardException if the status could not be determined
     */
    public abstract boolean isCardPresent() throws javax.smartcardio.CardException;

    /**
     * Waits until a card is present in this terminal or the timeout
     * expires. If the method returns due to an expired timeout, it returns
     * false. Otherwise it return true.
     *
     * <P>If a card is present in this terminal when this
     * method is called, it returns immediately.
     *
     * @param timeout if positive, block for up to <code>timeout</code>
     *   milliseconds; if zero, block indefinitely; must not be negative
     * @return false if the method returns due to an expired timeout,
     *   true otherwise.
     *
     * @throws IllegalArgumentException if timeout is negative
     * @throws javax.smartcardio.CardException if the operation failed
     */
    public abstract boolean waitForCardPresent(long timeout) throws javax.smartcardio.CardException;

    /**
     * Waits until a card is absent in this terminal or the timeout
     * expires. If the method returns due to an expired timeout, it returns
     * false. Otherwise it return true.
     *
     * <P>If no card is present in this terminal when this
     * method is called, it returns immediately.
     *
     * @param timeout if positive, block for up to <code>timeout</code>
     *   milliseconds; if zero, block indefinitely; must not be negative
     * @return false if the method returns due to an expired timeout,
     *   true otherwise.
     *
     * @throws IllegalArgumentException if timeout is negative
     * @throws javax.smartcardio.CardException if the operation failed
     */
    public abstract boolean waitForCardAbsent(long timeout) throws CardException;

}
