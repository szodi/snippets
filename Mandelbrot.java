import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Mandelbrot extends Frame {

	private static final long serialVersionUID = 1481763817764353589L;
	
	private final int iterationCount = 256;
	private final double smoothness = 0.001;
	
	BufferedImage image;
	
	public Mandelbrot() {
		setLayout(null);
		setBounds(0, 0, 1920, 1080);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.image = generateImage(getWidth(), getHeight(), -2.96, 1.66, -1.3, 1.3);
		setVisible(true);
	}
	
	private static int calculateStability(double x, double y, int iterationCount) {
		ComplexNumber constant = new ComplexNumber(x, y);
		ComplexNumber z = constant;
		for (int i = 0; i < iterationCount; i++) {
			z = z.times(z).add(constant);
			if (z.abs() > 2.0) {
				return i;
			}
		}
		return 0;
	}
	
	private BufferedImage generateImage(int width, int height, double minX, double maxX, double minY, double maxY) {
		BufferedImage mandelbrotSet = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] dataBuffer = ((DataBufferInt)mandelbrotSet.getRaster().getDataBuffer()).getData();
		if (maxX > minX && maxY > minY) {			
			double dx = width / (maxX - minX);
			double dy = height / (maxY - minY);
			for (double j = minY; j < maxY; j += smoothness) {
				for (double i = minX; i < maxX; i += smoothness) {
					int rgb = stabilityToRgb(calculateStability(i, j, iterationCount));
					int px = (int)((i - minX) * dx);
					int py = (int)(height - 1 - (j - minY) * dy);
					dataBuffer[py * width + px] = rgb;
				}
			}	
		}
		return mandelbrotSet;
	}

	private static int stabilityToRgb(int stability) {
		return (stability * 4) << 16;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	public static void main(String[] args) {
		new Mandelbrot();
	}

	static class ComplexNumber {

		private double real;
		private double imaginary;

		public ComplexNumber(double real, double imaginary) {
			this.real = real;
			this.imaginary = imaginary;
		}

		public ComplexNumber times(ComplexNumber number) {
			double newReal = this.real * number.real - this.imaginary * number.imaginary;
			double newImaginary = this.real * number.imaginary + this.imaginary * number.real;
			return new ComplexNumber(newReal, newImaginary);
		}

		public ComplexNumber add(ComplexNumber number) {
			double newReal = this.real + number.real;
			double newImaginary = this.imaginary + number.imaginary;
			return new ComplexNumber(newReal, newImaginary);
		}

		public double abs() {
			return Math.hypot(this.real, this.imaginary);
		}
	}	
}
