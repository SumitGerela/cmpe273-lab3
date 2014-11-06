package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class Client 
{
    //Define server URLs
    private static final String SERVER_3000 = "http://localhost:3000";
    private static final String SERVER_3001 = "http://localhost:3001";
    private static final String SERVER_3002 = "http://localhost:3002";

    public static void main(String[] args) throws Exception 
    {
        System.out.println("Starting Cache Client...");

        //Key = Array index, Value = Array[index]
        String [] values = {"0","iPhone - 2007", "iPhone 3G - 2008", "iPhone 3GS - 2009", "iPhone 4 - 2010", "iPhone 4S - 2011", "iPhone 5 - 2012", "iPhone 5S - 2014", "iPhone 5C - 2014", "iPhone 6 - 2015", "iPhone 6 plus - 2015"};
        
        List<DistributedCacheService> serverList = new ArrayList<DistributedCacheService>();
        serverList.add(new DistributedCacheService(SERVER_3000));
        serverList.add(new DistributedCacheService(SERVER_3001));
        serverList.add(new DistributedCacheService(SERVER_3002));
        
        System.out.println(" !!<--- PUT values into Servers based on bucket --->!! ");
        for(int putkey=1; putkey<11; putkey++) 
        {
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(putkey)), serverList.size());
            serverList.get(bucket).put(putkey, values[putkey]);
            System.out.println(" PUTing the VALUE - " + values[putkey] + " having KEY - " + putkey + " to SERVER - " + bucket);
        }

        System.out.println(" !!<--- GET values from the Servers based on bucket --->!! ");
        for(int getkey=1; getkey<11; getkey++) 
        {
            int bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(getkey)), serverList.size());
            System.out.println(" GETting the VALUE - " + serverList.get(bucket).get(getkey) + " having KEY - " + getkey+ " from SERVER - "+ bucket); 
        }

        System.out.println(" !!<--- END OF HASHING --->!!");     
    }
}
