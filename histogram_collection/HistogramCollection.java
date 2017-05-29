package histogram_collection;

import java.util.*;

/**
 * Created by szodi on 2017.05.28..
 */
public class HistogramCollection<K, E> extends HashMap<K, Collection<E>>
{
	Class<? extends Collection> collectionClass;

	public HistogramCollection(Class<? extends Collection> collectionClass)
	{
		this.collectionClass = collectionClass;
	}

	public void add(K key, E value)
	{
		if (containsKey(key))
		{
			get(key).add(value);
		}
		else
		{
			try
			{
				Collection<E> collection = collectionClass.newInstance();
				collection.add(value);
				put(key, collection);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args)
	{
		HistogramCollection<Integer, String> hs = new HistogramCollection<>(HashSet.class);
		hs.add(1, "id1");
		hs.add(2, "id2");
		hs.add(3, "id3");
		hs.add(1, "id1");
		hs.add(2, "id5");
		for (Integer integer : hs.keySet())
		{
			System.out.println(hs.get(integer));
		}
		System.out.println("****************");

		hs.keySet().removeIf(key -> hs.get(key).size() < 2);
		for (Integer integer : hs.keySet())
		{
			System.out.println(hs.get(integer));
		}
	}
}
