package tr.gov.tubitak.uekae.esya.api.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;

/**
 * INIFile class provides methods for manipulating (Read/Write) windows and Esya ini files.
 * This file merges abilities in IniFile and INIFile (Both Deprecated....)
 * @author Prasad P. Khandekar, Zeldal
 * @version 1.0
 * @since 1.0
 */
public final class Ini
{
    protected static Logger logger = LoggerFactory.getLogger(Ini.class);

    /**
     * Variable to represent the date format
     */
    private String mstrDateFmt = "yyyy-MM-dd";

    /**
     * Variable to represent the timestamp format
     */
    private String mstrTimeStampFmt = "yyyy-MM-dd HH:mm:ss";

    /**
     * Variable to denote the successfull load operation.
     */
    private boolean mblnLoaded = false;

    /**
     * Variable to hold the ini file name and full path
     */
    private String mstrFile;

    /**
     *
     */
    byte[] iniBytes = null;

    /**
     * Variable to hold the sections in an ini file.
     */
    private LinkedHashMap<String,INISection> mhmapSections;

    /**
     * Variable to hold environment variables *
     */
    private Properties mpropEnv;

    public Ini() {
        this.mpropEnv = _getEnvVars();
        this.mhmapSections = new LinkedHashMap<String,INISection>();
    }


    public Ini(byte[] aInibytes) throws IOException {
        this();
        this.iniBytes = aInibytes;
        _loadFile();
    }

    /**
     * Create a iniFile object from the file named in the parameter.
     * @param pstrPathAndName The full path and name of the ini file to be used.
     */
    public Ini(String pstrPathAndName) throws IOException {
        this();
        this.mstrFile = pstrPathAndName;
        if (_checkFile(pstrPathAndName)) _loadFile();
    }

    public void loadIni(String pstrPathAndName) throws IOException {
        this.mpropEnv = _getEnvVars();
        this.mhmapSections = new LinkedHashMap<String,INISection>();
        this.mstrFile = pstrPathAndName;
        // Load the specified INI file.
        if (_checkFile(pstrPathAndName)) _loadFile();
    }

/*    public void loadIni() throws IOException {
        // Load the specified INI file.
        if (_checkFile(this.mstrFile)) _loadFile();
    }*/

/*------------------------------------------------------------------------------
 * Getters
------------------------------------------------------------------------------*/
    /**
     * Returns the ini file name being used.
     * @return the INI file name.
     */
    public String getFileName()
    {
        return this.mstrFile;
    }


