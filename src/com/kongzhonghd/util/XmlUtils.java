package com.kongzhonghd.util;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 3:04 PM
 */
public class XmlUtils
{
    public static String xmlToString(Document document)
    {
        OutputFormat format = new OutputFormat();
        format.setSuppressDeclaration(true);
        StringWriter out = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(out, format);
        try
        {
            xmlWriter.write(document);
            xmlWriter.flush();
        }
        catch (Exception ex)
        {
            return null;
        }
        return out.toString();
    }
}
