import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BilateralFilter
{
	private final int kernelRadius;

	private final double[][] kernelD;
	private final double[] kernelR;
	private final int[] imageData;

	private final int[][] rgbArray;

	private BufferedImage sourceImage;

	private final int imageWidth;
	private final int imageHeight;

	public BilateralFilter(BufferedImage source, double sigmaD, double sigmaR) // eg. image, 5, 5
	{
		double sigmaMax = Math.max(sigmaD, sigmaR);
		this.kernelRadius = (int)Math.ceil(2 * sigmaMax);

		int kernelSize = kernelRadius * 2 + 1;

		this.kernelD = new double[kernelSize][kernelSize];

		for (int x = 0; x < kernelSize; x++)
		{
			for (int y = 0; y < kernelSize; y++)
			{
				kernelD[x][y] = gauss2(sigmaD, x - kernelRadius, y - kernelRadius);
			}
		}

		kernelR = new double[256];
		for (int i = 0; i < 256; i++)
		{
			kernelR[i] = gauss(sigmaR, i);
		}

		imageWidth = source.getWidth();
		imageHeight = source.getHeight();
		sourceImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		sourceImage.getGraphics().drawImage(source, 0, 0, null);
		imageData = ((DataBufferInt)sourceImage.getRaster().getDataBuffer()).getData();
		rgbArray = new int[imageHeight][imageWidth];

		for (int i = 0; i < imageData.length; i++)
		{
			int x = i % imageWidth;
			int y = i / imageWidth;

			rgbArray[y][x] = imageData[i];
		}
	}

	public BufferedImage filterImage()
	{
		ExecutorService pool = Executors.newFixedThreadPool(10);

		for (int y = 0; y < imageHeight; y++)
		{
			final int dx = y;
			pool.submit(() -> filterRow(dx));
		}
		pool.shutdown();
		try
		{
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return sourceImage;
	}

	private void filterRow(int y)
	{
		// System.out.println((y + 1) + " / " + imageHeight);
		for (int x = 0; x < imageWidth; x++)
		{

			double sumR = 0;
			double sumG = 0;
			double sumB = 0;
			double totalRWeight = 0;
			double totalGWeight = 0;
			double totalBWeight = 0;

			for (int n = y - kernelRadius; n <= y + kernelRadius; n++)
			{
				for (int m = x - kernelRadius; m <= x + kernelRadius; m++)
				{
					if (m >= 0 && n >= 0 && imageWidth > m && imageHeight > n)
					{
						double factor = kernelD[y - n + kernelRadius][x - m + kernelRadius];
						int cRed = rgbArray[n][m] >> 16 & 0xff;
						int cGreen = rgbArray[n][m] >> 8 & 0xff;
						int cBlue = rgbArray[n][m] & 0xff;

						int pRed = rgbArray[y][x] >> 16 & 0xff;
						int pGreen = rgbArray[y][x] >> 8 & 0xff;
						int pBlue = rgbArray[y][x] & 0xff;

						double rWeight = factor * kernelR[Math.abs(cRed - pRed)];
						double gWeight = factor * kernelR[Math.abs(cGreen - pGreen)];
						double bWeight = factor * kernelR[Math.abs(cBlue - pBlue)];

						totalRWeight += rWeight;
						totalGWeight += gWeight;
						totalBWeight += bWeight;
						sumR += rWeight * cRed;
						sumG += gWeight * cGreen;
						sumB += bWeight * cBlue;
					}
				}
			}
			int r = (int)(sumR / totalRWeight);
			int g = (int)(sumG / totalGWeight);
			int b = (int)(sumB / totalBWeight);

			imageData[y * imageWidth + x] = r << 16 | g << 8 | b;
		}
	}

	private double gauss(double sigma, int x)
	{
		return Math.exp(x / (-2 * sigma * sigma));
	}

	private double gauss2(double sigma, int x, int y)
	{
		return Math.exp((x * x + y * y) / (-2 * sigma * sigma));
	}
}
