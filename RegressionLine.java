import java.awt.Point;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * y = mx + b
 * 
 * m : slope :		m = sum((xi - x_mean) * (yi - y_mean)) / sum((xi - x_mean) * (xi - x_mean))
 * b : intercept	b = y_mean - m * x_mean
 */

public class RegressionLine
{
	private double meanX = 0.0;
	private double meanY = 0.0;
	private double slope = 0.0;
	private double intercept = 0.0;

	public RegressionLine(Collection<Point> points)
	{
		meanX = points.parallelStream().collect(Collectors.averagingDouble(Point::getX));
		meanY = points.parallelStream().collect(Collectors.averagingDouble(Point::getY));
		double v1 = points.parallelStream().collect(Collectors.summingDouble(p -> (p.x - meanX) * (p.y - meanY)));
		double v2 = points.parallelStream().collect(Collectors.summingDouble(p -> (p.x - meanX) * (p.x - meanX)));
		slope = v1 / v2;
		intercept = meanY - slope * meanX;
	}

	public double getRegressionLine(double px)
	{
		return intercept + slope * px;
	}

	public static final void main(String[] args)
	{
		Point p1 = new Point(10, 10);
		Point p2 = new Point(20, 20);
		Point p3 = new Point(30, 10);
		Point p4 = new Point(40, 30);
		Point p5 = new Point(50, 10);
		Point p6 = new Point(60, 40);
		Set<Point> points = new HashSet<>(Arrays.asList(p1, p2, p3, p4, p5, p6));
		RegressionLine regressionLine = new RegressionLine(points);
		for (int i = 0; i < 100; i++)
		{
			System.out.println(i + ";" + regressionLine.getRegressionLine(i));
		}
	}
}
