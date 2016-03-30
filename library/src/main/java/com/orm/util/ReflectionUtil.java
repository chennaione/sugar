package com.orm.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import com.orm.Configuration;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Table;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ReflectionUtil {

	public static final String TAG = "Sugar/ReflectUtil";
	public static boolean DEBUG = true;

	public static List<Field> getTableFields(Class table) {
		List<Field> fieldList = SugarConfig.getFields(table);
		if (fieldList != null) {
			return fieldList;
		}

		if (DEBUG) {
			Log.d(TAG, "Fetching properties");
		}
		List<Field> typeFields = new ArrayList<Field>();

		getAllFields(typeFields, table);

		List<Field> toStore = new ArrayList<Field>();
		for (Field field : typeFields) {
			if (!field.isAnnotationPresent(Ignore.class) &&
				!Modifier.isStatic(field.getModifiers()) &&
				!Modifier.isTransient(field.getModifiers())) {
				toStore.add(field);
			}
		}

		SugarConfig.setFields(table, toStore);
		return toStore;
	}

	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		Collections.addAll(fields, type.getDeclaredFields());

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	public static void addFieldValueToColumn(ContentValues values, Field field, Object entity,
											 Map<Object, Long> entitiesMap) {

		if (DEBUG) {
			Log.d(TAG, "addFieldValueToColumn: entity=" + entity.getClass().getName());
		}
		field.setAccessible(true);
		Class<?> columnType = field.getType();

		if (DEBUG) {
			Log.d(TAG, "\tColumn Type: " + columnType.getName());
		}

		try {
			String columnName = NamingHelper.toSQLName(field);
			if (DEBUG) {
				Log.d(TAG, "\tColumn Name: " + columnName);
			}
			//if(true) {
			//	throw new RuntimeException("colname: " + columnName);
			//}
			//assert !columnName.equalsIgnoreCase("id");
			Object columnValue = field.get(entity);

			if (DEBUG) {
				Log.d(TAG, "\tColumn Value: " + columnValue);
			}

			if (columnType.isAnnotationPresent(Table.class)) {
				if (DEBUG) {
					Log.d(TAG, "\tColumn is an annotated table.");
				}
				try {
					Field idField = columnType.getDeclaredField("id");
					idField.setAccessible(true);
					//values.put(columnName,
					//		(idField != null)
					//		? String.valueOf(idField.get(columnValue)) : "0");


					final String value = (idField != null)
										 ? String.valueOf(idField.get(columnValue)) : "0";
					if (DEBUG) {
						Log.d(TAG, "\tSetting values: " + BaseColumns._ID + "=" + value);
					}
					values.put(BaseColumns._ID, value);

				} catch (NoSuchFieldException e) {
					if (DEBUG) {
						Log.d(TAG, "\tNo such field \"id\".", e);
					}
					if (entitiesMap.containsKey(columnValue)) {
						//values.put(columnName, entitiesMap.get(columnValue));
						final Long value = entitiesMap.get(columnValue);
						if (DEBUG) {
							Log.d(TAG, "\tSetting values: " + BaseColumns._ID + "=" + value);
						}
						values.put(BaseColumns._ID, value);
					}
				}
			} else if (SugarRecord.class.isAssignableFrom(columnType)) {
				if (DEBUG) {
					Log.d(TAG, "\tColumn is a SugarRecord.");
				}
				//values.put(columnName,
				//		(columnValue != null)
				//		? String.valueOf(((SugarRecord) columnValue).getId())
				//		: "0");

				final String value = (columnValue != null)
									 ? String.valueOf(((SugarRecord) columnValue).getId())
									 : "0";
				if (DEBUG) {
					Log.d(TAG, "\tSetting values: " + BaseColumns._ID + "=" + value);
				}
				values.put(BaseColumns._ID, value);


			} else {
				if (DEBUG) {
					Log.d(TAG, "\tColumn is a property.");
					Log.d(TAG, "\tSetting values: " + columnName + "=" + columnValue);
				}
				if (columnType.equals(Short.class) || columnType.equals(short.class)) {
					values.put(columnName, (Short) columnValue);
				} else if (columnType.equals(Integer.class) || columnType.equals(int.class)) {
					values.put(columnName, (Integer) columnValue);
				} else if (columnType.equals(Long.class) || columnType.equals(long.class)) {
					values.put(columnName, (Long) columnValue);
				} else if (columnType.equals(Float.class) || columnType.equals(float.class)) {
					values.put(columnName, (Float) columnValue);
				} else if (columnType.equals(Double.class) || columnType.equals(double.class)) {
					values.put(columnName, (Double) columnValue);
				} else if (columnType.equals(Boolean.class) || columnType.equals(boolean.class)) {
					values.put(columnName, (Boolean) columnValue);
				} else if (columnType.equals(BigDecimal.class)) {
					try {
						values.put(columnName, field.get(entity).toString());
					} catch (NullPointerException e) {
						values.putNull(columnName);
					}
				} else if (Timestamp.class.equals(columnType)) {
					try {
						values.put(columnName, ((Timestamp) field.get(entity)).getTime());
					} catch (NullPointerException e) {
						values.put(columnName, (Long) null);
					}
				} else if (Date.class.equals(columnType)) {
					try {
						values.put(columnName, ((Date) field.get(entity)).getTime());
					} catch (NullPointerException e) {
						values.put(columnName, (Long) null);
					}
				} else if (Calendar.class.equals(columnType)) {
					try {
						values.put(columnName, ((Calendar) field.get(entity)).getTimeInMillis());
					} catch (NullPointerException e) {
						values.put(columnName, (Long) null);
					}
				} else if (columnType.equals(byte[].class)) {
					if (columnValue == null) {
						values.put(columnName, "".getBytes());
					} else {
						values.put(columnName, (byte[]) columnValue);
					}
				} else {
					if (columnValue == null) {
						values.putNull(columnName);
					} else if (columnType.isEnum()) {
						values.put(columnName, ((Enum) columnValue).name());
					} else {
						values.put(columnName, String.valueOf(columnValue));
					}
				}
			}

		} catch (IllegalAccessException e) {
			Log.e(TAG, "Field not accessible.", e);
		}
	}

	public static void setFieldValueFromCursor(Cursor cursor, Field field, Object object) {
		field.setAccessible(true);
		try {
			Class fieldType = field.getType();
			String colName = NamingHelper.toSQLName(field);
			if ("id".equals(colName)) {
				// XXX Forcing the columns to the standard name.
				colName = BaseColumns._ID;
			}
			int columnIndex = cursor.getColumnIndex(colName);

			//TODO auto upgrade to add new columns
			if (columnIndex < 0) {
				Log.e(TAG, "Invalid colName, you should upgrade database");
				return;
			}

			if (cursor.isNull(columnIndex)) {
				return;
			}

			if (colName.equalsIgnoreCase("id") || colName.equalsIgnoreCase(BaseColumns._ID)) {
				long cid = cursor.getLong(columnIndex);
				field.set(object, Long.valueOf(cid));
			} else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
				field.set(object,
						cursor.getLong(columnIndex));
			} else if (fieldType.equals(String.class)) {
				String val = cursor.getString(columnIndex);
				field.set(object, val != null && val.equals("null") ? null : val);
			} else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
				field.set(object,
						cursor.getDouble(columnIndex));
			} else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
				field.set(object,
						cursor.getString(columnIndex).equals("1"));
			} else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
				field.set(object,
						cursor.getInt(columnIndex));
			} else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
				field.set(object,
						cursor.getFloat(columnIndex));
			} else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
				field.set(object,
						cursor.getShort(columnIndex));
			} else if (fieldType.equals(BigDecimal.class)) {
				String val = cursor.getString(columnIndex);
				field.set(object, val != null && val.equals("null") ? null : new BigDecimal(val));
			} else if (fieldType.equals(Timestamp.class)) {
				long l = cursor.getLong(columnIndex);
				field.set(object, new Timestamp(l));
			} else if (fieldType.equals(Date.class)) {
				long l = cursor.getLong(columnIndex);
				field.set(object, new Date(l));
			} else if (fieldType.equals(Calendar.class)) {
				long l = cursor.getLong(columnIndex);
				Calendar c = Calendar.getInstance();
				c.setTimeInMillis(l);
				field.set(object, c);
			} else if (fieldType.equals(byte[].class)) {
				byte[] bytes = cursor.getBlob(columnIndex);
				if (bytes == null) {
					field.set(object, "".getBytes());
				} else {
					field.set(object, cursor.getBlob(columnIndex));
				}
			} else if (Enum.class.isAssignableFrom(fieldType)) {
				try {
					Method valueOf = field.getType().getMethod("valueOf", String.class);
					String strVal = cursor.getString(columnIndex);
					Object enumVal = valueOf.invoke(field.getType(), strVal);
					field.set(object, enumVal);
				} catch (Exception e) {
					Log.e(TAG,
							"Enum cannot be read from Sqlite3 database. Please check the type of field " +
							field.getName(), e);
				}
			} else {
				Log.e(TAG,
						"Class cannot be read from Sqlite3 database. Please check the type of field " +
						field.getName() + "(" + field.getType().getName() + ")");
			}
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "field set error", e);
		} catch (IllegalAccessException e) {
			Log.e(TAG, "field set error", e);
		}
	}

	private static Field getDeepField(String fieldName, Class<?> type) throws NoSuchFieldException {
		try {
			Field field = type.getDeclaredField(fieldName);
			return field;
		} catch (NoSuchFieldException e) {
			Class superclass = type.getSuperclass();
			if (superclass != null) {
				Field field = getDeepField(fieldName, superclass);
				return field;
			} else {
				throw e;
			}
		}
	}

	public static void setFieldValueForId(Object object, Long value) {
		try {
			Field field = getDeepField("id", object.getClass());
			field.setAccessible(true);
			field.set(object, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static List<Class> getDomainClasses(Configuration configuration) {
		List<Class> domainClasses = new ArrayList<Class>();
		try {
			for (String className : getAllClasses(configuration)) {
				Class domainClass = getDomainClass(className, configuration.getContext());
				if (domainClass != null) {
					domainClasses.add(domainClass);
				}
			}
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}

		return domainClasses;
	}


	private static Class getDomainClass(String className, Context context) {
		Class<?> discoveredClass = null;
		try {
			discoveredClass = Class.forName(className, true, context.getClass().getClassLoader());
		} catch (Throwable e) {
			String error = (e.getMessage() == null) ? "getDomainClass " + className + " error"
													: e.getMessage();
			Log.e(TAG, error);
		}

		if ((discoveredClass != null) &&
			((SugarRecord.class.isAssignableFrom(discoveredClass) &&
			  !SugarRecord.class.equals(discoveredClass)) ||
			 discoveredClass.isAnnotationPresent(Table.class)) &&
			!Modifier.isAbstract(discoveredClass.getModifiers())) {

			//Log.d(TAG, "domain class : " + discoveredClass.getSimpleName());
			return discoveredClass;

		} else {
			return null;
		}
	}


	private static List<String> getAllClasses(Configuration configuration) throws
																		   PackageManager.NameNotFoundException,
																		   IOException {
		String packageName = configuration
				.getDomain(); // ManifestHelper.getDomainPackageName(context);
		List<String> classNames = new ArrayList<String>();
		try {
			List<String> allClasses = MultiDexHelper.getAllClasses(configuration.getContext());
			for (String classString : allClasses) {
				if (classString.startsWith(packageName)) {
					classNames.add(classString);
				}
			}
		} catch (NullPointerException e) {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> urls = classLoader.getResources("");
			while (urls.hasMoreElements()) {
				List<String> fileNames = new ArrayList<String>();
				String classDirectoryName = urls.nextElement().getFile();
				if (classDirectoryName.contains("bin") || classDirectoryName.contains("classes")
					|| classDirectoryName.contains("retrolambda")) {
					File classDirectory = new File(classDirectoryName);
					for (File filePath : classDirectory.listFiles()) {
						populateFiles(filePath, fileNames, "");
					}
					for (String fileName : fileNames) {
						if (fileName.startsWith(packageName)) {
							classNames.add(fileName);
						}
					}
				}
			}
		} finally {
			//            if (null != dexfile) dexfile.close();
		}

		return classNames;
	}

	private static void populateFiles(File path, List<String> fileNames, String parent) {
		if (path.isDirectory()) {
			for (File newPath : path.listFiles()) {
				if ("".equals(parent)) {
					populateFiles(newPath, fileNames, path.getName());
				} else {
					populateFiles(newPath, fileNames, parent + "." + path.getName());
				}
			}
		} else {
			String pathName = path.getName();
			String classSuffix = ".class";
			pathName = pathName.endsWith(classSuffix) ?
					   pathName.substring(0, pathName.length() - classSuffix.length()) : pathName;
			if ("".equals(parent)) {
				fileNames.add(pathName);
			} else {
				fileNames.add(parent + "." + pathName);
			}
		}
	}

	private static String getSourcePath(Context context) throws
														 PackageManager.NameNotFoundException {
		return context.getPackageManager()
					  .getApplicationInfo(context.getPackageName(), 0).sourceDir;
	}
}
