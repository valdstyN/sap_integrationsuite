import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {
    def body = message.getBody();
   
    def webPage = "https://api12preview.sapsf.eu/odata/v2/beta/getWorkflowPendingData?wfRequestId=220L";
    def authString = "user:password";
    def authStringEnc = authString.bytes.encodeBase64().toString();

    URL url = new URL(webPage);
    URLConnection urlConnection = url.openConnection();
    urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
    urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
    urlConnection.setRequestMethod("POST"); // PUT is another valid option
    urlConnection.setDoOutput(true);

    InputStream is = urlConnection.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);

    def messageLog = messageLogFactory.getMessageLog(message);
    messageLog.addAttachmentAsString("HTTP RESPONSE", isr.text, "text/plain");

    return message;
}

