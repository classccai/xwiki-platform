/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.rendering.internal.parser.xwiki10.macro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.xwiki.rendering.parser.xwiki10.macro.AbstractRadeoxMacroConverter;

/**
 * 
 * @version $Id$
 * @since 1.8M1
 */
public class ImageRadeoxMacroConverter extends AbstractRadeoxMacroConverter
{
    @Override
    public String convert(String name, Map<String, String> parameters, String content)
    {
        StringBuffer result = new StringBuffer();

        if (parameters.size() == 1 || (parameters.size() == 2 && parameters.containsKey("document"))) {
            appendSimpleImage(result, parameters);
        } else {
            result.append("[[");
            appendSimpleImage(result, parameters);
            result.append("||");
            Map<String, String> parametersClone = new LinkedHashMap<String, String>(parameters);
            parametersClone.remove("");
            parametersClone.remove("document");
            appendParameters(result, parametersClone);
            result.append("]]");
        }

        return result.toString();
    }

    private void appendSimpleImage(StringBuffer result, Map<String, String> parameters)
    {
        result.append("image:");

        String document = parameters.get("document");
        if (document != null) {
            result.append(document);
            result.append("@");
        }

        result.append(parameters.get(""));
    }

    public boolean supportContent()
    {
        return false;
    }
}
