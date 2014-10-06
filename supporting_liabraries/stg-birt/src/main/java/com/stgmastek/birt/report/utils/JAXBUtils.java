/*
 * Copyright (c) 2014 Mastek Ltd. All rights reserved.
 * 
 * This file is part of JBEAM. JBEAM is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * JBEAM is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for the specific language governing permissions and 
 * limitations.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JBEAM. If not, see <http://www.gnu.org/licenses/>.
 */
package com.stgmastek.birt.report.utils;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.w3c.dom.Node;


/**
 * @author Yogesh Jadhav
 * 
 */
public class JAXBUtils {

	
	private static JAXBContext jaxbContext;
	static{
		try {
			jaxbContext = JAXBContext.newInstance("com.stgmastek.birt.report.beans");
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void setCustomContext(JAXBContext context) throws JAXBException{
		jaxbContext = context;
	}
	public static JAXBContext getCustomContext() {
		return jaxbContext;
	}	
	
	private static Map<Class, MarshallerPool> MARSHALLER_OBJECT_POOL_CACHE 		= new HashMap<Class, MarshallerPool>();
	private static Map<Class, UnmarshallerPool> UNMARSHALLER_OBJECT_POOL_CACHE 	= new HashMap<Class, UnmarshallerPool>();

	private static class UnmarshallerPool extends AbstractObjectPool<Unmarshaller> {
		public JAXBContext context;

		public UnmarshallerPool(Class contextClass) {
			JAXBElementProvider elementProvider = new JAXBElementProvider();
			context = elementProvider.getPackageContext(contextClass);
			if (context == null) {
				try {
					context = elementProvider.getClassContext(contextClass);
				} catch (JAXBException e) {
					throw new RuntimeException(e);
				}
			}
		}
		@Override protected Unmarshaller createObject() {
			try {
				return context.createUnmarshaller();
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		}
		@Override protected int getMaxPoolSize() {	return 5; }
	}

	private static class MarshallerPool extends AbstractObjectPool<Marshaller> {
		public JAXBContext context;

		public MarshallerPool(Class contextClass) {
			JAXBElementProvider elementProvider = new JAXBElementProvider();
			context = elementProvider.getPackageContext(contextClass);
			if (context == null) {
				try {
					context = elementProvider.getClassContext(contextClass);
				} catch (JAXBException e) {
					throw new RuntimeException(e);
				}
			}
		}
		@Override protected Marshaller createObject() {
			try {
				return context.createMarshaller();
			} catch (JAXBException e) {
				throw new RuntimeException(e);
			}
		}
		@Override protected int getMaxPoolSize() {	return 5; }
	}

	public static String writeFromObject(Object obj) {
	    if(obj == null) 
	        return "";
	    
		StringWriter stringWriter = new StringWriter();
		Class c = obj.getClass();
		Marshaller m = createMarshaller(c);
		try {
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(obj, stringWriter);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseMarshaller(c, m);
		}

		return stringWriter.getBuffer().toString();
	}

	@SuppressWarnings("unchecked")
	public static String writeFromObject(Object obj, String tagName) {
		StringWriter stringWriter = new StringWriter();
		Class c = obj.getClass();
		Marshaller m = createMarshaller(c);
		try {
			m.setProperty("com.sun.xml.bind.xmlDeclaration", false);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(new JAXBElement(new QName("", tagName), obj.getClass(), obj), stringWriter);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseMarshaller(c, m);
		}
		return stringWriter.getBuffer().toString();
	}

	public static String writeFromObject(Object obj, boolean printDeclaration) {
		StringWriter stringWriter = new StringWriter();
		Class c = obj.getClass();
		Marshaller m = createMarshaller(c);
		try {
			m.setProperty("com.sun.xml.bind.xmlDeclaration", printDeclaration);
			m.marshal(obj, stringWriter);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseMarshaller(c, m);
		}
		return stringWriter.getBuffer().toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T readFromFile(File s, Class<T> cls) {
		Unmarshaller u = null;
		try {
			u = createUnmarshaller(cls);
			Object o = u.unmarshal(s);
			if (o instanceof JAXBElement) {
				o = ((JAXBElement) o).getValue();
			}
			return cls.cast(o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseUnmarshaller(cls, u);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readFromSource(Node s, Class<T> cls) {
		Unmarshaller u = createUnmarshaller(cls);
		try {
			Object o = u.unmarshal(s);
			if (o instanceof JAXBElement) {
				o = ((JAXBElement) o).getValue();
			}
			return cls.cast(o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseUnmarshaller(cls, u);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T readFromSource(String s, Class<T> cls) {
	    if(s == null || s.trim().length() == 0)
	        return null;
	    
		Unmarshaller u = createUnmarshaller(cls);
		
		try {
			StringReader reader = new StringReader(s);
			Object o = u.unmarshal(reader);
			if (o instanceof JAXBElement) {
				o = ((JAXBElement) o).getValue();
			}
			return cls.cast(o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			releaseUnmarshaller(cls, u);
		}
	}

	private static Unmarshaller createUnmarshaller(Class cls) {
		UnmarshallerPool pool = UNMARSHALLER_OBJECT_POOL_CACHE.get(cls);
		if (pool == null) {
			pool = new UnmarshallerPool(cls);
			UNMARSHALLER_OBJECT_POOL_CACHE.put(cls, pool);
		}
		return pool.getObject();
	}

	private static void releaseUnmarshaller(Class cls, Unmarshaller m) {
		if (m != null) {
			UnmarshallerPool pool = UNMARSHALLER_OBJECT_POOL_CACHE.get(cls);
			pool.releaseObject(m);
		}
	}

	private static Marshaller createMarshaller(Class cls) {
		MarshallerPool pool = MARSHALLER_OBJECT_POOL_CACHE.get(cls);
		if (pool == null) {
			pool = new MarshallerPool(cls);
			MARSHALLER_OBJECT_POOL_CACHE.put(cls, pool);
		}
		return pool.getObject();
	}

	private static void releaseMarshaller(Class cls, Marshaller m) {
		if (m != null) {
			MarshallerPool pool = MARSHALLER_OBJECT_POOL_CACHE.get(cls);
			pool.releaseObject(m);
		}
	}

	public static String marshalValue(Object value) throws Exception {
		if(value == null) {
			return "";
		}
//		if(value instanceof Date) {
//			return marshalDate((Date) value);
//		}else {
			return String.valueOf(value);
//		}
	}
	
//	public static Date unmarshalDate(String date){
//		if(CommonUtils.isEmpty(date)){
//			return null;
//		}
//		return ISO8601DateUtil.parse(date);
//	}
	
//	public static String marshalDate(Date date){
//		if(date == null){
//			return "";
//		}
//		return ISO8601DateUtil.format(date);
//	}
}
