package com.allinfinance.framework.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XstreamDateConverter implements Converter {

	public void marshal(Object object, HierarchicalStreamWriter writer,
			MarshallingContext context) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.CHINA);

		writer.setValue(dateFormat.format(object));

	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Object obj = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.CHINA);
			obj = dateFormat.parseObject(reader.getValue());
		} catch (Exception e) {

		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class arg0) {
		return arg0.equals(Date.class);
	}

}
