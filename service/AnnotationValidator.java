
package service;

import java.lang.reflect.Field;
import java.util.ArrayList;

import model.annotation.Positive;
import model.annotation.MaxLength;

public class AnnotationValidator
{
    public static String[] validate(Object obj)
    {
        ArrayList<String> errors = new ArrayList<>();

        if (obj == null)
        {
            errors.add("Object cannot be null.");
            return errors.toArray(new String[0]);
        }

        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields)
        {
            field.setAccessible(true);

            try
            {
                Object value = field.get(obj);

                // Check @Positive
                if (field.isAnnotationPresent(Positive.class))
                {
                    Positive positive =
                        field.getAnnotation(Positive.class);

                    if (value instanceof Number
                        && ((Number) value).doubleValue() <= 0)
                    {
                        errors.add(
                            field.getName() + " "
                            + positive.message()
                        );
                    }
                }

                // Check @MaxLength
                if (field.isAnnotationPresent(MaxLength.class))
                {
                    MaxLength maxLength =
                        field.getAnnotation(MaxLength.class);

                    if (value instanceof String
                        && ((String) value).length() > maxLength.value())
                    {
                        errors.add(
                            field.getName()
                            + " must not exceed "
                            + maxLength.value()
                            + " characters."
                        );
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                errors.add(
                    "Cannot access field: " + field.getName()
                );
            }
        }

        return errors.toArray(new String[0]);
    }
}