    /**
     * old style
     * @param header
     * @param property
     * @return
     */
    public String getValue(String header, String property){
        return getStringProperty(header,property);
    }
    /**
     * Returns the specified string property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the string property value.
     */
    public String getStringProperty(String header, String property)
    {
        String strRet = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            if (objProp != null)
            {
                strRet = objProp.getPropValue();
                objProp = null;
            }
            objSec = null;
        }
        return strRet;
    }

    /**
     * same as getBooleanProperty()
     * @param header
     * @param property
     * @return
     * @deprecated use getBooleanProperty()
     */
    @Deprecated
    public boolean getBoolValue(String header, String property) {
        Boolean aBoolean = getBooleanProperty(header, property);
        return aBoolean == null?false:aBoolean;
    }

    /**
     * Returns the specified boolean property from the specified section.
     * This method considers the following values as boolean values.
     * <ol>
     * <li>YES/yes/Yes - boolean true</li>
     * <li>NO/no/No  - boolean false</li>
     * <li>1 - boolean true</li>
     * <li>0 - boolean false</li>
     * <li>TRUE/True/true - boolean true</li>
     * <li>FALSE/False/false - boolean false</li>
     * </ol>
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the boolean value
     */
    public Boolean getBooleanProperty(String header, String property)
    {
        boolean blnRet = false;
        String strVal = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            if (objProp != null)
            {
                strVal = objProp.getPropValue().toUpperCase();
                if (strVal.equals("YES") || strVal.equals("TRUE") ||
                        strVal.equals("1"))
                {
                    blnRet = true;
                }
                objProp = null;
            }
            objSec = null;
        }
        return new Boolean(blnRet);
    }

    /**
     * same as getIntegerProperty()
     * @param header
     * @param property
     * @return
     * @deprecated use getIntegerProperty()
     */
    public int getIntValue(String header, String property) {
        Integer integer = getIntegerProperty(header, property);
        return integer==null?-1: integer;
    }

    /**
     * Returns the specified integer property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the integer property value.
     */
    public Integer getIntegerProperty(String header, String property)
    {
        Integer intRet = null;
        String strVal = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            try
            {
                if (objProp != null)
                {
                    strVal = objProp.getPropValue();
                    if (strVal != null) intRet = new Integer(strVal);
                }
            }
            catch (NumberFormatException e)
            {
                logger.warn("Warning in Ini", e);
            }
            finally
            {
                if (objProp != null) objProp = null;
            }
            objSec = null;
        }
        return intRet;
    }


    public long getLongValue(String header, String property) {
        Long aLong = getLongProperty(header, property);
        return aLong==null?-1:aLong;
    }
    /**
     * Returns the specified long property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the long property value.
     */
    public Long getLongProperty(String header, String property)
    {
        Long lngRet = null;
        String strVal = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            try
            {
                if (objProp != null)
                {
                    strVal = objProp.getPropValue();
                    if (strVal != null) lngRet = new Long(strVal);
                }
            }
            catch (NumberFormatException e)
            {
                logger.warn("Warning in Ini", e);
            }
            finally
            {
                if (objProp != null) objProp = null;
            }
            objSec = null;
        }
        return lngRet;
    }

    /**
     * Returns the specified double property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the double property value.
     */
    public Double getDoubleProperty(String header, String property)
    {
        Double dblRet = null;
        String strVal = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            try
            {
                if (objProp != null)
                {
                    strVal = objProp.getPropValue();
                    if (strVal != null) dblRet = new Double(strVal);
                }
            }
            catch (NumberFormatException e)
            {
                logger.warn("Warning in Ini", e);
            }
            finally
            {
                if (objProp != null) objProp = null;
            }
            objSec = null;
        }
        return dblRet;
    }

    /**
     * Returns the specified date property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the date property value.
     */
    public Date getDateProperty(String header, String property)
    {
        Date dtRet = null;
        String strVal = null;
        DateFormat dtFmt = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            try
            {
                if (objProp != null) strVal = objProp.getPropValue();
                if (strVal != null)
                {
                    dtFmt = new SimpleDateFormat(this.mstrDateFmt);
                    dtRet = dtFmt.parse(strVal);
                }
            }
            catch (ParseException e)
            {
                logger.warn("Warning in Ini", e);
            }
            catch (IllegalArgumentException e)
            {
                logger.warn("Warning in Ini", e);
            }
            finally
            {
                if (objProp != null) objProp = null;
            }
            objSec = null;
        }
        return dtRet;
    }

    /**
     * Returns the specified date property from the specified section.
     * @param header the INI section name.
     * @param property    the property to be retrieved.
     * @return the date property value.
     */
    public Date getTimestampProperty(String header, String property)
    {
        Timestamp tsRet = null;
        Date dtTmp = null;
        String strVal = null;
        DateFormat dtFmt = null;
        INIProperty objProp = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objProp = objSec.getProperty(property);
            try
            {
                if (objProp != null) strVal = objProp.getPropValue();
                if (strVal != null)
                {
                    dtFmt = new SimpleDateFormat(this.mstrDateFmt);
                    dtTmp = dtFmt.parse(strVal);
                    tsRet = new Timestamp(dtTmp.getTime());
                }
            }
            catch (ParseException e)
            {
                logger.warn("Warning in Ini", e);
            }
            catch (IllegalArgumentException e)
            {
                logger.warn("Warning in Ini", e);
            }
            finally
            {
                if (objProp != null) objProp = null;
            }
            objSec = null;
        }
        return tsRet;
    }

