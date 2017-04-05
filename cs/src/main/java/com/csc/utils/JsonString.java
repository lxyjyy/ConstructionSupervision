package com.csc.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonString {
	/*** 定义全局变量 */
	private static int currentPos = 0;// 指针当前位置
	private static int stringLen = 0;// 字符串长度
	private static int deepLen = 0;// 层数
	private static boolean boolValue = false;// boolean类型值
	private static String stringValue = null;// 字符串类型的值
	private static String numberValue = null;// 数字类型的值
	private static Map<String, Object> dictionaryValue = null;// map类型的值
	private static List<Object> arrayValue = null;// list类型的值
	private static String[] keyValue = new String[20];// key的值

	/*** 字符串转化为Object */
	// 1: dictionary 2:array 3:string 4:false 5:true 6:null 7:number 99:error
	// 0:nil
	public static Object objectWithString(String string) {
		if (string == null || string.isEmpty())
			return null;
		char[] sc = string.toCharArray();
		currentPos = 0;
		stringLen = sc.length;
		int ret = scanValue(sc);
		switch (ret) {
		case 1:
			return dictionaryValue;
		case 2:
			return arrayValue;
		case 3:
			return stringValue;
		case 4:
			return boolValue;
		case 5:
			return boolValue;
		case 6:
			return null;
		case 7:
			return numberValue;
		case 8:
			return null;
		case 0:
		case 99:
			return null;
		default:
			break;
		}
		return null;
	}

	/*** 如果是空格，则将指针往后移，忽略空格 */
	protected static void skipWhiteSpace(char[] s) {
		if (currentPos < stringLen) {
			while (s[currentPos] == ' ' && currentPos != stringLen - 2) {//2014-05-20改前：currentPos != stringLen - 1
				currentPos = currentPos + 1;
			}
		}
	}

	/*** 调度方法，根据情况来处理不同对像。此函数可能被递归调用。 */
	// 1: dictionary 2:array 3:string 4:false 5:true 6:null 7:number 99:error
	// 0:nil
	public static int scanValue(char[] s) {
		skipWhiteSpace(s);
		char c = s[currentPos];
		currentPos++;
		switch (c) {
		case '{':
			return scanRestOfDictionary(s);
		case '[':
			return scanRestOfArray(s);
		case '"':
			return scanRestOfString(s);
		case 'f':
			return scanRestOfFalse(s);
		case 't':
			return scanRestOfTrue(s);
		case 'n':
			return scanRestOfNull(s);
		case 'u':
			return scanRestOfUndefined(s);
		case '-':
		case '+':
			currentPos--;
			return scanRestOfNumber(s);
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			currentPos--;
			return scanRestOfNumber(s);		
		default:
			break;
		}
		return 0;
	}

	/*** 读取boolean类型中的值为true */
	private static int scanRestOfTrue(char[] s) {
		if (currentPos < stringLen - 3 && s[currentPos] == 'r' && s[currentPos + 1] == 'u' && s[currentPos + 2] == 'e') {
			boolValue = true;
			currentPos = currentPos + 3;
			return 5;
		}
		return 99;
	}

	/*** 读取boolean类型的值为false */
	private static int scanRestOfFalse(char[] s) {
		if (currentPos < stringLen - 4 && s[currentPos] == 'a' && s[currentPos + 1] == 'l' && s[currentPos + 2] == 's'
				&& s[currentPos + 3] == 'e') {
			boolValue = false;
			currentPos = currentPos + 4;
			return 4;
		}
		return 99;
	}

	/*** 读取空类型的值为null */
	private static int scanRestOfNull(char[] s) {
		if (currentPos < stringLen - 3 && s[currentPos] == 'u' && s[currentPos + 1] == 'l' && s[currentPos + 2] == 'l') {
			currentPos = currentPos + 3;
			return 6;
		}
		return 99;
	}
	
	/*** 读取空类型的值为undefined */
	private static int scanRestOfUndefined(char[] s){
		if(currentPos<stringLen-8&&s[currentPos]=='n'&&s[currentPos+1]=='d'&&s[currentPos+2]=='e'
				&&s[currentPos+3]=='f'&&s[currentPos+4]=='i'&&s[currentPos+5]=='n'&&s[currentPos+6]=='e'
				&&s[currentPos+7]=='d'){
			currentPos = currentPos + 8;
			return 8;
		}
		return 99;
	}
	

	/*** 读取数字类型的值 */
	private static int scanRestOfNumber(char[] s) {
		int orgPos = currentPos;
		boolean hasDot = false;
		currentPos++;
		while (currentPos < stringLen && (s[currentPos] >= '0' && s[currentPos] <= '9') || (!hasDot && s[currentPos] == '.')) {
			if (s[currentPos] == '.')
				hasDot = true;
			currentPos++;
		}
		if (currentPos - orgPos > 0) {
			numberValue = String.valueOf(s, orgPos, currentPos - orgPos);
			return 7;
		}
		return 99;
	}

	/*** 读取字符串类型的值 */
	private static int scanRestOfString(char[] s) {
		int orgPos = currentPos;
		boolean flag = false;// 字符串中是否有反斜杠
		while (currentPos < stringLen) {
			char c = s[currentPos];
			if (c == '"') {
				int cPos = currentPos - 1;
				int count = 0;
				while (s[cPos] == '\\' && cPos >= orgPos) {
					count++;
					cPos--;
				}
				//此处解决的是反斜杠个数为单数时的情况，遇到 反斜杠个数为双时用count % 2 == 0解决不了
//				if (count % 2 == 0) { // 到字符串尾部  
//					currentPos++;
//					break;
//				} else {
//					flag = true;
//				}
				if (count == 0) { // 到字符串尾部  
					currentPos++;
					break;
				} else {
					if(count % 2 != 0){
						flag = true;
					}		
				}
			} else if (c == '\\' && !flag) {
				int tempindex = currentPos + 1;
				while (s[tempindex] == '\\' && tempindex < stringLen) {
					tempindex++;
				}
				if (tempindex < stringLen && s[tempindex] == '"') {

				} else {
					flag = true;
				}

			}
			currentPos++;
		}
		stringValue = String.valueOf(s, orgPos, currentPos - orgPos - 1);
		if (currentPos < stringLen) {
			if (!flag) {
				if(currentPos - 1 > s.length)
					stringValue = "";
				else
					stringValue = String.valueOf(s, orgPos, currentPos - orgPos - 1);
			} else {
				// 字符串中有反斜杠，字符串反转义
				int index = orgPos;
				StringBuffer sb = new StringBuffer();
				while (index < currentPos - 1) {
					if (s[index] == '\\') {
						index++;
						char uc = s[index];
						switch (uc) {
						case '\\':
							sb.append(uc);
							break;
						case '"':
							sb.append(uc);
							break;
						default:
							sb.append('\\');
							sb.append(uc);
							break;
						}
						index++;
					} else if (s[index] == '"') {
						//此处两行是新增加的，反斜杠的个数为双时字符串反转义
						sb.append(s[index]);
						index++;
					} else {
						sb.append(s[index]);
						index++;
					}
				}
				stringValue = sb.toString();
			}
			return 3;
		}
		return 99;
	}

	/*** 读取map类型的值 */
	private static int scanRestOfDictionary(char[] s) {
		Map<String, Object> currentDic = new HashMap<String, Object>();
		deepLen += 1;
		if (deepLen > 10) {
			return 99;
		}
		skipWhiteSpace(s);
		while (currentPos < stringLen) {
			char c = s[currentPos];
			if (c == '}') {
				deepLen--;
				currentPos++;
				dictionaryValue = currentDic;
				return 1;
			}
			skipWhiteSpace(s);
			// key
			if (c == '\"') {
				currentPos++;
				int ret = scanRestOfString(s);
				if (ret != 3) {
					scanRestInvalidString(s);
					continue;
				} else {
					keyValue[deepLen] = stringValue.toLowerCase();
					stringValue = null;
				}
			} else if (c != '\"') {
				scanRestInvalidString(s);
				continue;
			}

			skipWhiteSpace(s);
			if (currentPos < stringLen) {
				c = s[currentPos];

				if (c != ':') {
					return 99;
				}
				currentPos++;
				int ret = scanValue(s);
				// Object v = null;
				if (ret == 0 || ret >= 90){ // error
				    //2014-05-27添加此行代码解决：{"url":"Function/2014/rf/b2632ab23253494bba2acc09766e9fe7.jpg","width":,"height":}
					c = s[currentPos-1];
					//测试下一段是否是完整的，如果是完整的，则跳过这一段
					if (c == '}' || c == ',')// if(c=='}'||c==','||c=='0x00')
					{
						// v = null;
						// currentDic.put(keyString,null);
						currentPos--;
					} else// 没有引号，则用另一模式,回退
					{
						// while (*(c-1)!=':' && *(c-1)!='{' && *(c-1)!=',') {
						// c --;
						// }
						// if(![self scanRestOfString:&v hasSi:NO])
						// return NO;

					}
				} else {
					// 1: dictionary 2:array 3:string 4:false 5:true 6:null
					// 7:number
					// 99:error 0:nil
					switch (ret) {
					case 1:
						currentDic.put(keyValue[deepLen], dictionaryValue);
						dictionaryValue = null;
						break;
					case 2:
						currentDic.put(keyValue[deepLen], arrayValue);
						arrayValue = null;
						break;
					case 3:
						currentDic.put(keyValue[deepLen], stringValue);
						stringValue = null;
						break;
					case 4:
						currentDic.put(keyValue[deepLen], false);
						break;
					case 5:
						currentDic.put(keyValue[deepLen], true);
						break;
					case 6:
						currentDic.put(keyValue[deepLen], null);
						break;
					case 7: 
						if (numberValue != null) {
							if (numberValue.contains(".")) {
								currentDic.put(keyValue[deepLen], Float.parseFloat(numberValue));
							} else {
//								currentDic.put(keyValue[deepLen], Integer.parseInt(numberValue));
								//判断一下，如果为“”或者“null”时
								if(!numberValue.isEmpty()&&!numberValue.equalsIgnoreCase("null")){
									currentDic.put(keyValue[deepLen], Long.parseLong(numberValue));
								}else{
									currentDic.put(keyValue[deepLen], 0);
								}
							}
							numberValue = null;
						} else{
							currentDic.put(keyValue[deepLen], 0);
						}
						break;
					case 8:
						currentDic.put(keyValue[deepLen], null);
						break;
					}

				}
				skipWhiteSpace(s);
				// 如果有逗号，则检查 后面是否还有
				if (currentPos < stringLen && s[currentPos] == ',') {
					skipWhiteSpace(s);
					if (currentPos < stringLen && s[currentPos] == '}') {
						currentPos--;
						continue;
					} else {
						currentPos++;
					}
				}
			}
		}
		return 99;
	}

	/*** 读取list类型的值 */
	private static int scanRestOfArray(char[] s) {
		deepLen += 1;
		if (deepLen > 10) {
			return 99;
		}
		List<Object> currentArray = new ArrayList<Object>();
		while (currentPos < stringLen) {
			skipWhiteSpace(s);
			if (s[currentPos] == ']') {
				deepLen--;
				arrayValue = currentArray;
				currentPos++;
				return 2;
			}
			int ret = scanValue(s);
			if (ret == 0 || ret >= 90) {
				arrayValue = null;
				return 99;
			} else {
				switch (ret) {
				case 1:
					currentArray.add(dictionaryValue);
					dictionaryValue = null;
					break;
				case 2:
					currentArray.add(arrayValue);
					arrayValue = null;
					break;
				case 3:
					currentArray.add(stringValue);
					stringValue = null;
					break;
				case 4:
					currentArray.add(false);
					break;
				case 5:
					currentArray.add(true);
					break;
				case 6:
					currentArray.add(null);
					break;
				case 7: 
					if (numberValue != null) {
						if (numberValue.contains(".")) {
							currentArray.add(Float.parseFloat(numberValue));
						} else {
							currentArray.add(Integer.parseInt(numberValue));
						}
						numberValue = null;
					} else{
						currentArray.add(0);
					}
					break;
				case 8:
					currentArray.add(null);
					break;
				}

			}
			skipWhiteSpace(s);
			if (currentPos < stringLen && s[currentPos] == ',') {
				skipWhiteSpace(s);
				if (currentPos < stringLen && s[currentPos] == ']') {
					currentPos--;
					continue;
				} else {
					currentPos++;
				}
			}
		}
		return 99;
	}

	/*** 为了提高兼容性，添加过滤不合法的键 */
	private static void scanRestInvalidString(char[] s) {
		//2014-05-22新加一行代码 原来 currentpos没有加1,导致有空格时，返复在这致死循环
		 currentPos++;
		if (currentPos < stringLen) {
			char c = s[currentPos];
			while ( c != '}' && c != ']' && c != ' ') {
				currentPos++;
				if (currentPos < stringLen) {
					c = s[currentPos];
				}else{
					break;
				}
			}
		}

	}

}
