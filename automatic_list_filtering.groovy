/*
This script generates a list of filter. This can come in handy when the "in" parameter is not allowed.
eg: (CustomerProject eq 'project123' or CustomerProject eq 'project456' or CustomerProject eq 'project789')

INPUT
Body: project123,project456,project789
Header: "filter_name" = "CustomerProject"

OUTPUT
Property: "cquery" = "(CustomerProject eq 'project123' or CustomerProject eq 'project456' or CustomerProject eq 'project789')"
Can then be used in the query of an HTTP/OData adapter, eg: ../odata/ProjectSet?$filter=${property.cquery}

*/

import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    def body = message.getBody(java.lang.String) as String;
    def n = message.headers.get("filter_name")
    def customQuery = "(";
    for(def i=0;i<body.split(",").size();i++){
        if(i!=0){customQuery+=" or "}
        customQuery += n + " eq '" + body.split(",")[i] + "'"
    }
    customQuery += ")";
    message.setProperty("cquery",customQuery)
    return message;
}
    
