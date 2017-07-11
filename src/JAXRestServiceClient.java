/* 																														*
 * Programmer: Baran Topal                   																			*
 * Project name: JAXRestServiceClient       																			*
 * Folder name: src        																								*
 * Package name: - 																										*
 * File name: JAXRestServiceClient.java                     															*
 *                                           																			*      
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *	                                                                                         							*
 *  LICENSE: This source file is subject to have the protection of GNU General Public License.             				*
 *	You can distribute the code freely but storing this license information. 											*
 *	Contact Baran Topal if you have any questions. barantopal@barantopal.com 										    *
 *	                                                                                         							*
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import org.json.XML;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
public class JAXRestServiceClient {


	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
	public static String TEST_XML_STRING =
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static void main(String[] args) {
		BufferedReader br = null;
		try {

			JSONObject xmlToJSON = null;

			String result = CharStreams.toString( new InputStreamReader( new FileInputStream("my.xml"), Charsets.UTF_8));			

			xmlToJSON = XML.toJSONObject(result);		

			String jsonPrettyPrintString = xmlToJSON.toString(PRETTY_PRINT_INDENT_FACTOR);

			System.out.println(jsonPrettyPrintString);

			//  Now pass JSON File Data to REST Service
			try {
				URL url = new URL("http://localhost:8080/JAXBRestService/api/JAXBWebService");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(xmlToJSON.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				while (in.readLine() != null) {
				}
				System.out.println("\nREST Service Invoked Successfully!");
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
				System.out.println(e);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) {
					br.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
