package com.csc.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint({ "DefaultLocale", "NewApi" })
public class JsonObjectTools {
	/*** 字符串解析成Object */
	@SuppressLint("NewApi")
	public static Object mapToObject(String jsonString, Class<?> clazz) {
		if (jsonString == null || jsonString.isEmpty())
			return null;
		Object mainObject = JsonString.objectWithString(jsonString);
		return mapToObject(clazz, mainObject);
	}

	/*** 解析成Object */
	public static Object mapToObject(Class<?> clazz, Object mainObject) {
		Object o = null;
		if (mainObject != null) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) mainObject;
			if(map != null){
				o = mapToObject(clazz, map);
			}		
		}
		return o;
	}

	/*** 解析成Object */
	@SuppressLint({ "DefaultLocale", "NewApi" })
	public static Object mapToObject(Class<?> clazz, Map<String, Object> map) {
		Object o = null;
		if (map != null) {
			// 取到类下所有的属性，也就是变量名
			Field[] fields = clazz.getDeclaredFields();
			Field field;
			try {
				o = clazz.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			String valueString = "";
			for (int i = 0; i < fields.length; i++) {
				field = fields[i];
				String fieldName = field.getName();
				// 把属性的第一个字母处理成大写
				String stringLetter = fieldName.substring(0, 1).toUpperCase();
				// 取得set方法名，比如setLogo
				String setterName = "set" + stringLetter + fieldName.substring(1);
				// 真正取得set方法。
				Method setMethod = null;
				Class<?> fieldClass = field.getType();
				try {
					if (isHaveSuchMethod(clazz, setterName)) {
						if (fieldClass == String.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString == null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, "");// 为其赋值
							}else{
								setMethod.invoke(o, String.valueOf(map.get(fieldName)));// 为其赋值
							}			
						} else if (fieldClass == Integer.class || fieldClass == int.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);						
							valueString = String.valueOf(map.get(fieldName));
							if(valueString == null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, 0);// 为其赋值
							}else{
								setMethod.invoke(o, Integer.parseInt(valueString));// 为其赋值
							}
						} else if (fieldClass == Boolean.class || fieldClass == boolean.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString==null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, false);// 为其赋值
							}else{
								setMethod.invoke(o, Boolean.getBoolean(valueString));// 为其赋值
							}
						} else if (fieldClass == Short.class || fieldClass == short.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString==null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, 0);// 为其赋值
							}else{
								setMethod.invoke(o, Short.parseShort(valueString));// 为其赋值
							}
						} else if (fieldClass == Long.class || fieldClass == long.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString==null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, 0);// 为其赋值
							}else{
								setMethod.invoke(o, Long.parseLong(valueString));// 为其赋值
							}
						} else if (fieldClass == Double.class || fieldClass == double.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString ==null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, 0.0);// 为其赋值
							}else{
								setMethod.invoke(o, Double.parseDouble(valueString));// 为其赋值
							}
						} else if (fieldClass == Float.class || fieldClass == float.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							valueString = String.valueOf(map.get(fieldName));
							if(valueString ==null || valueString.isEmpty()||valueString.equalsIgnoreCase("null")){
								setMethod.invoke(o, 0.0f);// 为其赋值
							}else{
								setMethod.invoke(o, Float.parseFloat(valueString));// 为其赋值
							}
						} else if (fieldClass == BigInteger.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, BigInteger.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));// 为其赋值
						} else if (fieldClass == BigDecimal.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							setMethod.invoke(o, BigDecimal.valueOf(Long.parseLong(String.valueOf(map.get(fieldName)))));// 为其赋值
						} else if (fieldClass == Date.class) {
							setMethod = clazz.getMethod(setterName, fieldClass);
							if (map.get(fieldName).getClass() == java.sql.Date.class) {
								setMethod.invoke(o, new Date(((java.sql.Date) map.get(fieldName)).getTime()));// 为其赋值
							} else if (map.get(fieldName).getClass() == java.sql.Time.class) {
								setMethod.invoke(o, new Date(((java.sql.Time) map.get(fieldName)).getTime()));// 为其赋值
							} else if (map.get(fieldName).getClass() == java.sql.Timestamp.class) {
								setMethod.invoke(o, new Date(((java.sql.Timestamp) map.get(fieldName)).getTime()));// 为其赋值
							}
						} else if (fieldClass == List.class) {//实体类的数组类型
							Type fc = field.getGenericType();
							ParameterizedType pt = (ParameterizedType) fc;
							if (pt.getOwnerType() != null) {//不确定实体类的数组类型如{List<?> data;String msg;}
								Class<?> su = (Class<?>) pt.getActualTypeArguments()[0];
								setMethod = clazz.getMethod(setterName, fieldClass);
								List<Object> subobject = mapToObjectList(su, map.get(fieldName));
								setMethod.invoke(o, subobject);
							} else {//具体实体类的数组类型如{List<HCMessageGroup data;String msg;}
								setMethod = clazz.getMethod(setterName, fieldClass);
								setMethod.invoke(o, map.get(fieldName));
							}
						} else if(fieldClass == Object.class){//不确定实体类型如{Objcet data;String msg;}
							setMethod = clazz.getMethod(setterName, fieldClass);
							Object object = map.get(fieldName);
							setMethod.invoke(o, object);
						}else if(fieldClass == Map.class){
							setMethod = clazz.getMethod(setterName, fieldClass);
							@SuppressWarnings("unchecked")
							Map<String,Object> omap = (Map<String, Object>) map.get(fieldName);
							setMethod.invoke(o, omap);
						}else {//单个具体的实体类如：{HCMessageGroup data;String msg;}
							setMethod = clazz.getMethod(setterName, fieldClass);
							Object object = mapToObject(fieldClass, map.get(fieldName));
							setMethod.invoke(o, object);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
		return o;
	}

	/*** 字符串解析成Object数组 */
	public static List<?> mapToObjectList(String jsonString, Class<?> clazz) {
		if (jsonString == null || jsonString.isEmpty())
			return null;
		Object mainObject = JsonString.objectWithString(jsonString);
		List<?> list = mapToObjectList(clazz, mainObject);
		return list;
	}

	/*** 解析成Object数组 */
	@SuppressWarnings("unchecked")
	public static List<Object> mapToObjectList(Class<?> clazz, Object mainObject) {
		List<Object> Olist = null;
		if (mainObject != null) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) mainObject;
			if(list != null){
				Olist = mapToObjectList(clazz, list);
			}
		}
		return Olist;
	}

	/*** 解析成Object数组 */
	@SuppressWarnings("unchecked")
	public static List<?> mapToObjectListO(Class<?> clazz, List<?> lists){
		List<?> result = null;
		if(lists != null && lists.size()>0){
			List<Map<String, Object>> list = (List<Map<String, Object>>) lists;
			if(list != null && list.size()>0){
				result = mapToObjectList(clazz, list);
			}
		}
		return result;

	}
	
	/*** 解析成Object数组 */
	public static List<Object> mapToObjectList(Class<?> clazz, List<Map<String, Object>> list) {
		List<Object> Olist = null;
		if (list != null && list.size() > 0) {
			Olist = new ArrayList<Object>();
			for (Map<String, Object> map : list) {
				Object o = mapToObject(clazz, map);
				Olist.add(o);
			}
		}
		return Olist;
	}

	/*** 判断某个类里是否有某个方法 */
	public static boolean isHaveSuchMethod(Class<?> clazz, String methodName) {
		Method[] methodArray = clazz.getMethods();
		boolean result = false;
		if (null != methodArray) {
			for (int i = 0; i < methodArray.length; i++) {
				if (methodArray[i].getName().equals(methodName)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

}
