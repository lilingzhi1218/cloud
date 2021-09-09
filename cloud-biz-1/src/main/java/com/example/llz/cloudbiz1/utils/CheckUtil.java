package com.example.llz.cloudbiz1.utils;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public final class CheckUtil {
    private static Pattern pattern = null;

    public CheckUtil() {
    }

    public static boolean isEmail(String sAddr) {
        if (sAddr != null) {
            if (pattern == null) {
                pattern = Pattern.compile("^([a-z0-9A-Z]+[-|~?_.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z][a-z0-9A-Z]*$");
            }

            return pattern.matcher(sAddr).matches();
        } else {
            return false;
        }
    }

    public static <T> boolean contains(Enumeration<T> list, T item) {
        if (list == null) {
            return false;
        } else {
            Object oVal;
            do {
                if (!list.hasMoreElements()) {
                    return false;
                }

                oVal = list.nextElement();
                if (oVal == item) {
                    return true;
                }
            } while(oVal == null || !oVal.equals(item));   

            return true;
        }  
    } 

    public static boolean containsIgnoreCase(Enumeration<String> list, String item) {
        if (list == null) {
            return false;
        } else {
            String oVal;
            do {
                if (!list.hasMoreElements()) {
                    return false;
                }

                oVal = (String)list.nextElement();
                if (oVal == item) {
                    return true;
                }
            } while(oVal == null || !oVal.equalsIgnoreCase(item));

            return true;
        }
    }

    public static boolean containsIgnoreCase(Collection<String> list, String item) {
        if (list == null) {
            return false;
        } else if (list.contains(item)) {
            return true;
        } else {
            Iterator var2 = list.iterator();

            String val;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                val = (String)var2.next();
                if (val == item) {
                    return true;
                }
            } while(val == null || !val.equalsIgnoreCase(item));

            return true;
        }
    }

    public static boolean containsKeyIgnoreCase(Map<?, ?> map, String key) {
        if (map == null) {
            return false;
        } else if (map.containsKey(key)) {
            return true;
        } else {
            Iterator var2 = map.keySet().iterator();

            Object orgKey;
            do {
                if (!var2.hasNext()) {
                    return false;
                }

                orgKey = var2.next();
                if (orgKey == key) {
                    return true;
                }
            } while(orgKey == null || !orgKey.toString().equalsIgnoreCase(key));

            return true;
        }
    }

    public static <T> boolean contains(T[] arr, T item) {
        if (arr == null) {
            return false;
        } else {
            Object[] var2 = arr;
            int var3 = arr.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                T oVal = (T) var2[var4];
                if (oVal == item) {
                    return true;
                }

                if (oVal != null && oVal.equals(item)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static int indexOf(String[] list, String item) {
        if (list != null && list.length != 0) {
            for(int ix = 0; ix < list.length; ++ix) {
                if (list[ix] == item) {
                    return ix;
                }

                if (list[ix] != null && list[ix].equals(item)) {
                    return ix;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static int indexOfIgnoreCase(String[] list, String item) {
        if (list != null && list.length != 0) {
            for(int ix = 0; ix < list.length; ++ix) {
                if (list[ix] == item) {
                    return ix;
                }

                if (list[ix] != null && list[ix].equalsIgnoreCase(item)) {
                    return ix;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public static <T> T getMapValueIgnoreCase(Map<?, T> map, String key) {
        if (map == null) {
            return null;
        } else {
            T val = map.get(key);
            if (val != null) {
                return val;
            } else {
                Iterator var3 = map.entrySet().iterator();

                Entry ent;
                Object orgKey;
                do {
                    if (!var3.hasNext()) {
                        return null;
                    }

                    ent = (Entry)var3.next();
                    orgKey = ent.getKey();
                    if (orgKey == key) {
                        return (T) ent.getValue();
                    }
                } while(orgKey == null || !orgKey.toString().equalsIgnoreCase(key));

                return (T) ent.getValue();
            }
        }
    }

    public static boolean isNullorEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullorEmpty(Collection<?> value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNullorEmpty(Map<?, ?> value) {
        return value == null || value.isEmpty();
    }

    public static <T> boolean isNullorEmpty(T[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isNullorEmpty(byte[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isNullorEmpty(int[] value) {
        return value == null || value.length == 0;
    }

    public static <T extends Number> boolean isNullorZero(T value) {
        return value == null || value.doubleValue() == 0.0D;
    }

    public static boolean isValidId(String id) {
        if (id != null && !id.isEmpty()) {
            return id.charAt(0) != '#';
        } else {
            return false;
        }
    }


    private static boolean isLike(String sql, int ipos) {
        if (ipos < 5) {
            return false;
        } else if (!Character.isSpace(sql.charAt(ipos - 4))) {
            return false;
        } else {
            char chx = sql.charAt(ipos);
            return chx != 'e' && chx != 'E' ? false : "lik".equalsIgnoreCase(sql.substring(ipos - 3, ipos));
        }
    }

    public static boolean isCNumber(char chv) {
        return chv == 9675 || chv == 19968 || chv == 20108 || chv == 19977 || chv == 22235 || chv == 20116 || chv == 20845 || chv == 19971 || chv == 20843 || chv == 20061 || chv == 12295;
    }

    public static boolean isDigit(String val) {
        if (val != null && !val.isEmpty()) {
            char[] var1 = val.toCharArray();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                char chx = var1[var3];
                if (!Character.isDigit(chx)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static long checkTime(long start, String title) {
        long next = System.currentTimeMillis();
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        if (start > 0L) {
            System.err.printf("[%s](%03d)%s用时：%d ms\n", ste[2].getMethodName(), ste.length, title, next - start);
        } else if (title != null) {
            System.err.printf("[%s](%03d)%s开始计时================================\n", ste[2].getMethodName(), ste.length, title);
        }

        return next;
    }

    public static boolean isHaveSuchMethod(Class<?> clazz, String methodName) {
        Method[] methodArray = clazz.getMethods();
        boolean result = false;
        Method[] var4 = methodArray;
        int var5 = methodArray.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method method = var4[var6];
            if (method.getName().equals(methodName)) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static boolean isValidDate(String datevalue, String dateFormat) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);

        try {
            format.setLenient(false);
            format.parse(datevalue);
        } catch (ParseException var5) {
            convertSuccess = false;
        }

        return convertSuccess;
    }
}
