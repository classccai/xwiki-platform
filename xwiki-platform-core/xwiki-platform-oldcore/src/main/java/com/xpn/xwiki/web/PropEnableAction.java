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
package com.xpn.xwiki.web;

import javax.inject.Named;
import javax.inject.Singleton;

import org.xwiki.component.annotation.Component;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.classes.BaseClass;

/**
 * Action for re-enabling a property definition of the current class. The property to enable is specified in the
 * {@code propname} request parameter, and the class is the one defined in the requested document.
 *
 * @version $Id$
 * @since 2.4M2
 */
@Component
@Named("propenable")
@Singleton
public class PropEnableAction extends AbstractPropChangeAction
{
    @Override
    public void changePropertyDefinition(BaseClass xclass, String propertyName, XWikiContext context)
        throws XWikiException
    {
        XWiki xwiki = context.getWiki();
        XWikiDocument doc = xclass.getOwnerDocument();

        xclass.enableField(propertyName);

        String comment = localizePlainOrKey("core.model.xclass.enableClassProperty.versionSummary", propertyName);

        // Make sure the user is allowed to make this modification
        context.getWiki().checkSavingDocument(context.getUserReference(), doc, comment, true, context);

        xwiki.saveDocument(doc, comment, true, context);
    }
}
