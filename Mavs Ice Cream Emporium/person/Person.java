package person;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Person
{
	protected String name;
	protected String phone;
	
	public Person(String name, String phone)
	{
		this.name = name;
		this.phone = phone;
	}
	
	public Person(BufferedReader br) throws IOException
	{
		this.name = br.readLine();
		this.phone = br.readLine();
	}
	
	public void save(BufferedWriter bw) throws IOException
	{
		bw.write("" + name + '\n');
		bw.write("" + phone + '\n');
	}
	
	@Override
	public boolean equals(Object rhs)
	{
		if(rhs == this)
			return true;
		
		if(rhs.getClass() != this.getClass())
			return false;
		
		Person p = (Person) rhs;
		
		return p.name.equals(this.name);
	}		
	
	@Override
	public int hashCode()
	{
		String hashValue = phone.substring(8);
		int actualValue = Integer.parseInt(hashValue);
		return actualValue;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

}