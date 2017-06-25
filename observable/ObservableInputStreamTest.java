package observable;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ObservableInputStreamTest
{
	public static void downloadJPG(URL url, File toFile) throws Exception
	{
		HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");

		long contentLength = Long.parseLong(con.getHeaderField("Content-Length"));

		InputStream is = con.getInputStream();
		ObservableInputStream observableInputstream = new ObservableInputStream(is, bytesRead -> System.out.print("\r" + (100L * bytesRead / contentLength) + "%\t" + url + "  ->  " + toFile));
		BufferedImage img = ImageIO.read(observableInputstream);
		ImageIO.write(img, "jpg", toFile);
	}

	public static void main(String[] args)
	{
		try
		{
			URL url = new URL("https://<image_url>");
			File file = new File("<to_file>");
			downloadJPG(url, file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