/*------------------------------------------------------------------------------
 * Setters
------------------------------------------------------------------------------*/
    /**
     * Sets the comments associated with a section.
     * @param header  the section name
     * @param astrComments the comments.
     */
    public void addSection(String header, String astrComments)
    {
        getSection(header).setSecComments(_delRemChars(astrComments));
    }


    public void setValue(String header, String property, boolean value) {
        setBooleanProperty(header, property, value, null);
    }

    public void setValue(String header, String property,int value) {
        setIntegerProperty(header, property, value, null);
    }

    public void setValue(String header, String property,long value) {
        setLongProperty(header, property, value, null);
    }

    public void setValue(String header, String property, String value) {
        setStringProperty(header, property, value, null);
    }

    /**
     * Sets the specified string property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param astrVal    the property value
     * @param astrComments    the property comment
     */
    public void setStringProperty(String header, String property,
                                  String astrVal, String astrComments)
    {
        getSection(header).setProperty(property, astrVal, astrComments);
    }

    private INISection getSection(String header) {

        INISection objSec = this.mhmapSections.get(header);
        if (objSec == null)
        {
            objSec = new INISection(header);
            this.mhmapSections.put(header, objSec);
        }
        return objSec;
    }

    /**
     * Sets the specified boolean property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param ablnVal     the boolean value to be persisted
     */
    public void setBooleanProperty(String header, String property,
                                   boolean ablnVal, String astrComments)
    {
        if (ablnVal)
            getSection(header).setProperty(property, "TRUE", astrComments);
        else
            getSection(header).setProperty(property, "FALSE", astrComments);
    }

    /**
     * Sets the specified integer property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param aintVal     the int property to be persisted.
     */
    public void setIntegerProperty(String header, String property,
                                   int aintVal, String astrComments)
    {
        getSection(header).setProperty(property, Integer.toString(aintVal), astrComments);
    }

    /**
     * Sets the specified long property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param alngVal     the long value to be persisted.
     */
    public void setLongProperty(String header, String property,
                                long alngVal, String astrComments)
    {
        getSection(header).setProperty(property, Long.toString(alngVal), astrComments);
    }

    /**
     * Sets the specified double property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param adblVal     the double value to be persisted.
     */
    public void setDoubleProperty(String header, String property,
                                  double adblVal, String astrComments)
    {
        getSection(header).setProperty(property, Double.toString(adblVal), astrComments);
    }

    /**
     * Sets the specified java.util.Date property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param adtVal      the date value to be persisted.
     */
    public void setDateProperty(String header, String property,
                                Date adtVal, String astrComments)
    {
        INISection objSec = getSection(header);
        getSection(header).setProperty(property, _utilDateToStr(adtVal, this.mstrDateFmt),
                astrComments);
    }

    /**
     * Sets the specified java.sql.Timestamp property.
     * @param header the INI section name.
     * @param property    the property to be set.
     * @param atsVal      the timestamp value to be persisted.
     */
    public void setTimestampProperty(String header, String property,
                                     Timestamp atsVal, String astrComments)
    {
        INISection objSec = getSection(header);
        getSection(header).setProperty(property, _timeToStr(atsVal, this.mstrTimeStampFmt),
                astrComments);
    }

    /**
     * Sets the format to be used to interpreat date values.
     * @param astrDtFmt the format string
     * @throws IllegalArgumentException if the if the given pattern is invalid
     */
    public void setDateFormat(String astrDtFmt)
            throws IllegalArgumentException
    {
        if (!_checkDateTimeFormat(astrDtFmt))
            throw new IllegalArgumentException("The specified date pattern is invalid!");
        this.mstrDateFmt = astrDtFmt;
    }

    /**
     * Sets the format to be used to interpreat timestamp values.
     * @param astrTSFmt the format string
     * @throws IllegalArgumentException if the if the given pattern is invalid
     */
    public void setTimeStampFormat(String astrTSFmt)
    {
        if (!_checkDateTimeFormat(astrTSFmt))
            throw new IllegalArgumentException("The specified timestamp pattern is invalid!");
        this.mstrTimeStampFmt = astrTSFmt;
    }

    /*------------------------------------------------------------------------------
     * Public methods
    ------------------------------------------------------------------------------*/
    public int getTotalSections()
    {
        return this.mhmapSections.size();
    }

    /**
     * Returns a string array containing names of all sections in INI file.
     * @return the string array of section names
     */
    public String[] getAllSectionNames()
    {
        int iCntr = 0;
        Iterator iter = null;
        String[] arrRet = null;

        try
        {
            if (this.mhmapSections.size() > 0)
            {
                arrRet = new String[this.mhmapSections.size()];
                iter = this.mhmapSections.keySet().iterator();
                while (iter.hasNext())
                {
                    arrRet[iCntr] = (String) iter.next();
                    iCntr++;
                }
            }
        }
        catch (NoSuchElementException e)
        {
            logger.warn("Warning in Ini", e);
        }
        finally
        {
            if (iter != null) iter = null;
        }
        return arrRet;
    }

    /**
     * Returns a string array containing names of all the properties under specified section.
     * @param header the name of the section for which names of properties is to be retrieved.
     * @return the string array of property names.
     */
    public String[] getPropertyNames(String header)
    {
        String[] arrRet = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            arrRet = objSec.getPropNames();
            objSec = null;
        }
        return arrRet;
    }

    /**
     * Returns a map containing all the properties under specified section.
     * @param header the name of the section for which properties are to be retrieved.
     * @return the map of properties.
     */
    public Map<String,INIProperty> getPropertiesAsMap(String header)
    {
        Map<String,INIProperty> hmRet = null;
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            hmRet = objSec.getProperties();
            objSec = null;
        }
        return hmRet;
    }

    public Properties getProperties(String header){

        Map<String, INIProperty> propertiesAsMap = getPropertiesAsMap(header);
        if(propertiesAsMap == null)
            return null;

        Properties properties = new Properties();
        for (INIProperty property : propertiesAsMap.values()) {
            properties.setProperty(property.getPropName(), property.getPropValue()) ;
        }
        return properties;
    }

    /**
     * Removed specified property from the specified section. If the specified
     * section or the property does not exist, does nothing.
     * @param header the section name.
     * @param property    the name of the property to be removed.
     */
    public void removeProperty(String header, String property)
    {
        INISection objSec = null;

        objSec = (INISection) this.mhmapSections.get(header);
        if (objSec != null)
        {
            objSec.removeProperty(property);
            objSec = null;
        }
    }

    /**
     * Removes the specified section if one exists, otherwise does nothing.
     * @param header the name of the section to be removed.
     */
    public void removeSection(String header)
    {
        if (this.mhmapSections.containsKey(header))
            this.mhmapSections.remove(header);
    }


    public void saveIni(String file) throws IOException {
        this.mstrFile = file;
        save();
    }
    public void saveIni() throws IOException{
        save();
    }
    /**
     * Flush changes back to the disk file. If the disk file does not exists then
     * creates the new one.
     */
    public boolean save() throws IOException {
        boolean blnRet = false;
        File objFile = null;
        String strName = null;
        String strTemp = null;
        Iterator itrSec = null;
        INISection objSec = null;
        FileWriter objWriter = null;

        try
        {
            if (this.mhmapSections.size() == 0) return false;
            objFile = new File(this.mstrFile);
            if (objFile.exists()) objFile.delete();
            objWriter = new FileWriter(objFile);
            itrSec = this.mhmapSections.keySet().iterator();
            while (itrSec.hasNext())
            {
                strName = (String) itrSec.next();
                objSec = (INISection) this.mhmapSections.get(strName);
                strTemp = objSec.toString();
                objWriter.write(strTemp);
                objWriter.write("\r\n");
                objSec = null;
            }
            blnRet = true;
        }
        finally
        {
            if (objWriter != null)
            {
                _closeWriter(objWriter);
                objWriter = null;
            }
            if (objFile != null) objFile = null;
            if (itrSec != null) itrSec = null;
        }
        return blnRet;
    }

    /*------------------------------------------------------------------------------
     * Helper functions
     *----------------------------------------------------------------------------*/
    /**
     * Procedure to read environment variables.
     * Thanx to http://www.rgagnon.com/howto.html for this implementation.
     */
    private Properties _getEnvVars()
    {
        Process p = null;
        Properties envVars = new Properties();

        try
        {
            Runtime r = Runtime.getRuntime();
            String OS = System.getProperty("os.name").toLowerCase();

            if (OS.indexOf("windows 9") > -1)
            {
                p = r.exec("command.com /c set");
            }
            else if ((OS.indexOf("nt") > -1) ||
                    (OS.indexOf("windows") > -1))
            {
                p = r.exec("cmd.exe /c set");
            }
            else
            {
                // our last hope, we assume Unix (thanks to H. Ware for the fix)
                p = r.exec("env");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
            {
                int idx = line.indexOf('=');
                String key = line.substring(0, idx);
                String value = line.substring(idx + 1);
                envVars.setProperty(key, value);
            }
        }
        catch (Exception e)
        {
            logger.warn("Warning in Ini", e);
        }
        return envVars;
    }

    /**
     * Helper function to check the date time formats.
     * @param astrDtFmt the date time format string to be checked.
     * @return true for valid date/time format, false otherwise.
     */
    private boolean _checkDateTimeFormat(String astrDtFmt)
    {
        boolean blnRet = false;
        DateFormat objFmt = null;

        try
        {
            objFmt = new SimpleDateFormat(astrDtFmt);
            blnRet = true;
        }
        catch (NullPointerException e)
        {
            logger.warn("Warning in Ini", e);
        }
        catch (IllegalArgumentException e)
        {
            logger.warn("Warning in Ini", e);
        }
        finally
        {
            if (objFmt != null) objFmt = null;
        }
        return blnRet;
    }

    /**
     * Reads the INI file and load its contentens into a section collection after
     * parsing the file line by line.
     */
    private void _loadFile() throws IOException {
        int iPos = -1;
        String strLine = null;
        String strSection = null;
        String strRemarks = null;
        BufferedReader objBRdr = null;
        InputStreamReader objFRdr = null;
        INISection objSec = null;

        try
        {
            if(this.mstrFile != null)
                objFRdr = new FileReader(this.mstrFile);
            else if(this.iniBytes != null)
                objFRdr = new InputStreamReader(new ByteArrayInputStream(iniBytes));
            else
                throw new FileNotFoundException(this.mstrFile);
            if (objFRdr != null)
            {
                objBRdr = new BufferedReader(objFRdr);
                if (objBRdr != null)
                {
                    while (objBRdr.ready())
                    {
                        iPos = -1;
                        strLine = null;
                        strLine = objBRdr.readLine().trim();
                        if (strLine == null)
                        {
                        }
                        else if (strLine.length() == 0)
                        {
                        }
                        else if (strLine.substring(0, 1).equals(";")|| strLine.substring(0, 1).equals("#"))
                        {
                            if (strRemarks == null)
                                strRemarks = strLine.substring(1);
                            else if (strRemarks.length() == 0)
                                strRemarks = strLine.substring(1);
                            else
                                strRemarks = strRemarks + "\r\n" + strLine.substring(1);
                        }
                        else if (strLine.startsWith("[") && strLine.endsWith("]"))
                        {
                            // Section start reached create new section
                            if (objSec != null)
                                this.mhmapSections.put(strSection.trim(), objSec);
                            objSec = null;
                            strSection = strLine.substring(1, strLine.length() - 1);
                            objSec = new INISection(strSection.trim(), strRemarks);
                            strRemarks = null;
                        }
                        else if (((iPos = strLine.indexOf("=")) > 0) && (objSec != null))
                        {
                            // read the key value pair 012345=789
                            objSec.setProperty(strLine.substring(0, iPos).trim(),
                                    strLine.substring(iPos + 1).trim(),
                                    strRemarks);
                            strRemarks = null;
                        }
                    }
                    if (objSec != null)
                        this.mhmapSections.put(strSection.trim(), objSec);
                    this.mblnLoaded = true;
                }
            }
        }
        finally
        {
            if (objBRdr != null)
            {
                _closeReader(objBRdr);
                objBRdr = null;
            }
            if (objFRdr != null)
            {
                _closeReader(objFRdr);
                objFRdr = null;
            }
            if (objSec != null) objSec = null;
        }
    }

    /**
     * Helper function to close a reader object.
     * @param anObjRdr the reader to be closed.
     */
    private void _closeReader(Reader anObjRdr)
    {
        if (anObjRdr == null) return;
        try
        {
            anObjRdr.close();
        }
        catch (IOException e)
        {
            logger.warn("Warning in Ini", e);
        }
    }

    /**
     * Helper function to close a writer object.
     * @param aObjWriter the writer to be closed.
     */
    private void _closeWriter(Writer aObjWriter)
    {
        if (aObjWriter == null) return;

        try
        {
            aObjWriter.close();
        }
        catch (IOException e)
        {
            logger.warn("Warning in Ini", e);
        }
    }

    /**
     * Helper method to check the existance of a file.
     * @param aStrFile the full path and name of the file to be checked.
     * @return true if file exists, false otherwise.
     */
    private boolean _checkFile(String aStrFile)
    {
        boolean blnRet = false;
        File objFile = null;

        try
        {
            objFile = new File(aStrFile);
            blnRet = (objFile.exists() && objFile.isFile());
        }
        catch (Exception e)
        {
            blnRet = false;
            logger.warn("Warning in Ini", e);
        }
        finally
        {
            if (objFile != null) objFile = null;
        }
        return blnRet;
    }

    /**
     * Converts a java.util.date into String
     * @param adt      Date that need to be converted to String
     * @param aStrFmt The date format pattern.
     * @return String
     */
    private String _utilDateToStr(Date adt, String aStrFmt)
    {
        String strRet = null;
        SimpleDateFormat dtFmt = null;

        try
        {
            dtFmt = new SimpleDateFormat(aStrFmt);
            strRet = dtFmt.format(adt);
        }
        catch (Exception e)
        {
            strRet = null;
            logger.warn("Warning in Ini", e);
        }
        finally
        {
            if (dtFmt != null) dtFmt = null;
        }
        return strRet;
    }

    /**
     * Converts the given sql timestamp object to a string representation. The format
     * to be used is to be obtained from the configuration file.
     * @param aObjTS  the sql timestamp object to be converted.
     * @param aStrFmt If true formats the string using GMT  timezone
     *                otherwise using local timezone.
     * @return the formatted string representation of the timestamp.
     */
    private String _timeToStr(Timestamp aObjTS, String aStrFmt)
    {
        String strRet = null;
        SimpleDateFormat dtFmt = null;

        try
        {
            dtFmt = new SimpleDateFormat(aStrFmt);
            strRet = dtFmt.format(aObjTS);
        }
        catch (IllegalArgumentException iae)
        {
            strRet = "";
            logger.warn("Warning in Ini", iae);
        }
        catch (NullPointerException npe)
        {
            strRet = "";
            logger.warn("Warning in Ini", npe);
        }
        finally
        {
            if (dtFmt != null) dtFmt = null;
        }
        return strRet;
    }

    /**
     * This function deletes the remark characters ';' from source string
     * @param aStrSrc the source  string
     * @return the converted string
     */
    private String _delRemChars(String aStrSrc)
    {
        int intPos = 0;

        if (aStrSrc == null|| aStrSrc.trim().equalsIgnoreCase("")) return null;
        while ((intPos = aStrSrc.indexOf(";")) >= 0)
        {
            if (intPos == 0)
                aStrSrc = aStrSrc.substring(intPos + 1);
            else if (intPos > 0)
                aStrSrc = aStrSrc.substring(0, intPos) + aStrSrc.substring(intPos + 1);
        }
        return aStrSrc;
    }

    /**
     * This function adds a remark character ';' in source string.
     * @param aStrSrc source string
     * @return converted string.
     */
    private String _addRemChars(String aStrSrc)
    {
        int intLen = 2;
        int intPos = 0;
        int intPrev = 0;

        String strLeft = null;
        String strRight = null;

        if (aStrSrc == null || aStrSrc.trim().equalsIgnoreCase("")) return null;
        while (intPos >= 0)
        {
            intLen = 2;
            intPos = aStrSrc.indexOf("\r\n", intPrev);
            if (intPos < 0)
            {
                intLen = 1;
                intPos = aStrSrc.indexOf("\n", intPrev);
                if (intPos < 0) intPos = aStrSrc.indexOf("\r", intPrev);
            }
            if (intPos == 0)
            {
                aStrSrc = ";\r\n" + aStrSrc.substring(intPos + intLen);
                intPrev = intPos + intLen + 1;
            }
            else if (intPos > 0)
            {
                strLeft = aStrSrc.substring(0, intPos);
                strRight = aStrSrc.substring(intPos + intLen);
                if (strRight == null)
                    aStrSrc = strLeft;
                else if (strRight.length() == 0)
                    aStrSrc = strLeft;
                else
                    aStrSrc = strLeft + "\r\n;" + strRight;
                intPrev = intPos + intLen + 1;
            }
        }
        if (!aStrSrc.substring(0, 1).equals(";"))
            aStrSrc = ";" + aStrSrc;
        aStrSrc = aStrSrc + "\r\n";
        return aStrSrc;
    }
    /*------------------------------------------------------------------------------
     * Main entry point to test the functionality.
     *----------------------------------------------------------------------------*/
    /**
     * The main entry point for testing.
     * @param astrArgs the command line arguments array if any.
     */
    public static void main(String[] astrArgs) throws IOException {
        Ini objINI = null;
        String strFile = null;

        //if (astrArgs.length == 0) return;

        //strFile = astrArgs[0];
        strFile = "deneme.ini";
        // Following call will load the strFile if one exists.
        objINI = new Ini(strFile);

        objINI.addSection("QADatabase", "QA database connection details\nUsed for QA Testing");
        objINI.setStringProperty("QADatabase", "SID", "ORCL", null);
        objINI.setStringProperty("QADatabase", "UserId", "System", null);
        objINI.setStringProperty("QADatabase", "Password", "Manager", null);
        objINI.setStringProperty("QADatabase", "HostName", "DBServer", null);
        objINI.setIntegerProperty("QADatabase", "Port", 1521, null);
        objINI.setStringProperty("QADatabase", "OracleHome", "%ORACLE_HOME%", null);
//
//        objINI.setSectionComments("Folders", "Directories where generated files are stored");
        objINI.setStringProperty("Folders", "folder1", "G:\\Temp", null);
        objINI.setStringProperty("Folders", "folder2", "G:\\Temp\\Backup", null);

        // Save changes back to strFile.
        objINI.save();
        objINI = null;
    }

/*------------------------------------------------------------------------------
 * Private class representing the INI Section.
 *----------------------------------------------------------------------------*/
    /**
     * Class to represent the individual ini file section.
     * @author Prasad P. Khandekar
     * @version 1.0
     * @since 1.0
     */
    private class INISection
    {

        /**
         * Variable to hold any comments associated with this section
         */
        private String mstrComment;

        /**
         * Variable to hold the section name.
         */
        private String mstrName;

        /**
         * Variable to hold the properties falling under this section.
         */
        private LinkedHashMap<String,INIProperty> mhmapProps;

        /**
         * Construct a new section object identified by the name specified in
         * parameter.
         * @param pstrSection The new sections name.
         */
        public INISection(String pstrSection)
        {
            this.mstrName = pstrSection;
            this.mhmapProps = new LinkedHashMap<String, INIProperty>();
        }

        /**
         * Construct a new section object identified by the name specified in
         * parameter and associated comments.
         * @param pstrSection  The new sections name.
         * @param pstrComments the comments associated with this section.
         */
        public INISection(String pstrSection, String pstrComments)
        {
            this.mstrName = pstrSection;
            this.mstrComment = _delRemChars(pstrComments);
            this.mhmapProps = new LinkedHashMap<String, INIProperty>();
        }

        /**
         * Returns any comments associated with this section
         * @return the comments
         */
        public String getSecComments()
        {
            return this.mstrComment;
        }

        /**
         * Returns name of the section.
         * @return Name of the section.
         */
        public String getSecName()
        {
            return this.mstrName;
        }

        /**
         * Sets the comments associated with this section.
         * @param astrComments the comments
         */
        public void setSecComments(String astrComments)
        {
            this.mstrComment = _delRemChars(astrComments);
        }

        /**
         * Sets the section name.
         * @param astrName the section name.
         */
        public void setSecName(String astrName)
        {
            this.mstrName = astrName;
        }

        /**
         * Removes specified property value from this section.
         * @param property The name of the property to be removed.
         */
        public void removeProperty(String property)
        {
            if (this.mhmapProps.containsKey(property))
                this.mhmapProps.remove(property);
        }

        /**
         * Creates or modifies the specified property value.
         * @param property     The name of the property to be created or modified.
         * @param astrValue    The new value for the property.
         * @param astrComments the associated comments
         */
        public void setProperty(String property, String astrValue, String astrComments)
        {
            this.mhmapProps.put(property, new INIProperty(property, astrValue, astrComments));
        }

        /**
         * Returns a map of all properties.
         * @return a map of all properties
         */
        public Map<String,INIProperty> getProperties()
        {
            return Collections.unmodifiableMap(this.mhmapProps);
        }

        /**
         * Returns a string array containing names of all the properties under
         * this section.
         * @return the string array of property names.
         */
        public String[] getPropNames()
        {
            int iCntr = 0;
            String[] arrRet = null;
            Iterator iter = null;

            try
            {
                if (this.mhmapProps.size() > 0)
                {
                    arrRet = new String[this.mhmapProps.size()];
                    for (iter = this.mhmapProps.keySet().iterator(); iter.hasNext();)
                    {
                        arrRet[iCntr] = (String) iter.next();
                        iCntr++;
                    }
                }
            }
            catch (NoSuchElementException NSEExIgnore)
            {
                arrRet = null;
                logger.warn("Warning in Ini", NSEExIgnore);
            }
            return arrRet;
        }

        /**
         * Returns underlying value of the specified property.
         * @param property the property whose underlying value is to be etrieved.
         * @return the property value.
         */
        public INIProperty getProperty(String property)
        {
            INIProperty objRet = null;

            if (this.mhmapProps.containsKey(property))
                objRet = (INIProperty) this.mhmapProps.get(property);
            return objRet;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            Set colKeys = null;
            String strRet = "";
            Iterator iter = null;
            INIProperty objProp = null;
            StringBuffer objBuf = new StringBuffer();

            if (this.mstrComment != null)
                objBuf.append(_addRemChars(this.mstrComment));
            objBuf.append("[" + this.mstrName + "]\r\n");
            colKeys = this.mhmapProps.keySet();
            if (colKeys != null)
            {
                iter = colKeys.iterator();
                if (iter != null)
                {
                    while (iter.hasNext())
                    {
                        objProp = (INIProperty) this.mhmapProps.get(iter.next());
                        objBuf.append(objProp.toString());
                        objBuf.append("\r\n");
                        objProp = null;
                    }
                }
            }
            strRet = objBuf.toString();

            objBuf = null;
            iter = null;
            colKeys = null;
            return strRet;
        }
    }

    /*------------------------------------------------------------------------------
     * Private class representing the INI Property.
     *----------------------------------------------------------------------------*/
    /**
     * This class represents a key value pair called property in an INI file.
     * @author Prasad P. Khandekar
     * @version 1.0
     * @since 1.0
     */
    private class INIProperty
    {

        /**
         * Variable to hold name of this property
         */
        private String mstrName;
        /**
         * Variable to hold value of this property
         */
        private String mstrValue;
        /**
         * Variable to hold comments associated with this property
         */
        private String mstrComments;

        /**
         * Constructor
         * @param pstrName  the name of this property.
         * @param pstrValue the value of this property.
         */
        public INIProperty(String pstrName, String pstrValue)
        {
            this.mstrName = pstrName;
            this.mstrValue = pstrValue;
        }

        /**
         * Constructor
         * @param pstrName     the name of this property.
         * @param pstrValue    the value of this property.
         * @param pstrComments the comments associated with this property.
         */
        public INIProperty(String pstrName, String pstrValue, String pstrComments)
        {
            this.mstrName = pstrName;
            this.mstrValue = pstrValue;
            this.mstrComments = _delRemChars(pstrComments);
        }

        /**
         * Returns the string identifier (key part) of this property.
         * @return the string identifier of this property.
         */
        public String getPropName()
        {
            return this.mstrName;
        }

        /**
         * Returns value of this property. If value contains a reference to
         * environment avriable then this reference is replaced by actual value
         * before the value is returned.
         * @return the value of this property.
         */
        public String getPropValue()
        {
            int intStart = 0;
            int intEnd = 0;
            String strVal = null;
            String strVar = null;
            String strRet = null;

            strRet = this.mstrValue;
            intStart = strRet.indexOf("%");
            if (intStart >= 0)
            {
                intEnd = strRet.indexOf("%", intStart + 1);
                strVar = strRet.substring(intStart + 1, intEnd);
                strVal = mpropEnv.getProperty(strVar);
                if (strVal != null)
                {
                    strRet = strRet.substring(0, intStart) + strVal +
                            strRet.substring(intEnd + 1);
                }
            }
            return strRet;
        }

        /**
         * Returns comments associated with this property.
         * @return the associated comments if any.
         */
        public String getPropComments()
        {
            return this.mstrComments;
        }

        /**
         * Sets the string identifier (key part) of a property
         * @param astrName the string identifier of a property
         */
        public void setPropName(String astrName)
        {
            this.mstrName = astrName;
        }

        /**
         * Sets the property value
         * @param astrValue the value for the property
         */
        public void setPropValue(String astrValue)
        {
            this.mstrValue = astrValue;
        }

        /**
         * Sets the comments for a property
         * @param astrComments the comments
         */
        public void setPropComments(String astrComments)
        {
            this.mstrComments = _delRemChars(astrComments);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        public String toString()
        {
            String strRet = "";

            if (this.mstrComments != null)
                strRet = _addRemChars(mstrComments);
            strRet = strRet + this.mstrName + " = " + this.mstrValue;
            return strRet;
        }
    }
}
