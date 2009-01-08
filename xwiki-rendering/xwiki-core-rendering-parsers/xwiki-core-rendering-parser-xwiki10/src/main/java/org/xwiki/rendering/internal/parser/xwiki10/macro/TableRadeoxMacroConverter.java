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

import java.util.Map;
import java.util.StringTokenizer;

import org.xwiki.rendering.parser.xwiki10.macro.AbstractRadeoxMacroConverter;

/**
 * 
 * @version $Id$
 * @since 1.8M1
 */
public class TableRadeoxMacroConverter extends AbstractRadeoxMacroConverter
{
    @Override
    public String convert(String name, Map<String, String> parameters, String content)
    {
        StringBuffer result = new StringBuffer();

        StringTokenizer tokenizer = new StringTokenizer(content, "|\n", true);
        String lastToken = null;
        boolean firstCell = true;
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // If a token contains [, then all tokens up to one containing a ] are concatenated. Kind of a block marker.
            if (token.indexOf('[') != -1 && token.indexOf(']') == -1) {
                String linkToken = "";
                while (token.indexOf(']') == -1 && tokenizer.hasMoreTokens()) {
                    linkToken += token;
                    token = tokenizer.nextToken();
                }
                token = linkToken + token;
            }
            if ("\n".equals(token)) {
                // New line: either new row, or a literal newline.
                lastToken = (lastToken == null) ? "" : lastToken;
                if (!lastToken.endsWith("\\")) {
                    // A new row, not a literal newline.
                    // If the last cell didn't contain any data, then it was skipped. Add a blank cell to compensate.
                    if (("".equals(lastToken) || "|".equals(lastToken)) && !firstCell) {
                        result.append('|');
                        result.append(" ");
                        // OLD: table.addCell(" ");
                    }
                    result.append('\n');
                    // OLD: table.newRow();
                } else {
                    // A continued row, with a literal newline.
                    String cell = lastToken;
                    // Keep concatenating while the cell data ends with \\
                    while (cell.endsWith("\\") && tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken();
                        if (!"|".equals(token)) {
                            cell = cell + token;
                        } else {
                            break;
                        }
                    }
                    firstCell = false;
                    result.append('|');
                    result.append(cell.trim());
                    // OLD: table.addCell(cell.trim());
                    if (!tokenizer.hasMoreTokens()) {
                        result.append('\n');
                        // OLD: table.newRow();
                    }
                }
            } else if (!"|".equals(token)) {
                // Cell data
                if (!token.endsWith("\\")) {
                    // If the cell data ends with \\, then it will be continued. Current data is stored in lastToken.
                    result.append('|');
                    result.append(token.trim());
                    // OLD: table.addCell(token.trim());
                    firstCell = false;
                } else if (!tokenizer.hasMoreTokens()) {
                    // Remove backslashes from the end
                    while (token.endsWith("\\")) {
                        token = token.substring(0, token.length() - 1);
                    }
                    result.append('|');
                    result.append(token.trim());
                    // OLD: table.addCell(token.trim());
                }
            } else if ("|".equals(token)) {
                // Cell delimiter
                if ((null == lastToken || "".equals(lastToken)) && !firstCell || "|".equals(lastToken)) {
                    // If the last cell didn't contain any data, then it was skipped. Add a blank cell to compensate.
                    result.append('|');
                    result.append(" ");
                    // OLD: table.addCell(" ");
                    firstCell = false;
                } else if (lastToken.endsWith("\\")) {
                    // The last cell wasn't added because it ended with a continuation mark (\\). Add it now.
                    result.append('|');
                    result.append(lastToken.trim());
                    // OLD: table.addCell(lastToken.trim());
                    firstCell = false;
                }
            }
            lastToken = token;
        }

        return result.toString();
    }

    public boolean supportContent()
    {
        return true;
    }

    @Override
    public boolean isInline()
    {
        return false;
    }
}
