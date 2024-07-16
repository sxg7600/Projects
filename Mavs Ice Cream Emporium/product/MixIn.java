package product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class MixIn
{
	private MixInFlavor flavor;
	private MixInAmount amount;
	
	public MixIn(MixInFlavor flavor, MixInAmount amount)
	{
		this.flavor = flavor;
		this.amount = amount;
	}
	
	public MixIn(BufferedReader br) throws IOException
	{
		flavor = new MixInFlavor(br);
		amount = MixInAmount.valueOf(br.readLine());
	}
	
	public void save(BufferedWriter bw) throws IOException
	{
		flavor.save(bw);
		bw.write("" + String.valueOf(amount) + '\n');
	}
	
	public int price()
	{
		return flavor.price();
	}
	
	@Override
	public String toString()
	{
		return flavor + (amount.equals(MixInAmount.Normal)? "" : "" + " (" + amount + ")");	 
	}
}