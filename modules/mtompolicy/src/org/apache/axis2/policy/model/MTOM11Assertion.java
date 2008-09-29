package org.apache.axis2.policy.model;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.neethi.Constants;
import org.apache.neethi.PolicyComponent;

public class MTOM11Assertion extends MTOMAssertion {
    
    public final static String NS = "http://www.w3.org/2007/08/soap12-mtom-policy";

    public final static String MTOM_LN = "MTOM";

    public final static String PREFIX = "wsoma";

    public QName getName() {
        return new QName(NS, MTOM_LN);
    }

    public PolicyComponent normalize() {
        throw new UnsupportedOperationException("TODO");
    }

    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        String prefix = writer.getPrefix(NS);

        if (prefix == null) {
            prefix = PREFIX;
            writer.setPrefix(PREFIX, NS);
        }

        writer.writeStartElement(PREFIX, MTOM_LN, NS);

        if (optional)
            writer.writeAttribute("Optional", "true");

        writer.writeNamespace(PREFIX, NS);
        writer.writeEndElement();
        
    }

    public boolean equal(PolicyComponent policyComponent) {
        throw new UnsupportedOperationException("TODO");
    }

    public short getType() {
        return Constants.TYPE_ASSERTION;
    }

}