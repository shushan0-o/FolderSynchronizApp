package com.myclass.main;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;

import com.myclass.implementation.TwoFoldersSynchron;

public class Main {

	public static void main(String[] args) {

		BasicConfigurator.configure();

		try {
			if (args.length == 3) {
				new TwoFoldersSynchron().fileManag(args[0], args[1], args[2]);
			} else {
				System.out.println("You missed one or more arguments");
				System.exit(-1);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
