/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package org.apache.fop.fo.pagination;

// XML
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

// FOP
import org.apache.fop.fo.FONode;

/**
 * Class modelling the fo:static-content object.
 */
public class StaticContent extends Flow {

    /**
     * @param parent FONode that is the parent of this object
     */
    public StaticContent(FONode parent) {
        super(parent);
    }

    /**
     * @see org.apache.fop.fo.FONode#startOfNode()
     */
    protected void startOfNode() throws SAXParseException {
        if (getFlowName() == null || getFlowName().equals("")) {
            throw new SAXParseException("A 'flow-name' is required for "
                                   + getName() + ".", locator);
        }
        getFOEventHandler().startFlow(this);
    }

    /**
     * Make sure content model satisfied, if so then tell the
     * FOEventHandler that we are at the end of the flow.
     * @see org.apache.fop.fo.FONode#endOfNode
     */
    protected void endOfNode() throws SAXParseException {
        if (childNodes == null) {
            missingChildElementError("(%block;)+");
        }
        getFOEventHandler().endFlow(this);
    }

    /**
     * @see org.apache.fop.fo.FONode#validateChildNode(Locator, String, String)
     * XSL Content Model: (%block;)+
     */
    protected void validateChildNode(Locator loc, String nsURI, String localName) 
        throws SAXParseException {
        if (!isBlockItem(nsURI, localName)) {
            invalidChildError(loc, nsURI, localName);
        }
    }

    /**
     * @see org.apache.fop.fo.FObj#getName()
     */
    public String getName() {
        return "fo:static-content";
    }

    /**
     * @see org.apache.fop.fo.FObj#getNameId()
     */
    public int getNameId() {
        return FO_STATIC_CONTENT;
    }
}
