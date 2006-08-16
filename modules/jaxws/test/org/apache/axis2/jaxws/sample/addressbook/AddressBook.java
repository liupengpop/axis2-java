
package org.apache.axis2.jaxws.sample.addressbook;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAXWS SI.
 * JAX-WS RI 2.0-b26-ea3
 * Generated source version: 2.0
 * 
 */
@WebService(name = "AddressBook", targetNamespace = "http://org/apache/axis2/jaxws/sample/addressbook", wsdlLocation = "C:\\work\\apps\\eclipse\\workspace\\axis2\\modules\\jaxws\\test\\org\\apache\\axis2\\jaxws\\sample\\addressbook\\META-INF\\AddressBook.wsdl")
public interface AddressBook {


    /**
     * 
     * @param entry
     * @return
     *     returns java.lang.Boolean
     */
    @WebMethod
    @WebResult(name = "status", targetNamespace = "http://org/apache/axis2/jaxws/sample/addressbook")
    @RequestWrapper(localName = "addEntry", targetNamespace = "http://org/apache/axis2/jaxws/sample/addressbook", className = "org.apache.axis2.jaxws.sample.addressbook.AddEntry")
    @ResponseWrapper(localName = "addEntryResponse", targetNamespace = "http://org/apache/axis2/jaxws/sample/addressbook", className = "org.apache.axis2.jaxws.sample.addressbook.AddEntryResponse")
    public Boolean addEntry(
        @WebParam(name = "entry", targetNamespace = "http://org/apache/axis2/jaxws/sample/addressbook")
        AddressBookEntry entry);

}
