package com.pierre.util;

import com.pierre.view.alert.AlertManager;

import javax.naming.directory.NoSuchAttributeException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class containing static methods that use Java Reflection to get attributes values from a target object by
 * knowing the attribute's name. This's useful when used with generic classes to decouple the logic from the data type
 * and make the code more flexible to all types of data by minimizing dependency on data type.
 */
public class AttributeUtil {

    /**
     * Method returns the value of an object's attribute using the attribute's name.
     * @param object The object we want to get its attribute's value
     * @param attributeName Name of the attribute
     * @return Value of the attribute
     */
    public static Object getAttribute(Object object, String attributeName) {

        Class<?> clazz = object.getClass();

        Object attributeObject = null;

        try {

            if (attributeName != null) {

                Field attributeField = clazz.getDeclaredField(attributeName);

                attributeField.setAccessible(true);

                attributeObject = attributeField.get(object);

            } else {

                throw new NoSuchAttributeException();
            }

        } catch (NoSuchFieldException | IllegalAccessException | NoSuchAttributeException e) {

            AlertManager.errorAlert("Error Reading Attribute",
                    "Error accessing attribute of object " + object.toString());
        }

        return attributeObject;
    }

    /**
     * Returns a list of all the names of an objects attributes.
     * @param object Object we want to get its attributes
     * @return List of object's attributes' names
     */
    public static List<String> getAttributesList(Object object) {

        Field[] fieldList = object.getClass().getDeclaredFields();

        List<String> attributesList = new ArrayList<>();

        for (Field field : fieldList) {

            attributesList.add(field.getName());
        }

        return attributesList;
    }
}