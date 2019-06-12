package com.service.macaddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Lookupservice {

	private static final String SEARCH_MAC_URL = "https://api.macvendors.com/MACID";

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter MAC ID : ");
		String macId = sc.nextLine();
		String companyName = Lookupservice.searchMACId(macId);
		System.out.println(companyName);
		sc.close();
	}

	private static String getCompanyName(String reqURL) {

		StringBuffer response = new StringBuffer();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("Accept", "*/*");
			urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

			InputStream stream = urlConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inputStreamReader);

			String line;
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}

			reader.close();
			urlConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}

	private static String searchMACId(String macId) {
		String reqUrl = SEARCH_MAC_URL.replaceAll("MACID", macId);
		return getCompanyName(reqUrl);
	}

}
