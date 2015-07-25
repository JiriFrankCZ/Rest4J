package org.frank.rest4j.util;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Common used utilities exposed via static methods
 */
public class CommonsUtil {

    // Pattern that matches {test}, {pokus2}, {parameterTest}...
    private static final Pattern parameterPattern = Pattern.compile("\\{([^}]+)\\}");

    /**
     * Takes parameter of FQN and returns extracted classname.
     *
     * @param className Fully qualified name
     * @return Class name
     */
    public static String getClassNameFromFQN(String className){
        if(StringUtils.isEmpty(className)){
            return StringUtils.EMPTY;
        }

        int pos = className.lastIndexOf ('.') + 1;

        return className.substring(pos);
    }

    /**
     *  Transforms parametres/tokens in provided text
     *  to values from map according to name {test} => value
     *
     * @param text String for url replacement
     * @param valuesByKey Map of parameter value pairs
     * @return String with replaced values
     */
    public static String replaceParametres(String text, Map<String, Object> valuesByKey) {
        StringBuilder output = new StringBuilder();
        Matcher tokenMatcher = parameterPattern.matcher(text);

        int cursor = 0;
        while (tokenMatcher.find()) {

            // A token is defined as a sequence of the format "{...}".
            // A key is defined as the content between the brackets.
            int tokenStart = tokenMatcher.start();
            int tokenEnd = tokenMatcher.end();
            int keyStart = tokenMatcher.start(1);
            int keyEnd = tokenMatcher.end(1);

            output.append(text.substring(cursor, tokenStart));

            String token = text.substring(tokenStart, tokenEnd);
            String key = text.substring(keyStart, keyEnd);

            if (valuesByKey.containsKey(key)) {
                Object value = valuesByKey.get(key);
                output.append(String.valueOf(value));
            } else {
                output.append(token);
            }

            cursor = tokenEnd;
        }

        output.append(text.substring(cursor));

        return output.toString();
    }
}
