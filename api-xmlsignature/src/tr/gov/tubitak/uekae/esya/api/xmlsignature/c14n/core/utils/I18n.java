/*
 * Copyright  1999-2004 The Apache Software Foundation.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package tr.gov.tubitak.uekae.esya.api.xmlsignature.c14n.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;


/**
 * The Internationalization (I18N) pack.
 *
 * @author Christian Geuer-Pollmann
 */
public class I18n {

   protected static Logger logger = LoggerFactory.getLogger(I18n.class);

   public static List SUPPORTED_LANGUAGE_CODES = Arrays.asList(new String [] {"tr", "en"});

   /** Field NOT_INITIALIZED_MSG */
   public static final String NOT_INITIALIZED_MSG =
      "You must initialize the xml-security library correctly before you use it. "
      + "Call the static method \"org.apache.xml.security.Init.init();\" to do that "
      + "before you use any functionality from that library.";

    static {
        init(Locale.getDefault().getLanguage(),
             Locale.getDefault().getCountry());
    }

   /** Field defaultLanguageCode */
   static String defaultLanguageCode;    // will be set in static{} block

   /** Field defaultCountryCode */
   static String defaultCountryCode;    // will be set in static{} block

   /** Field resourceBundle */
   static ResourceBundle resourceBundle;

   /** Field alreadyInitialized */
   static boolean alreadyInitialized = false;

   /** Field _languageCode */
   static String _languageCode = null;

   /** Field _countryCode */
   static String _countryCode = null;


   /**
    * Constructor I18n
    *
    */
   private I18n() {

      // we don't allow instantiation
   }

   /**
    * Method translate
    *
    * translates a message ID into an internationalized String, see alse
    * <CODE>XMLSecurityException.getExceptionMEssage()</CODE>. The strings are
    * stored in the <CODE>ResourceBundle</CODE>, which is identified in
    * <CODE>exceptionMessagesResourceBundleBase</CODE>
    *
    * @param message
    * @param args is an <CODE>Object[]</CODE> array of strings which are inserted into the String which is retrieved from the <CODE>ResouceBundle</CODE>
    * @return message translated
    */
   public static String translate(String message, Object[] args) {
      return getExceptionMessage(message, args);
   }

   /**
    * Method translate
    *
    * translates a message ID into an internationalized String, see alse
    * <CODE>XMLSecurityException.getExceptionMEssage()</CODE>
    *
    * @param message
    * @return message translated
    */
   public static String translate(String message) {
      return getExceptionMessage(message);
   }

   /**
    * Method getExceptionMessage
    *
    * @param msgID
    * @return message translated
    *
    */
   public static String getExceptionMessage(String msgID) {

      try {
         String s = resourceBundle.getString(msgID);

         return s;
      } catch (Exception e) {
         logger.warn("Warning in I18n", e);
         if (alreadyInitialized) {
            return "No message with ID \"" + msgID
                   + "\" found in resource bundle \""
                   + Constants.exceptionMessagesResourceBundleBase + "\"";
         } 
         return I18n.NOT_INITIALIZED_MSG;
      }
   }

   /**
    * Method getExceptionMessage
    *
    * @param msgID
    * @param originalException
    * @return message translated
    */
   public static String getExceptionMessage(String msgID,
                                            Exception originalException) {

      try {
         Object exArgs[] = { originalException.getMessage() };
         String s = MessageFormat.format(resourceBundle.getString(msgID),
                                         exArgs);

         return s;
      } catch (Exception e) {
         logger.warn("Warning in I18n", e);
         if (alreadyInitialized) {
            return "No message with ID \"" + msgID
                   + "\" found in resource bundle \""
                   + Constants.exceptionMessagesResourceBundleBase
                   + "\". Original Exception was a "
                   + originalException.getClass().getName() + " and message "
                   + originalException.getMessage();
         } 
          return I18n.NOT_INITIALIZED_MSG;
      }
   }

   /**
    * Method getExceptionMessage
    *
    * @param msgID
    * @param exArgs
    * @return message translated
    */
   public static String getExceptionMessage(String msgID, Object exArgs[]) {

      try {
         String s = MessageFormat.format(resourceBundle.getString(msgID),
                                         exArgs);

         return s;
      } catch (Exception e) {
         logger.warn("Warning in I18n", e);
         if (alreadyInitialized) {
            return "No message with ID \"" + msgID
                   + "\" found in resource bundle \""
                   + Constants.exceptionMessagesResourceBundleBase + "\"";
         } 
         return I18n.NOT_INITIALIZED_MSG;
      }
   }

   /**
    * Method init
    *
    * @param _defaultLanguageCode
    * @param _defaultCountryCode
    */
   public static void init(String _defaultLanguageCode,
                           String _defaultCountryCode) {

      I18n.defaultLanguageCode = _defaultLanguageCode;

      if (I18n.defaultLanguageCode == null) {
         I18n.defaultLanguageCode = Locale.getDefault().getLanguage();
      }

      I18n.defaultCountryCode = _defaultCountryCode;

      if (I18n.defaultCountryCode == null) {
         I18n.defaultCountryCode = Locale.getDefault().getCountry();
      }

      initLocale(I18n.defaultLanguageCode, I18n.defaultCountryCode);
   }

   /**
    * Method initLocale
    *
    * @param languageCode
    * @param countryCode
    */
   public static void initLocale(String languageCode, String countryCode) {

      if (alreadyInitialized && languageCode.equals(_languageCode)
              && countryCode.equals(_countryCode)) {
         return;
      }

      if ((languageCode != null) && (countryCode != null)
              && (languageCode.length() > 0) && (countryCode.length() > 0)
              && SUPPORTED_LANGUAGE_CODES.contains(languageCode)) {
         _languageCode = languageCode;
         _countryCode = countryCode;
      } else {
         _countryCode = I18n.defaultCountryCode;
         _languageCode = I18n.defaultLanguageCode;
      }

      I18n.resourceBundle =
         ResourceBundle.getBundle(Constants.exceptionMessagesResourceBundleBase,
                                  new Locale(_languageCode, _countryCode));
   }
}
