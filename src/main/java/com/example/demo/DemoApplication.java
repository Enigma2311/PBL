package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static List<Modul> Modules = new ArrayList<>();

	public static void main(String[] args) {

		Modules.add(new Modul("1"));
		try {
			Modules.get(0).AddFlashLight(Color.YELLOW);
		} catch (Exception ex) {

		}

		try {
			Modules.get(0).AddFlashLight(Color.WHITE);
			System.out.println(Modules.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Modules.get(0).AddFlashLight(Color.RED);
		} catch (Exception ex) {

		}

		try {
			Modules.get(0).RemoveFlashLight(Color.WHITE);
		} catch (Exception e) {
			e.printStackTrace();
		}


		SpringApplication.run(DemoApplication.class, args);
	}
}

