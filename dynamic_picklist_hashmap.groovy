// see sample payload in readme section

import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*   

def Message processData(Message message) {

        def body = message.getBody(java.lang.String) as String;
    
        // create the hashmap for the input picklist values
        HashMap<String,String> picklist = new HashMap<>()
        def xml = new XmlParser().parseText(body)
        xml.each{
            picklist.put(it.externalCode.text(), [it.label_defaultValue.text(),it.optionId.text()])
        }
        message.setProperty("picklist", picklist);

        // to retrieve a value (can be used in a later script)
        HashMap<String,String> picklist2 = new HashMap<>()
        picklist2 = message.properties.get("picklist")
        def extCode = "3" // this can be passed as a property/headder
        def r = picklist2.get(extCode)
        println r[0] // output the label
        println r[1] // output the optionId

        return message;
}
    
