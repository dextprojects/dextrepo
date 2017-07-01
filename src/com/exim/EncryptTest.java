// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 1/25/2006 5:51:12 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   EncryptTest.java

package com.exim;


public class EncryptTest
{

    public EncryptTest()
    {
    }

    public static StringBuffer encrypt(String s)
    {
        String s1 = s;
        char c = '0';
        StringBuffer stringbuffer = new StringBuffer();
        if(s1.length() > 0 && s1.length() <= 4)
        {
            stringbuffer.append('#');
            stringbuffer.append('^');
            stringbuffer.append('W');
            stringbuffer.append('&');
            stringbuffer.append('?');
            stringbuffer.append('V');
            stringbuffer.append('$');
            stringbuffer.append('Z');
            stringbuffer.append('o');
            stringbuffer.append('@');
        } else
        if(s1.length() >= 5 && s1.length() <= 7)
        {
            stringbuffer.append('?');
            stringbuffer.append('X');
            stringbuffer.append('&');
            stringbuffer.append('&');
            stringbuffer.append('?');
            stringbuffer.append('Y');
            stringbuffer.append('M');
            stringbuffer.append('M');
            stringbuffer.append('o');
            stringbuffer.append('9');
        } else
        if(s1.length() >= 8 && s1.length() <= 10)
        {
            stringbuffer.append('&');
            stringbuffer.append('^');
            stringbuffer.append('&');
            //stringbuffer.append('-');
            stringbuffer.append('#');
            stringbuffer.append('?');
            stringbuffer.append('Y');
            stringbuffer.append('u');
            stringbuffer.append('#');
            stringbuffer.append('&');
            //stringbuffer.append('-');
            stringbuffer.append('7');
        } else
        if(s1.length() >= 11 && s1.length() <= 14)
        {
            stringbuffer.append('Q');
            stringbuffer.append('^');
            stringbuffer.append('*');
            stringbuffer.append('&');
            stringbuffer.append('?');
            stringbuffer.append('Y');
            stringbuffer.append('G');
            stringbuffer.append('g');
            stringbuffer.append('5');
            stringbuffer.append('6');
        } else
        {
            stringbuffer.append('#');
            stringbuffer.append('^');
            stringbuffer.append('&');
            stringbuffer.append('&');
            //stringbuffer.append('-');
            stringbuffer.append('?');
            stringbuffer.append('Y');
            stringbuffer.append('u');
            stringbuffer.append('Z');
            stringbuffer.append('o');
            stringbuffer.append('1');
        }
        for(int i = 0; i < s1.length(); i++)
        {
            if((i + 1) % 2 != 0)
                c = (char)(s1.charAt(i) + (i + 1));
            if((i + 1) % 2 == 0)
                c = (char)(s1.charAt(i) - (i + 1));
            stringbuffer.append(c);
        }

        System.out.println("..............Encrypted Password:...."+stringbuffer);
        return stringbuffer;
    }

    public StringBuffer decrypt(String s)
    {
        String s1 = s;
        s1 = s1.substring(10);
        char c = '0';
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = 0; i < s1.length(); i++)
        {
            if((i + 1) % 2 != 0)
                c = (char)(s1.charAt(i) - (i + 1));
            if((i + 1) % 2 == 0)
                c = (char)(s1.charAt(i) + (i + 1));
            stringbuffer.append(c);
        }

        return stringbuffer;
    }
}