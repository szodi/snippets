package observable;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class ObservableInputStream extends InputStream
{
	private final InputStream inputStream;
	private final Consumer<Long> bytesReadConsumer;
	long bytesRead = 0;

	public ObservableInputStream(InputStream inputStream, Consumer<Long> bytesReadConsumer)
	{
		this.inputStream = inputStream;
		this.bytesReadConsumer = bytesReadConsumer;
	}

	@Override
	public int read() throws IOException
	{
		int bytes = inputStream.read();
		if (bytesReadConsumer != null)
		{
			bytesReadConsumer.accept(++bytesRead);
		}
		return bytes;
	}
}
