/* 
sample payload can be produced by quering SuccessFactors OData entity PickListValueV2
eg: ../odata/v2/PickListValueV2?$filter=PickListV2_id eq 'contractType'&$select=externalCode,optionId,label_defaultValue,label_en_US

<PickListValueV2>
  <PickListValueV2>
    <label_en_US>Permanent</label_en_US>
    <externalCode>1</externalCode>
    <optionId>2015</optionId>
    <label_defaultValue>Permanent</label_defaultValue>
    <PickListV2_id>contractType</PickListV2_id>
  </PickListValueV2>
  <PickListValueV2>
    <label_en_US>Fixed Term Contract</label_en_US>
    <externalCode>2</externalCode>
    <optionId>2016</optionId>
    <label_defaultValue>Fixed Term Contract</label_defaultValue>
    <PickListV2_id>contractType</PickListV2_id>
  </PickListValueV2>
  </PickListValueV2>

*/

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
    
