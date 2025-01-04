package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

import static utilities.Constants.JMX_DIR_PATH;

public class XMLHelper {
    private static final Logger logger = LogManager.getLogger(XMLHelper.class);
    public static ReadPropertyFile readPropertyFile;

    public enum XPATH{
        LoopCount("//ThreadGroup/elementProp/stringProp[@name='LoopController.loops']"),
        ThreadCount("//ThreadGroup/intProp[@name='ThreadGroup.num_threads']"),
        RampUpPeriod("//ThreadGroup/intProp[@name='ThreadGroup.ramp_time']"),
        Domain("//HTTPSamplerProxy/stringProp[@name='HTTPSampler.domain']"),
        Port("//HTTPSamplerProxy/stringProp[@name='HTTPSampler.port']"),
        Protocol("//HTTPSamplerProxy/stringProp[@name='HTTPSampler.protocol']"),
        CSVFile("//CSVDataSet/stringProp[@name='filename']");

        private final String value;

        XPATH(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void updateBasicDetails(String fileName){
        readPropertyFile = new ReadPropertyFile();
        updateElementText(XMLHelper.XPATH.Protocol,fileName,0,
                readPropertyFile.readProperty("protocol"));
        XMLHelper.updateElementText(XMLHelper.XPATH.Domain,fileName,0,
                readPropertyFile.readProperty("domain"));
        XMLHelper.updateElementText(XMLHelper.XPATH.Port,fileName,0,
                readPropertyFile.readProperty("port"));
    }
    public static void updateElementText(XPATH xpath, String fileName, int itemNo, String newData) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                    new InputSource(JMX_DIR_PATH + fileName));
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPath.evaluate(xpath.getValue(), doc,
                    XPathConstants.NODESET);
            if (nodes != null && nodes.getLength() > itemNo) {
                nodes.item(itemNo).setTextContent(newData);
            }
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new File( JMX_DIR_PATH +fileName));
            xformer.transform(new DOMSource(doc), result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
