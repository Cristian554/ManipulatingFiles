package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import entities.Product;

public class Program {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Product> list = new ArrayList<>();

		System.out.println("Enter file path: ");
		String sourceFileStr = sc.nextLine();

		File sourceFile = new File(sourceFileStr);
		String sourceFolderStr = sourceFile.getParent();

		boolean success = new File(sourceFolderStr + "\\out").mkdir(); // cria pasta

		String targetFileStr = sourceFolderStr + "\\out\\sumarry.csv";// cria a subpasta dentro da pasta out

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFileStr))) { // fazendo a leitura do aquivo de
																						// entrada o filereader é bufferedreader instanciado atraves do
			String itemCsv = br.readLine(); // leitura da linha do item
			while(itemCsv != null) {
				
				String[] fields = itemCsv.split(",");// split quebra a linha
				String name = fields[0];
				double price = Double.parseDouble(fields[1]);// converter o fields pois ele converte um String
				int quantity = Integer.parseInt(fields[2]);
				
				list.add(new Product(name, price, quantity));
				
				itemCsv = br.readLine(); //passou para outro item
				
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))){ // utiliza como argumento targeFileStr que é onde está o arqivo de saída
				
				for(Product item : list) {
					bw.write(item.getName() + "," + String.format(" %.2f", item.total()));//mensagem dos produtos na tela
					bw.newLine();
				}
				System.out.println(targetFileStr + " CREATED ");
				
			}catch(IOException e){
				System.out.println("Error writing file: " + e.getMessage());
			}

		} catch (IOException e) {
			System.out.println("Error writing file: " + e.getMessage());

		}
		sc.close();
	}
}
