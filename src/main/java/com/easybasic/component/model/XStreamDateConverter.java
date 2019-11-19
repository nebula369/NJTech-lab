package com.easybasic.component.model;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class XStreamDateConverter extends AbstractSingleValueConverter {

    private static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");


    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(Date.class);
    }

    @Override
    public Object fromString(String s) {
        try {
            return DEFAULT_DATEFORMAT.parseObject(s);
        } catch (ParseException e) {
            return new Date();
        }
    }
}
