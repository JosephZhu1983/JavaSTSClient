package com.kongzhonghd.util;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-31
 * Time: PM4:13
 * To change this template use File | Settings | File Templates.
 */
public class ObjectUtils
{
    public static Object deepClone(Object orig)
    {
        Object obj = null;
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return obj;
    }

    public static Object fromString(String s) throws IOException,
            ClassNotFoundException
    {
        byte[] data = Base64Coder.decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

    public static String toString(Serializable o) throws IOException
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(byteArrayOutputStream);
        oos.writeObject(o);
        oos.close();
        return new String(Base64Coder.encode(byteArrayOutputStream.toByteArray()));
    }
}
